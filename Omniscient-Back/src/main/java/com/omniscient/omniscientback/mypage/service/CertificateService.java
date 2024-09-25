package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.Certificate;
import com.omniscient.omniscientback.mypage.model.CertificateDTO;
import com.omniscient.omniscientback.mypage.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<CertificateDTO> getAllActiveCertificates() {
        List<CertificateDTO> certificates = certificateRepository.findAllByIsActiveTrue().stream()
                .map(certificate -> new CertificateDTO(
                        certificate.getCertificateId(),
                        certificate.getName(),
                        certificate.getDate(),
                        certificate.getIssuer(),
                        certificate.getNumber(),
                        certificate.getIsActive()
                ))
                .collect(Collectors.toList());
        return certificates;
    }

    public CertificateDTO getCertificate(Integer id) {
        Certificate certificate = certificateRepository.findByCertificateIdAndIsActiveTrue(id)  // 수정된 부분
                .orElseThrow(() -> {
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });

        return new CertificateDTO(
                certificate.getCertificateId(),
                certificate.getName(),
                certificate.getDate(),
                certificate.getIssuer(),
                certificate.getNumber(),
                certificate.getIsActive()
        );
    }

    @Transactional
    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = new Certificate();
        updateCertificateFields(certificate, certificateDTO);
        certificate.setIsActive(true);
        Certificate savedCertificate = certificateRepository.save(certificate);

        return new CertificateDTO(
                savedCertificate.getCertificateId(),
                savedCertificate.getName(),
                savedCertificate.getDate(),
                savedCertificate.getIssuer(),
                savedCertificate.getNumber(),
                savedCertificate.getIsActive()
        );
    }

    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificateDTO) {
        Certificate existingCertificate = certificateRepository.findByCertificateIdAndIsActiveTrue(certificateDTO.getCertificateId())  // 수정된 부분
                .orElseThrow(() -> {
                    return new IllegalArgumentException("자격증을 찾을 수 없습니다");
                });

        updateCertificateFields(existingCertificate, certificateDTO);
        Certificate updatedCertificate = certificateRepository.save(existingCertificate);

        return new CertificateDTO(
                updatedCertificate.getCertificateId(),
                updatedCertificate.getName(),
                updatedCertificate.getDate(),
                updatedCertificate.getIssuer(),
                updatedCertificate.getNumber(),
                updatedCertificate.getIsActive()
        );
    }

    @Transactional
    public boolean deactivateCertificate(Integer id) {
        int updatedCount = certificateRepository.updateIsActiveById(id, false);
        return updatedCount > 0;
    }

    private void updateCertificateFields(Certificate certificate, CertificateDTO dto) {
        certificate.setName(dto.getName());
        certificate.setDate(dto.getDate());
        certificate.setIssuer(dto.getIssuer());
        certificate.setNumber(dto.getNumber());
        certificate.setIsActive(dto.getIsActive());
    }
}
