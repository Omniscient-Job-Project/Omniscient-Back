package com.omniscient.omniscientback.manager.faq.service;

import com.omniscient.omniscientback.manager.faq.model.Faq;
import com.omniscient.omniscientback.manager.faq.model.FaqDTO;
import com.omniscient.omniscientback.manager.faq.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public List<FaqDTO> getAllFaqs() {
        return faqRepository.findAll().stream()
                .filter(Faq::getFaqStatus)  // 활성 상태인 FAQ만 반환
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public FaqDTO getFaqById(Integer id) {
        return faqRepository.findById(id)
                .filter(Faq::getFaqStatus)  // 활성 상태인 FAQ만 반환
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));
    }

    public FaqDTO createFaq(FaqDTO faqDTO) {
        Faq faq = fromDto(faqDTO);
        faq.setFaqStatus(true);  // 생성 시 상태를 활성으로 설정
        Faq savedFaq = faqRepository.save(faq);
        return toDto(savedFaq);
    }

    public FaqDTO updateFaq(Integer id, FaqDTO faqDTO) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ not found");
        }
        Faq faq = fromDto(faqDTO);
        faq.setId(id);
        faq.setFaqStatus(true);  // 업데이트 시 상태를 활성으로 설정
        Faq updatedFaq = faqRepository.save(faq);
        return toDto(updatedFaq);
    }

    public boolean deleteFaq(Integer id) {
        Optional<Faq> faqOpt = faqRepository.findById(id);
        if (faqOpt.isEmpty()) {
            throw new RuntimeException("FAQ not found");
        }
        Faq faq = faqOpt.get();
        faq.setFaqStatus(false);  // 상태를 비활성으로 변경
        faqRepository.save(faq);
        return true;  // 삭제 성공 시 true 반환
    }

    private FaqDTO toDto(Faq faq) {
        FaqDTO dto = new FaqDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setFaqStatus(faq.getFaqStatus());  // 상태 필드 추가
        dto.setFaqViews(faq.getFaqViews());  // 조회수 필드 추가
        return dto;
    }

    private Faq fromDto(FaqDTO dto) {
        Faq faq = new Faq();
        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        faq.setFaqStatus(dto.getFaqStatus());  // 상태 필드 추가
        faq.setFaqViews(dto.getFaqViews());  // 조회수 필드 추가
        return faq;
    }

    @Transactional
    public Faq incrementViews(Integer faqId) {
        System.out.println("Incrementing views for faqId: " + faqId);
        return faqRepository.findById(faqId)
                .map(faq -> {
                    faq.setFaqViews(faq.getFaqViews() + 1);
                    return faqRepository.save(faq);
                })
                .orElse(null);
    }
}
