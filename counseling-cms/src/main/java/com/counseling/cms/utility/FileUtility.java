package com.counseling.cms.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.counseling.cms.entity.FileEntity;
import com.counseling.cms.mapper.FileMapper;

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
    	FTPClient ftpClient = new FTPClient();
    	
    	logger.info("Deleting file to: {}", file_path);
    	
        try {
            ftpClient.connect(host, port);
            boolean loginSuccessful = ftpClient.login(user, password);
            ftpClient.enterLocalActiveMode(); 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (!loginSuccessful) {
                logger.error("FTP login failed.");
                return 0;
            }else {
            	boolean done = ftpClient.deleteFile(file_path);
            	if (done) {
            		logger.info("File deleted successfully.");
            		return 1;
            	} else {
            		logger.error("Failed to delete file. FTP reply: {}", ftpClient.getReplyString());
            		return 0;
            	}            	
            }
        } catch (IOException e) {
            logger.error("Error during file delete: ", e);
            return 0;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                logger.error("Error during FTP logout or disconnect: ", ex);
            }
        }
    }
    
    public String ftpImageUpload(MultipartFile file) {
    	String uuid = createFileUuid();
    	String uploadUrl = createFilePath(file, uuid);
    	
        logger.info("Uploading file to: {}", uploadUrl);
        
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(host, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalActiveMode(); 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 바이너리 모드 설정

            try (InputStream inputStream = file.getInputStream()) {
                boolean done = ftpClient.storeFile(uploadUrl, inputStream);
                if (done) {
                    logger.info("File uploaded successfully.");
                    return uuid;
                } else {
                    logger.error("Failed to upload file.");
                    return null;
                }
            } catch (Exception e) {
                logger.error("Error during file upload: ", e);
                return null;
            }
            
        } catch (Exception e) {
            logger.error("FTP connection error: ", e);
            return null;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception ex) {
                logger.error("Error during FTP logout or disconnect: ", ex);
            }
        }
    }
    
    public Integer createFileCode() {
    	String randomNumber = "";
    	
    	for(int a = 0 ; a < 8 ; a++) {    		
    		randomNumber += (int)Math.floor((double)(Math.random()*10));
    	}
 	
    	return Integer.valueOf(randomNumber);
    }
    
    public String createFileUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    
    public String createFilePath(MultipartFile file,String uuid) {
    	String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        
    	return filePath + uuid + "." + extension;
    }
    
    public String createFile(MultipartFile file,String uuid) {
    	String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    	return filePath + uuid;
    }
    
    public ResponseEntity<UrlResource> downloadFile(Integer fileNo) throws MalformedURLException {
        // 파일 정보를 가져옵니다.
        FileEntity fileEntity = fileMapper.selectAllbyFileNoMapper(fileNo);
        
        // 파일 URL 생성
        String fileUrl = "http://172.30.1.16:20080" + fileEntity.getFilePath().split("CDN")[1];
        
        // UrlResource를 사용하여 URL로부터 리소스를 생성
        UrlResource resource = new UrlResource(fileUrl);

        // 리소스가 존재하지 않을 경우 예외 처리
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 파일의 MIME 타입을 추정
        String contentType = "application/octet-stream"; // 기본값
        String fileName = fileEntity.getFileName();
        System.out.println(fileName);

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

        // 파일 이름을 URL 인코딩합니다.
        String encodedFileName;
        try {
            encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20"); // +을 공백으로 변환
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("파일 이름 인코딩 실패", e);
        }

        // 파일 다운로드를 위한 ResponseEntity 설정
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType)) // MIME 타입 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"; filename*=UTF-8''" + encodedFileName) // 다운로드할 파일 이름 설정
                .body(resource);
    }
    
}
