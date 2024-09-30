package com.omniscient.omniscientback.mypage.profile.controller;

import com.omniscient.omniscientback.mypage.profile.model.ProFileDTO;
import com.omniscient.omniscientback.mypage.profile.model.ProFileEntity;
import com.omniscient.omniscientback.mypage.profile.service.ProFileService;
import com.omniscient.omniscientback.utile.Utils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import static java.net.Authenticator.RequestorType.SERVER;

@RestController
@RequestMapping("api/v1/profile")
public class ProFileController {

    private final ProFileService proFileService;
    private static final String IMAGE_DIR = "uploads/";

    @Autowired
    public ProFileController(ProFileService proFileService) {
        this.proFileService = proFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = Utils.uploadFileToFTP(file);
        System.out.println("url : " + url);
        if (url != null) {
            proFileService.saveProfile(new ProFileDTO());

            // JSON 형식으로 응답하기 위해 Map 사용
            Map<String, String> response = new HashMap<>();
            response.put("uploadedFilePath", url); // 프론트에서 기대하는 "uploadedFilePath" 키로 URL 전달

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "File upload failed"));
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

    // 이미지 제공
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(IMAGE_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // 이미지 타입 설정
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
