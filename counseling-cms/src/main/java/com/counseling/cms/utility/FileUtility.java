package com.counseling.cms.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.mapper.FileMapper;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileUtility {

    @Autowired
    private FileMapper fileMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    @Value("${host}")
    private String host;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;	
    @Value("${port}")
    private int port;
    @Value("${filePath}")
    private String filePath;


    public int ftpDeleteImage(String file_path) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password); // 패스워드 설정
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            logger.info("Deleting file: {}", file_path);
            channelSftp.rm(file_path);
            logger.info("File deleted successfully.");
            return 1;
        } catch (Exception e) {
            logger.error("Error during file delete: ", e);
            return 0;
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public String ftpImageUpload(MultipartFile file) {
        String uuid = createFileUuid();
        String uploadUrl = createFilePath(file, uuid);

        logger.info("Uploading file to: {}", uploadUrl);

        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password); // 패스워드 설정
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            try (InputStream inputStream = file.getInputStream()) {
                channelSftp.put(inputStream, uploadUrl);
                logger.info("File uploaded successfully.");
                return uuid;
            } catch (Exception e) {
                logger.error("Error during file upload: ", e);
                return null;
            }

        } catch (Exception e) {
            logger.error("Error during file upload: {}, {}", e.getMessage(), e);

            return null;
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public Integer createFileCode() {
        String randomNumber = "";

        for (int a = 0; a < 8; a++) {
            randomNumber += (int) Math.floor((double) (Math.random() * 10));
        }

        return Integer.valueOf(randomNumber);
    }

    public String createFileUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public String createFilePath(MultipartFile file, String uuid) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        return filePath + uuid + "." + extension;
    }

    public String createFile(MultipartFile file, String uuid) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return filePath + uuid;
    }

    public ResponseEntity<UrlResource> downloadFile(String fileSeq, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");
        // 파일 정보를 가져옵니다.
        FileEntity fileEntity = fileMapper.selectOneFile(fileSeq);

        // 파일 URL 생성
        String fileUrl = "http://34.64.201.205" + fileEntity.getFilePath().split("CDN")[1];
        // UrlResource를 사용하여 URL로부터 리소스를 생성
        UrlResource resource = new UrlResource(fileUrl);

        // 리소스가 존재하지 않을 경우 예외 처리
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 파일의 MIME 타입을 추정
        String contentType = "application/octet-stream"; // 기본값
        String fileName = fileEntity.getFileName();

        // 파일 확장자에 따라 MIME 타입 설정
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".hwp")) {
            contentType = "application/x-hwp";
        } else if (fileName.endsWith(".txt")) {
            contentType = "text/plain";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".gif")) {
            contentType = "image/gif";
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType)) // MIME 타입 설정
                .body(resource);
    }
}
