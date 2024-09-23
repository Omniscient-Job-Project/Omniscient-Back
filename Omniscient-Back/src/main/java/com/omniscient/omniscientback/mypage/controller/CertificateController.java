package com.omniscient.omniscientback.mypage.controller;

import com.omniscient.omniscientback.mypage.model.CertificateDTO;
import com.omniscient.omniscientback.mypage.service.CertificateService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {
    private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<List<CertificateDTO>> getAllCertificates() {
        logger.info("모든 활성 자격증 조회 요청");
        try {
            List<CertificateDTO> certificates = certificateService.getAllActiveCertificates();
            logger.info("자격증 조회 성공: {} 개 조회됨", certificates.size());
            return ResponseEntity.ok(certificates);
        } catch (Exception e) {
            logger.error("자격증 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> getCertificate(@PathVariable Integer certificateId) {
        logger.info("자격증 조회 요청: ID {}", certificateId);
        try {
            CertificateDTO certificate = certificateService.getCertificate(certificateId);
            logger.info("자격증 조회 성공: ID {}", certificateId);
            return ResponseEntity.ok(certificate);
        } catch (IllegalArgumentException e) {
            logger.warn("자격증을 찾을 수 없음: ID {}", certificateId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("자격증 조회 중 오류 발생: ID {}", certificateId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCertificate(@Valid @RequestBody CertificateDTO certificateDTO) {
        logger.info("새 자격증 생성 요청: {}", certificateDTO);
        try {
            CertificateDTO createdCertificate = certificateService.createCertificate(certificateDTO);
            logger.info("새 자격증 생성 성공: ID {}", createdCertificate.getCertificateId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCertificate);
        } catch (IllegalArgumentException e) {
            logger.error("새 자격증 생성 중 검증 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("새 자격증 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "내부 서버 오류가 발생했습니다."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDTO> updateCertificate(@PathVariable Integer certificateId, @Valid @RequestBody CertificateDTO certificateDTO) {
        logger.info("자격증 업데이트 요청: ID {}", certificateId);
        try {
            certificateDTO.setCertificateId(certificateId);
            CertificateDTO updatedCertificate = certificateService.updateCertificate(certificateDTO);
            logger.info("자격증 업데이트 성공: ID {}", certificateId);
            return ResponseEntity.ok(updatedCertificate);
        } catch (IllegalArgumentException e) {
            logger.warn("업데이트할 자격증을 찾을 수 없음: ID {}", certificateId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("자격증 업데이트 중 오류 발생: ID {}", certificateId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateCertificate(@PathVariable Integer certificateId) {
        try {
            boolean deactivated = certificateService.deactivateCertificate(certificateId);
            if (deactivated) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "자격증이 성공적으로 비활성화되었습니다.");
                response.put("id", certificateId);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "자격증을 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            logger.error("자격증 비활성화 중 오류 발생: ID {}", certificateId, e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "내부 서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
