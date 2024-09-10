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
        notice.setNoticeCreateAt(noticeDTO.getNoticeCreateAt());
        notice.setNoticeUpdateAt(noticeDTO.getNoticeUpdateAt());
        notice.setStatus(noticeDTO.getStatus());  // 상태 필드 설정

        return noticeRepository.save(notice);
    }

    @Transactional
    public Notice updateNotice(Notice notice) {
        if (notice.getNoticeId() == null || !noticeRepository.existsById(notice.getNoticeId())) {
            throw new IllegalArgumentException("Invalid notice ID");
        }
        // 기존 공지 사항을 가져와서 업데이트
        Notice existingNotice = noticeRepository.findById(notice.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID"));
        existingNotice.setNoticeTitle(notice.getNoticeTitle());
        existingNotice.setNoticeContent(notice.getNoticeContent());
        existingNotice.setNoticeUpdateAt(LocalDateTime.now());
        return noticeRepository.save(existingNotice);
    }

    @Transactional
    public boolean deleteNotice(Integer noticeId) {
        Optional<Notice> noticeOpt = noticeRepository.findById(noticeId);
        if (noticeOpt.isEmpty()) {
            return false;  // 공지사항이 존재하지 않으면 삭제 실패
        }
        Notice notice = noticeOpt.get();
        notice.setStatus(false);  // 공지사항을 삭제된 상태로 변경
        noticeRepository.save(notice);
        return true;  // 성공적으로 처리됨
    }



    @Transactional
    public Notice save(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        notice.setUserId(noticeDTO.getUserId());
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeCreateAt(noticeDTO.getNoticeCreateAt());
        notice.setNoticeUpdateAt(noticeDTO.getNoticeUpdateAt());
        notice.setStatus(noticeDTO.getStatus());  // 상태 필드 설정

        return noticeRepository.save(notice);
    }
}

