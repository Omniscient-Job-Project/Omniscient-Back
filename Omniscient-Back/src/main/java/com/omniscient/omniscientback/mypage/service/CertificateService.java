package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.Certificate;
import com.omniscient.omniscientback.mypage.model.CertificateDTO;
import com.omniscient.omniscientback.mypage.repository.CertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private static final Logger logger = LoggerFactory.getLogger(CertificateService.class);
    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<CertificateDTO> getAllActiveCertificates() {
        logger.info("모든 활성화된 자격증 조회 시작");
        List<CertificateDTO> certificates = certificateRepository.findAllByIsActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        logger.info("활성화된 자격증 조회 완료. 총 {}개 조회됨", certificates.size());
        return certificates;
    }

    public CertificateDTO getCertificate(Integer id) {
        logger.info("ID {}인 자격증 조회 시작", id);
        Certificate certificate = certificateRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    logger.warn("ID {}인 자격증을 찾을 수 없음", id);
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });
        CertificateDTO certificateDTO = convertToDTO(certificate);
        logger.info("ID {}인 자격증 조회 완료", id);
        return certificateDTO;
    }

    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        logger.info("새로운 자격증 생성 시작: {}", certificateDTO);

        List<String> missingFields = validateCertificateFields(certificateDTO);
        if (!missingFields.isEmpty()) {
            String errorMessage = "자격증 생성 실패: 다음 필수 필드가 누락되었습니다 - " + String.join(", ", missingFields);
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Certificate certificate = convertToEntity(certificateDTO);
        certificate.setIsActive(true);
        Certificate savedCertificate = certificateRepository.save(certificate);
        CertificateDTO savedDTO = convertToDTO(savedCertificate);
        logger.info("새로운 자격증 생성 완료. ID: {}", savedDTO.getId());
        return savedDTO;
    }

    public CertificateDTO updateCertificate(CertificateDTO certificateDTO) {
        logger.info("자격증 업데이트 시작. ID: {}", certificateDTO.getId());

        List<String> missingFields = validateCertificateFields(certificateDTO);
        if (!missingFields.isEmpty()) {
            String errorMessage = "자격증 업데이트 실패: 다음 필수 필드가 누락되었습니다 - " + String.join(", ", missingFields);
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Certificate existingCertificate = certificateRepository.findByIdAndIsActiveTrue(certificateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("업데이트할 자격증을 찾을 수 없음. ID: {}", certificateDTO.getId());
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });
        BeanUtils.copyProperties(certificateDTO, existingCertificate, "id", "isActive");
        Certificate updatedCertificate = certificateRepository.save(existingCertificate);
        CertificateDTO updatedDTO = convertToDTO(updatedCertificate);
        logger.info("자격증 업데이트 완료. ID: {}", updatedDTO.getId());
        return updatedDTO;
    }

    @Transactional
    public void deactivateCertificate(Integer id) {
        logger.info("자격증 비활성화 시작. ID: {}", id);
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("비활성화할 자격증을 찾을 수 없음. ID: {}", id);
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });
        certificate.setIsActive(false);
        certificateRepository.save(certificate);
        logger.info("자격증 비활성화 완료. ID: {}", id);
    }

    private List<String> validateCertificateFields(CertificateDTO certificateDTO) {
        List<String> missingFields = new ArrayList<>();
        if (isNullOrEmpty(certificateDTO.getName())) missingFields.add("name");
        if (isNullOrEmpty(certificateDTO.getDate())) missingFields.add("date");
        if (isNullOrEmpty(certificateDTO.getIssuer())) missingFields.add("issuer");
        if (isNullOrEmpty(certificateDTO.getNumber())) missingFields.add("number");
        return missingFields;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private CertificateDTO convertToDTO(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        BeanUtils.copyProperties(certificate, dto);
        return dto;
    }

    private Certificate convertToEntity(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        BeanUtils.copyProperties(dto, certificate);
        return certificate;
    }
}