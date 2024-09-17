package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.Certificate;
import com.omniscient.omniscientback.mypage.model.CertificateDTO;
import com.omniscient.omniscientback.mypage.repository.CertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Certificate certificate = convertToEntity(certificateDTO);
        certificate.setIsActive(true);
        Certificate savedCertificate = certificateRepository.save(certificate);
        CertificateDTO savedDTO = convertToDTO(savedCertificate);
        logger.info("새로운 자격증 생성 완료. ID: {}", savedDTO.getId());
        return savedDTO;
    }

    public CertificateDTO updateCertificate(CertificateDTO certificateDTO) {
        logger.info("자격증 업데이트 시작. ID: {}", certificateDTO.getId());
        Certificate existingCertificate = certificateRepository.findByIdAndIsActiveTrue(certificateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("업데이트할 자격증을 찾을 수 없음. ID: {}", certificateDTO.getId());
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });

        updateCertificateFields(existingCertificate, certificateDTO);
        Certificate updatedCertificate = certificateRepository.save(existingCertificate);
        CertificateDTO updatedDTO = convertToDTO(updatedCertificate);
        logger.info("자격증 업데이트 완료. ID: {}", updatedDTO.getId());
        return updatedDTO;
    }

    @Transactional
    public boolean deactivateCertificate(Integer id) {
        logger.info("자격증 비활성화 시작. ID: {}", id);
        int updatedCount = certificateRepository.updateIsActiveById(id, false);
        boolean deactivated = updatedCount > 0;
        if (deactivated) {
            logger.info("자격증 비활성화 완료. ID: {}", id);
        } else {
            logger.warn("비활성화할 자격증을 찾을 수 없음. ID: {}", id);
        }
        return deactivated;
    }

    private CertificateDTO convertToDTO(Certificate certificate) {
        return new CertificateDTO(
                certificate.getId(),
                certificate.getName(),
                certificate.getDate(),
                certificate.getIssuer(),
                certificate.getNumber(),
                certificate.getIsActive()
        );
    }

    private Certificate convertToEntity(CertificateDTO dto) {
        return new Certificate(
                dto.getId(),
                dto.getName(),
                dto.getDate(),
                dto.getIssuer(),
                dto.getNumber(),
                dto.getIsActive()
        );
    }
    // 프로필 이미지는 아직 구문 안만들었습니당
    // 추후에 여기 코드에 추가
    private void updateCertificateFields(Certificate certificate, CertificateDTO dto) {
        certificate.setName(dto.getName());
        certificate.setDate(dto.getDate());
        certificate.setIssuer(dto.getIssuer());
        certificate.setNumber(dto.getNumber());
    }
}