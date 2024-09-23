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
                .filter(Faq::getFaqStatus)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public FaqDTO getFaqByFaqId(Integer faqId) {
        return faqRepository.findById(faqId)
                .filter(Faq::getFaqStatus)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));
    }

    public FaqDTO createFaq(FaqDTO faqDTO) {
        Faq faq = fromDto(faqDTO);
        faq.setFaqStatus(true);
        Faq savedFaq = faqRepository.save(faq);
        return toDto(savedFaq);
    }

    public FaqDTO updateFaq(Integer faqId, FaqDTO faqDTO) {
        if (!faqRepository.existsById(faqId)) {
            throw new RuntimeException("FAQ not found");
        }
        Faq faq = fromDto(faqDTO);
        faq.setFaqId(faqId);
        faq.setFaqStatus(true);
        Faq updatedFaq = faqRepository.save(faq);
        return toDto(updatedFaq);
    }

    public boolean deleteFaq(Integer faqId) {
        Optional<Faq> faqOpt = faqRepository.findById(faqId);
        if (faqOpt.isEmpty()) {
            throw new RuntimeException("FAQ not found");
        }
        Faq faq = faqOpt.get();
        faq.setFaqStatus(false);
        faqRepository.save(faq);
        return true;
    }

    private FaqDTO toDto(Faq faq) {
        FaqDTO dto = new FaqDTO();
        dto.setFaqId(faq.getFaqId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setFaqStatus(faq.getFaqStatus());
        dto.setFaqViews(faq.getFaqViews());
        return dto;
    }

    private Faq fromDto(FaqDTO dto) {
        Faq faq = new Faq();
        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        faq.setFaqStatus(dto.getFaqStatus());
        faq.setFaqViews(dto.getFaqViews());
        return faq;
    }

    @Transactional
    public Faq incrementViews(Integer faqId) {
        return faqRepository.findById(faqId)
                .map(faq -> {
                    faq.setFaqViews(faq.getFaqViews() + 1);
                    return faqRepository.save(faq);
                })
                .orElse(null);
    }
}
