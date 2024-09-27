package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.service.FileUploadService;
import com.omniscient.omniscientback.mypage.model.FileUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadDTO response = fileUploadService.uploadFile(file);
            logger.info("파일 업로드 성공: {}, URL: {}", response.getFileName(), response.getFileUrl());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("파일 업로드 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("파일 업로드 중 서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<FileUploadDTO>> getAllFiles() {
        List<FileUploadDTO> files = fileUploadService.getAllActiveFiles();
        logger.info("활성화된 모든 파일 조회 성공. 파일 수: {}", files.size());
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileUploadDTO> getFile(@PathVariable Integer id) {
        try {
            FileUploadDTO file = fileUploadService.getFileById(id);
            logger.info("ID {}인 파일 조회 성공, URL: {}", id, file.getFileUrl());
            return ResponseEntity.ok(file);
        } catch (IllegalArgumentException e) {
            logger.warn("ID {}인 파일 조회 실패: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileUploadDTO> updateFile(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            FileUploadDTO updatedFile = fileUploadService.updateFile(id, file);
            logger.info("ID {}인 파일 업데이트 성공, 새 URL: {}", id, updatedFile.getFileUrl());
            return ResponseEntity.ok(updatedFile);
        } catch (IllegalArgumentException e) {
            logger.warn("ID {}인 파일 업데이트 실패: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("ID {}인 파일 업데이트 중 서버 오류 발생", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateFile(@PathVariable Integer id) {
        try {
            fileUploadService.deactivateFile(id);
            logger.info("ID {}인 파일 비활성화 성공", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("ID {}인 파일 비활성화 실패: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("ID {}인 파일 비활성화 중 서버 오류 발생", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}