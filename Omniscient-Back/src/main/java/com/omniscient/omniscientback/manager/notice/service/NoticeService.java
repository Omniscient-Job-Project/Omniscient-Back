package com.omniscient.omniscientback.manager.notice.service;

import com.omniscient.omniscientback.manager.notice.model.Notice;
import com.omniscient.omniscientback.manager.notice.model.NoticeDTO;
import com.omniscient.omniscientback.manager.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Transactional
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    @Transactional
    public Optional<Notice> getNoticeById(Integer noticeId) {
        return noticeRepository.findById(noticeId);
    }

    @Transactional
    public Notice createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        notice.setUserId(noticeDTO.getUserId());
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeCreateAt(LocalDateTime.now()); // 생성 시간 설정
        notice.setNoticeUpdateAt(LocalDateTime.now()); // 업데이트 시간 설정
        notice.setNoticeStatus(noticeDTO.getNoticeStatus());

        return noticeRepository.save(notice);
    }

    @Transactional
    public Notice updateNotice(Integer noticeId, NoticeDTO noticeDTO) {
        Notice existingNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID"));

        existingNotice.setNoticeTitle(noticeDTO.getNoticeTitle());
        existingNotice.setNoticeContent(noticeDTO.getNoticeContent());
        existingNotice.setNoticeUpdateAt(LocalDateTime.now()); // 업데이트 시간 설정
        existingNotice.setNoticeStatus(noticeDTO.getNoticeStatus());

        return noticeRepository.save(existingNotice);
    }

    @Transactional
    public void softDeleteNotice(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID"));

        notice.setNoticeStatus(false); // 상태를 비활성화로 변경
        noticeRepository.save(notice);
    }

    @Transactional
    public Notice incrementViews(Integer noticeId) {
        return noticeRepository.findById(noticeId)
                .map(notice -> {
                    notice.setNoticeViews(notice.getNoticeViews() + 1);
                    return noticeRepository.save(notice);
                })
                .orElse(null);
    }
}
