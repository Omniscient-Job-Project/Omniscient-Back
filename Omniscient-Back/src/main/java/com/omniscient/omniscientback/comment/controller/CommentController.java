package com.omniscient.omniscientback.comment.controller;

import com.omniscient.omniscientback.comment.model.CommentDTO;
import com.omniscient.omniscientback.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards/comments/{boardId}")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Integer boardId, @RequestBody CommentDTO commentDTO) {
        if (boardId == null || boardId <= 0) {
            return ResponseEntity.badRequest().body("유효하지 않은 게시글 ID입니다.");
        }
        if (commentDTO.getContent() == null || commentDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("댓글 내용은 비어있을 수 없습니다.");
        }
        try {
            CommentDTO newComment = commentService.addComment(boardId, commentDTO);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 추가 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Integer boardId) {
        if (boardId == null || boardId <= 0) {
            return ResponseEntity.badRequest().body("유효하지 않은 게시글 ID입니다.");
        }
        try {
            List<CommentDTO> comments = commentService.getCommentsByBoardId(boardId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer boardId, @PathVariable Integer commentId, @RequestBody CommentDTO commentDTO) {
        if (boardId == null || boardId <= 0 || commentId == null || commentId <= 0) {
            return ResponseEntity.badRequest().body("유효하지 않은 게시글 ID 또는 댓글 ID입니다.");
        }
        if (commentDTO.getContent() == null || commentDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("댓글 내용은 비어있을 수 없습니다.");
        }
        try {
            CommentDTO updatedComment = commentService.updateComment(boardId, commentId, commentDTO);
            return ResponseEntity.ok(updatedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PutMapping("/deactivate/{commentId}")
    public ResponseEntity<?> deactivateComment(@PathVariable Integer boardId, @PathVariable Integer commentId) {
        if (boardId == null || boardId <= 0 || commentId == null || commentId <= 0) {
            return ResponseEntity.badRequest().body("유효하지 않은 게시글 ID 또는 댓글 ID입니다.");
        }
        try {
            commentService.deactivateComment(boardId, commentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 비활성화 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
