package com.omniscient.omniscientback.board.controller;

import com.omniscient.omniscientback.board.service.BoardService;
import com.omniscient.omniscientback.board.model.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@CrossOrigin(origins = "http://localhost:8083")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * 모든 활성 게시글을 조회합니다.
     * @return 활성 상태인 모든 게시글 목록
     */
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = boardService.getAllActiveBoards();
        return ResponseEntity.ok(boards);
    }

    /**
     * 특정 ID의 게시글을 조회합니다.
     * @param id 게시글 ID
     * @return 조회된 게시글 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Integer id) {
        BoardDTO board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    /**
     * 새로운 게시글을 생성합니다.
     * @param boardDTO 생성할 게시글 정보
     * @return 생성된 게시글 정보
     */
    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO createdBoard = boardService.createBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    /**
     * 특정 ID의 게시글을 수정합니다.
     * @param id 수정할 게시글 ID
     * @param boardDTO 수정할 게시글 정보
     * @return 수정된 게시글 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Integer id, @Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO updatedBoard = boardService.updateBoard(id, boardDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * 특정 ID의 게시글을 비활성화(소프트 삭제)합니다.
     * @param id 비활성화할 게시글 ID
     * @return 비활성화 성공 메시지
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteBoard(@PathVariable Integer id) {
        boardService.softDeleteBoard(id);
        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }
}