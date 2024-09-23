package com.omniscient.omniscientback.comment.service;

import com.omniscient.omniscientback.comment.model.Comment;
import com.omniscient.omniscientback.comment.model.CommentDTO;
import com.omniscient.omniscientback.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentDTO addComment(Integer boardId, CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setContent(commentDTO.getContent());
        comment.setAuthor(commentDTO.getAuthor());
        comment.setActive(true);
        comment.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByBoardId(Integer boardId) {
        List<Comment> comments = commentRepository.findByBoardIdAndActiveTrue(boardId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO updateComment(Integer boardId, Integer commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    @Transactional
    public void deactivateComment(Integer boardId, Integer commentId) {
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setActive(false);
        commentRepository.save(comment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getBoardId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.isActive()
        );
    }
}