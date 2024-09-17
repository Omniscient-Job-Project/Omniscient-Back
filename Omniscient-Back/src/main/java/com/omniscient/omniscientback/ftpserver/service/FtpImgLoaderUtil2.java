package com.omniscient.omniscientback.ftpserver.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.PrintCommandListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class FtpImgLoaderUtil2 {

    private static final Logger logger = Logger.getLogger(FtpImgLoaderUtil2.class.getName());

    private String host = "192.168.0.150";
    private Integer port = 21;
    private String user = "omniscient";
    private String password = "omniscient";

    public FtpImgLoaderUtil2() {

    }

    public String uploadFile(File file, String servletPath) throws IOException {
        if (!file.exists()) {
            logger.log(Level.SEVERE, "파일이 존재하지 않습니다: {0}", file.getAbsolutePath());
            throw new IOException("파일이 존재하지 않습니다: " + file.getAbsolutePath());
        }

        FTPClient ftpClient = new FTPClient();
        try {
            connect(ftpClient);
            setFtpClientConfig(ftpClient);
            List<String> filePath = mkDirByRequestUri(servletPath, ftpClient);

            String fileName = "omniscient_" + UUID.randomUUID().toString() + "_" + convertToEnglish(file.getName());
            try (InputStream inputStream = new FileInputStream(file)) {
                boolean uploadResult = ftpClient.storeFile(fileName, inputStream);
                if (uploadResult) {
                    filePath.add(fileName);
                    String uploadedFilePath = String.join("/", filePath);
                    logger.log(Level.INFO, "파일 업로드 완료: {0}", uploadedFilePath);
                    return uploadedFilePath;  // 업로드된 파일 경로 반환
                } else {
                    logger.log(Level.SEVERE, "파일 업로드 실패: {0}", fileName);
                    throw new IOException("FTP 파일 업로드 실패: " + fileName);
                }
            }
        } finally {
            disconnect(ftpClient);
        }
    }

    public Resource download(String imgUrl) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            connect(ftpClient);
            setFtpClientConfig(ftpClient);

            int lastSlashIndex = imgUrl.lastIndexOf('/');
            if (lastSlashIndex == -1) {
                throw new IllegalArgumentException("이미지 URL이 잘못되었습니다: " + imgUrl);
            }

            String directoryPath = imgUrl.substring(0, lastSlashIndex);
            String fileName = imgUrl.substring(lastSlashIndex + 1);

            if (!ftpClient.changeWorkingDirectory(directoryPath)) {
                throw new IOException("디렉토리로 이동할 수 없습니다: " + directoryPath);
            }

            try (InputStream imgStream = ftpClient.retrieveFileStream(fileName)) {
                if (imgStream == null) {
                    throw new IOException("파일을 다운로드할 수 없습니다. 경로를 확인하세요: " + imgUrl);
                }
                byte[] result = IOUtils.toByteArray(imgStream);
                if (result.length == 0) {
                    logger.log(Level.SEVERE, "::DB에 데이터 조회 실패");
                    return null;
                }
                return new ByteArrayResource(result);
            }
        } finally {
            disconnect(ftpClient);
        }
    }

    public boolean delete(String imgUrl) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            connect(ftpClient);
            if (ftpClient.deleteFile(imgUrl)) {
                logger.log(Level.INFO, "파일 삭제 완료: {0}", imgUrl);
                return true;
            } else {
                logger.log(Level.SEVERE, "파일 삭제 실패: {0}", imgUrl);
                return false;
            }
        } finally {
            disconnect(ftpClient);
        }
    }

    private boolean connect(FTPClient ftpClient) throws IOException {
        logger.log(Level.INFO, "connecting to... {0}", host);
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        ftpClient.connect(host, port);
        int reply = ftpClient.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            logger.log(Level.SEVERE, "FTP 서버에 연결할 수 없습니다. 응답 코드: {0}", reply);
            disconnect(ftpClient);
            return false;
        }

        ftpClient.setControlEncoding("UTF-8");
        return ftpClient.login(user, password);
    }

    private void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                logger.log(Level.INFO, "disconnecting from {0}", host);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "FTP 연결 해제 중 오류 발생: {0}", e.getMessage());
            }
        }
    }

    private void setFtpClientConfig(FTPClient ftpClient) throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setAutodetectUTF8(true);
    }

    private List<String> mkDirByRequestUri(String servletPath, FTPClient ftpClient) throws IOException {
        List<String> result = new ArrayList<>();
        List<String> paths = Arrays.asList(servletPath.split("/"));

        logger.log(Level.INFO, "paths={0}", paths);
        for (String path : paths) {
            if (path.isEmpty()) {
                continue;
            }

            String englishPath = convertToEnglish(path);
            result.add(englishPath);

            if (!ftpClient.changeWorkingDirectory(englishPath)) {
                if (!ftpClient.makeDirectory(englishPath)) {
                    throw new IOException("디렉토리 생성 실패: " + englishPath);
                }
                ftpClient.changeWorkingDirectory(englishPath);
            }
        }

        return result;
    }

    private String convertToEnglish(String originalFileName) {
        return originalFileName.replaceAll("[^a-zA-Z0-9.]", "_");
    }
}
