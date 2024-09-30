package com.omniscient.omniscientback.utile;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;

@Configuration
public class Utils {

    private static String SERVER;

    private static Integer PORT;

    private static String USER_ID;

    private static String USER_PASS;

    private static FTPClient FTP_CLIENT;


    @Value("${ftp.server}")
    public void setServer(String server) {
        Utils.SERVER = server;
    }

    @Value("${ftp.port}")
    public void setFtp_port(Integer ftp_port) {
        Utils.PORT = ftp_port;
    }

    @Value("${ftp.user}")
    public void setUser_id(String user_id) {
        Utils.USER_ID = user_id;
    }

    @Value("${ftp.pass}")
    public void setPass(String pass) {
        Utils.USER_PASS = pass;
    }

    public static FTPClient initFtpClient() {
        if(FTP_CLIENT != null){
            return FTP_CLIENT;
        }
        Utils.FTP_CLIENT = connectFTP();

        return FTP_CLIENT;
    }

    private static FTPClient connectFTP() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");

        try {
            ftpClient.connect(SERVER, PORT);

            int replyCode = ftpClient.getReplyCode();
            System.out.println(replyCode);
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new IOException("FTP 연결 실패");
            }

            if (!ftpClient.login(USER_ID, USER_PASS)) {
                throw new IOException("FTP 로그인 실패");
            }

            return ftpClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String uploadFileToFTP(MultipartFile file) throws IOException {
        FTPClient ftpClient = connectFTP();
        String remoteFilePath = file.getOriginalFilename();

        try {

            // 파일을 바이너리 모드로 업로드
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 업로드할 파일 데이터를 가져옴
            byte[] fileData = file.getBytes();

            // 원격 서버에 파일 업로드
            boolean result = ftpClient.storeFile(remoteFilePath, new ByteArrayInputStream(fileData));

            if(!result){
                throw new IOException("FTP 파일 업로드 실패");
            }
            String uploadedFileName = "ftp://"+SERVER + remoteFilePath;
            return uploadedFileName;

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(ftpClient.isConnected()){
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }
}
