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

        CommentDTO savedCommentDTO = new CommentDTO();
        savedCommentDTO.setCommentId(savedComment.getCommentId());
        savedCommentDTO.setBoardId(savedComment.getBoardId());
        savedCommentDTO.setContent(savedComment.getContent());
        savedCommentDTO.setAuthor(savedComment.getAuthor());
        savedCommentDTO.setCreatedAt(savedComment.getCreatedAt());
        savedCommentDTO.setActive(savedComment.isActive());

        return savedCommentDTO;
    }

    public List<CommentDTO> getCommentsByBoardId(Integer boardId) {
        List<Comment> comments = commentRepository.findByBoardIdAndActiveTrue(boardId);

        return comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setCommentId(comment.getCommentId());
            commentDTO.setBoardId(comment.getBoardId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setAuthor(comment.getAuthor());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setActive(comment.isActive());
            return commentDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO updateComment(Integer boardId, Integer commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findBycommentIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepository.save(comment);

        CommentDTO updatedCommentDTO = new CommentDTO();
        updatedCommentDTO.setCommentId(updatedComment.getCommentId());
        updatedCommentDTO.setBoardId(updatedComment.getBoardId());
        updatedCommentDTO.setContent(updatedComment.getContent());
        updatedCommentDTO.setAuthor(updatedComment.getAuthor());
        updatedCommentDTO.setCreatedAt(updatedComment.getCreatedAt());
        updatedCommentDTO.setActive(updatedComment.isActive());

        return updatedCommentDTO;
    }

    @Transactional
    public void deactivateComment(Integer boardId, Integer commentId) {
        Comment comment = commentRepository.findBycommentIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setActive(false);
        commentRepository.save(comment);
    }
}
