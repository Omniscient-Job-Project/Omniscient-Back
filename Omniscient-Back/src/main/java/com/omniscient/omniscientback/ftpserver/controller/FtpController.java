package com.omniscient.omniscientback.ftpserver.controller;

import com.omniscient.omniscientback.ftpserver.service.FtpImgLoaderUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/imag")
public class FtpController {

    private final FtpImgLoaderUtil2 ftpFileUploadService; // FTP 파일 업로드 및 다운로드를 처리하는 서비스 객체

    @Autowired // 의존성 주입을 위해 사용.
    public FtpController(FtpImgLoaderUtil2 ftpFileUploadService) {
        this.ftpFileUploadService = ftpFileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        // 파일을 업로드하기 위해 로컬 임시 파일 경로를 생성
        String localFilePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
        File localFile = new File(localFilePath); // 로컬 파일 객체 생성

        try {
            // 클라이언트로부터 받은 파일을 로컬에 저장
            file.transferTo(localFile);

            // FTP 서버에 파일을 업로드하고 업로드된 파일 경로를 반환받기
            String uploadedFilePath = ftpFileUploadService.uploadFile(localFile, file.getOriginalFilename());

            // JSON 형식으로 업로드된 파일 경로를 응답으로 반환함
            return ResponseEntity.ok().body(Map.of("uploadedFilePath", uploadedFilePath));

        } catch (IOException e) {
            e.printStackTrace();
            // 500 상태 코드와 함께 오류 메시지를 JSON 형식으로 반환함.
            return ResponseEntity.status(500).body(Map.of("error", "파일 업로드 실패", "message", e.getMessage()));
        } finally {
            // 로컬에 저장된 파일을 삭제합니다.
            if (localFile.exists()) {
                localFile.delete(); // 파일이 존재하면 삭제
            }
        }
    }

    @GetMapping
    public ResponseEntity<Resource> getImage(@RequestParam("path") String path) {
        try {
            // FTP 서버에서 주어진 경로의 이미지를 다운로드
            Resource resource = ftpFileUploadService.download(path);
            if (resource == null) { // 다운로드된 리소스가 없을 경우
                return ResponseEntity.notFound().build(); // 404 상태 코드 반환
            }
            // 이미지 파일을 클라이언트에게 반환
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 응답의 콘텐츠 타입을 JPEG 이미지로 설정
//                    .contentType(MediaType.IMAGE_PNG) // 응답의 콘텐츠 타입을 PNG 이미지로 설정
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"") // 파일 이름을 헤더에 추가
                    .body(resource); // 리소스를 본문으로 추가
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete") // 실제 FTP 서버에서 삭제됨. 논리적 삭제가 되는지 찾아봐야함.
    public ResponseEntity<String> deleteFile(@RequestParam("path") String path) {
        try {
            // 주어진 경로의 파일을 삭제합니다.
            boolean isDeleted = ftpFileUploadService.delete(path);
            if (isDeleted) { // 삭제가 성공했을 경우
                return ResponseEntity.ok("파일 삭제 성공"); // 성공 메시지 반환
            } else {
                return ResponseEntity.status(404).body("파일 삭제 실패: 파일을 찾을 수 없습니다."); // 파일을 찾을 수 없을 경우 404 상태 코드 반환
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 500 상태 코드와 함께 오류 메시지를 반환
            return ResponseEntity.status(500).body("파일 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}