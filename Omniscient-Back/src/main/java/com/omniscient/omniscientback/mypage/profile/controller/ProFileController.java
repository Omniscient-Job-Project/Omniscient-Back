package com.omniscient.omniscientback.mypage.profile.controller;

import com.omniscient.omniscientback.mypage.profile.model.ProFileDTO;
import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import com.omniscient.omniscientback.mypage.profile.service.ProFileService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.omniscient.omniscientback.utile.Utils.initFtpClient;
import static java.net.Authenticator.RequestorType.SERVER;

@RestController
@RequestMapping("api/v1/profile")
public class ProFileController {

    private final ProFileService proFileService;

    @Autowired
    public ProFileController(ProFileService proFileService) {
        this.proFileService = proFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileToFTP(@RequestParam("file") MultipartFile file) {
        FTPClient ftpClient = initFtpClient();
        String remoteFilePath = file.getOriginalFilename();

        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            byte[] fileData = file.getBytes();

            boolean result = ftpClient.storeFile(remoteFilePath, new ByteArrayInputStream(fileData));

            if (!result) {
                throw new IOException("FTP 파일 업로드 실패");
            }

            String uploadedFileName = "ftp://" + SERVER + "/" + remoteFilePath;
            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다: " + uploadedFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping
    public ResponseEntity<List<ProFileEntity>> getAllProfile() {
        return ResponseEntity.ok(proFileService.getAllProfiles());
    }

    // ID로 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProFileEntity> getProfileById(@PathVariable Integer id) {
        return proFileService.getProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 프로필 생성
    @PostMapping
    public ResponseEntity<ProFileEntity> createProfile(@RequestBody ProFileEntity proFileEntity) {
        return ResponseEntity.ok(proFileService.createProfile(proFileEntity));
    }


    // 프로필 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<ProFileEntity> updateProfile(@PathVariable Integer id, @RequestBody ProFileDTO proFileDTO) {
        return ResponseEntity.ok(proFileService.updateProfile(id, proFileDTO));
    }

    // 프로필 상태 비활성화
    @PutMapping("/status/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable Integer id) {
        proFileService.deleteProFileById(id);
        return ResponseEntity.noContent().build();
    }
}
