package com.omniscient.omniscientback.manager.notice.controller;

import com.omniscient.omniscientback.manager.notice.model.Notice;
import com.omniscient.omniscientback.manager.notice.model.NoticeDTO;
import com.omniscient.omniscientback.manager.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notice")
@Tag(name = "Notice API", description = "공지사항 관련 컨트롤러")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Operation(summary = "공지사항 전체 조회", description = "모든 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인해 공지사항을 조회할 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<List<Notice>> getNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    @Operation(summary = "공지사항 상세 조회", description = "ID를 기반으로 특정 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID를 가진 공지사항을 찾을 수 없습니다.")
    })
    @GetMapping("/{noticeId}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable("noticeId") Integer noticeId) {
        Optional<Notice> notice = noticeService.getNoticeById(noticeId);
        return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인해 공지사항을 생성할 수 없습니다.")
    })
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDTO noticeDTO) {
        try {
            Notice createdNotice = noticeService.createNotice(noticeDTO);
            createdNotice.setNoticeCreateAt(LocalDateTime.now()); // Set creation timestamp
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "공지사항 수정", description = "ID를 기반으로 특정 공지사항을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID를 가진 공지사항을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인해 공지사항을 수정할 수 없습니다.")
    })
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PutMapping("/update/{noticeId}")
    public ResponseEntity<Notice> updateNotice(@PathVariable("noticeId") Integer noticeId,
                                               @RequestBody NoticeDTO noticeDTO) {
        try {
            Notice updatedNotice = noticeService.updateNotice(noticeId, noticeDTO);
            updatedNotice.setNoticeUpdateAt(LocalDateTime.now()); // Set update timestamp
            return ResponseEntity.ok(updatedNotice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "공지사항 삭제", description = "ID를 기반으로 특정 공지사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID를 가진 공지사항을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인해 공지사항을 삭제할 수 없습니다.")
    })
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    @PutMapping("/delete/{noticeId}")
    public ResponseEntity<Void> softDeleteNotice(@PathVariable("noticeId") Integer noticeId) {
        try {
            noticeService.softDeleteNotice(noticeId);
            return ResponseEntity.noContent().build(); // 성공적으로 처리된 경우 noContent 리턴
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 공지사항을 찾을 수 없는 경우
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 서버 오류 발생 시
        }
    }

    @Operation(summary = "공지사항 조회수 증가", description = "공지사항의 조회수를 증가시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회수가 증가되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID를 가진 공지사항을 찾을 수 없습니다.")
    })
    @PutMapping("/views/{noticeId}") // 경로 변수 이름 일관성 유지
    public ResponseEntity<Map<String, String>> incrementViews(@PathVariable("noticeId") Integer noticeId) {
        try {
            noticeService.incrementViews(noticeId);
            return ResponseEntity.ok(Map.of("message", "조회수가 증가하였습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
