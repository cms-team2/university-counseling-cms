package com.counseling.cms.utility;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtility {

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

    
    
}
