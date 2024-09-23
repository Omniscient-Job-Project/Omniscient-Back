package com.omniscient.omniscientback.comment.repository;

import com.omniscient.omniscientback.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoardIdAndActiveTrue(Integer boardId);
    Optional<Comment> findBycommentIdAndBoardId(Integer commentId, Integer boardId);
}
