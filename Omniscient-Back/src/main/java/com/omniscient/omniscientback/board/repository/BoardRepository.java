package com.omniscient.omniscientback.board.repository;
import com.omniscient.omniscientback.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByStatusTrue();
}