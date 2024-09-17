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

    private static final Logger logger = Logger.getLogger(FtpImgLoaderUtil2.class.getName()); // 로깅을 위한 Logger 객체 생성

    // FTP 서버의 호스트 주소
    private String host = "1.214.19.22";
    // FTP 서버의 포트 번호
    private Integer port = 2121;
    // FTP 서버에 로그인할 사용자 이름
    private String user = "omniscient";
    // FTP 서버에 로그인할 비밀번호
    private String password = "omniscient";

    public FtpImgLoaderUtil2() {

    }

    // 파일 업로드 메서드
    public String uploadFile(File file, String servletPath) throws IOException {
        // 업로드할 파일이 존재하는지 확인
        if (!file.exists()) {
            logger.log(Level.SEVERE, "파일이 존재하지 않습니다: {0}", file.getAbsolutePath()); // 오류 로그 기록
            throw new IOException("파일이 존재하지 않습니다: " + file.getAbsolutePath()); // 예외 발생
        }

        FTPClient ftpClient = new FTPClient(); // FTPClient 객체 생성
        try {
            connect(ftpClient); // FTP 서버에 연결
            setFtpClientConfig(ftpClient); // FTP 클라이언트 설정
            List<String> filePath = mkDirByRequestUri(servletPath, ftpClient); // 요청 URI에 따라 디렉토리 생성

            // 파일 이름을 UUID와 원래 파일 이름을 조합하여 생성
            String fileName = "omniscient_" + UUID.randomUUID().toString() + "_" + convertToEnglish(file.getName());
            try (InputStream inputStream = new FileInputStream(file)) { // 파일 입력 스트림 생성
                // FTP 서버에 파일 저장
                boolean uploadResult = ftpClient.storeFile(fileName, inputStream);
                if (uploadResult) {
                    filePath.add(fileName); // 업로드된 파일 경로 추가
                    String uploadedFilePath = String.join("/", filePath); // 전체 경로 생성
                    logger.log(Level.INFO, "파일 업로드 완료: {0}", uploadedFilePath); // 성공 로그 기록
                    return uploadedFilePath;  // 업로드된 파일 경로 반환
                } else {
                    logger.log(Level.SEVERE, "파일 업로드 실패: {0}", fileName); // 오류 로그 기록
                    throw new IOException("FTP 파일 업로드 실패: " + fileName); // 예외 발생
                }
            }
        } finally {
            disconnect(ftpClient); // FTP 연결 해제
        }
    }

    // 파일 다운로드 메서드
    public Resource download(String imgUrl) throws IOException {
        FTPClient ftpClient = new FTPClient(); // FTPClient 객체 생성
        try {
            connect(ftpClient); // FTP 서버에 연결
            setFtpClientConfig(ftpClient); // FTP 클라이언트 설정

            // 이미지 URL에서 마지막 슬래시 인덱스 찾기
            int lastSlashIndex = imgUrl.lastIndexOf('/');
            if (lastSlashIndex == -1) { // 슬래시가 없을 경우
                throw new IllegalArgumentException("이미지 URL이 잘못되었습니다: " + imgUrl); // 예외 발생
            }

            // 디렉토리 경로와 파일 이름 추출
            String directoryPath = imgUrl.substring(0, lastSlashIndex);
            String fileName = imgUrl.substring(lastSlashIndex + 1);

            // 해당 디렉토리로 이동
            if (!ftpClient.changeWorkingDirectory(directoryPath)) {
                throw new IOException("디렉토리로 이동할 수 없습니다: " + directoryPath); // 예외 발생
            }

            // 파일 스트림을 통해 파일 다운로드
            try (InputStream imgStream = ftpClient.retrieveFileStream(fileName)) {
                if (imgStream == null) { // 스트림이 없을 경우
                    throw new IOException("파일을 다운로드할 수 없습니다. 경로를 확인하세요: " + imgUrl); // 예외 발생
                }
                byte[] result = IOUtils.toByteArray(imgStream); // 스트림을 바이트 배열로 변환
                if (result.length == 0) { // 결과가 없을 경우
                    logger.log(Level.SEVERE, "::DB에 데이터 조회 실패"); // 오류 로그 기록
                    return null; // null 반환
                }
                return new ByteArrayResource(result); // 바이트 배열을 Resource로 반환
            }
        } finally {
            disconnect(ftpClient); // FTP 연결 해제
        }
    }

    // 파일 삭제 메서드
    public boolean delete(String imgUrl) throws IOException {
        FTPClient ftpClient = new FTPClient(); // FTPClient 객체 생성
        try {
            connect(ftpClient); // FTP 서버에 연결
            // 파일 삭제 시도
            if (ftpClient.deleteFile(imgUrl)) {
                logger.log(Level.INFO, "파일 삭제 완료: {0}", imgUrl); // 성공 로그 기록
                return true;
            } else { // 삭제 실패 시
                logger.log(Level.SEVERE, "파일 삭제 실패: {0}", imgUrl); // 오류 로그 기록
                return false;
            }
        } finally {
            disconnect(ftpClient); // FTP 연결 해제
        }
    }

    // FTP 서버에 연결하는 메서드
    private boolean connect(FTPClient ftpClient) throws IOException {
        logger.log(Level.INFO, "connecting to... {0}", host); // 연결 로그 기록
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true)); // 명령어 로그 출력 설정
        ftpClient.connect(host, port); // FTP 서버에 연결
        int reply = ftpClient.getReplyCode(); // 서버 응답 코드 확인

        // 응답 코드가 성공적이지 않은 경우
        if (!FTPReply.isPositiveCompletion(reply)) {
            logger.log(Level.SEVERE, "FTP 서버에 연결할 수 없습니다. 응답 코드: {0}", reply); // 오류 로그 기록
            disconnect(ftpClient); // 연결 해제
            return false; // false 반환
        }

        ftpClient.setControlEncoding("UTF-8"); // UTF-8 인코딩 설정
        return ftpClient.login(user, password); // 로그인 시도
    }

    // FTP 연결 해제 메서드
    private void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) { // 연결된 경우
            try {
                ftpClient.logout(); // 로그아웃
                ftpClient.disconnect(); // 연결 해제
                logger.log(Level.INFO, "disconnecting from {0}", host); // 해제 로그 기록
            } catch (IOException e) { // 예외 발생 시
                logger.log(Level.SEVERE, "FTP 연결 해제 중 오류 발생: {0}", e.getMessage()); // 오류 로그 기록
            }
        }
    }

    // FTP 클라이언트 설정 메서드
    private void setFtpClientConfig(FTPClient ftpClient) throws IOException {
        ftpClient.enterLocalPassiveMode(); // 패시브 모드 설정
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 파일 타입을 이진으로 설정
        ftpClient.setAutodetectUTF8(true); // UTF-8 자동 감지 설정
    }

    // 요청 URI에 따라 디렉토리를 생성하는 메서드
    private List<String> mkDirByRequestUri(String servletPath, FTPClient ftpClient) throws IOException {
        List<String> result = new ArrayList<>(); // 결과를 저장할 리스트
        List<String> paths = Arrays.asList(servletPath.split("/")); // 요청 URI를 슬래시로 분리하여 리스트로 변환

        logger.log(Level.INFO, "paths={0}", paths); // 경로 로그 기록
        for (String path : paths) { // 각 경로에 대해 반복
            if (path.isEmpty()) { // 빈 문자열은 건너뜀
                continue;
            }

            String englishPath = convertToEnglish(path); // 경로를 영어로 변환
            result.add(englishPath); // 결과 리스트에 추가

            // 현재 작업 디렉토리를 변경하려고 시도
            if (!ftpClient.changeWorkingDirectory(englishPath)) {
                // 디렉토리 변경 실패 시 디렉토리 생성 시도
                if (!ftpClient.makeDirectory(englishPath)) {
                    throw new IOException("디렉토리 생성 실패: " + englishPath); // 예외 발생
                }
                ftpClient.changeWorkingDirectory(englishPath); // 디렉토리 변경
            }
        }

        return result; // 생성된 디렉토리 경로 반환
    }

    // 파일 이름을 영어로 변환하는 메서드
    private String convertToEnglish(String originalFileName) {
        return originalFileName.replaceAll("[^a-zA-Z0-9.]", "_"); // 영어, 숫자 및 점을 제외한 모든 문자를 언더스코어로 대체
    }
}