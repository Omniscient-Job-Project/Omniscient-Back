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
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = boardService.getAllActiveBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Integer id) {
        BoardDTO board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO createdBoard = boardService.createBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Integer id, @Valid @RequestBody BoardDTO boardDTO) {
        BoardDTO updatedBoard = boardService.updateBoard(id, boardDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> updateBoardStatus(@PathVariable Integer id, @RequestParam boolean status) {
        boardService.updateBoardStatus(id, status);
        String message = status ? "게시글이 활성화되었습니다." : "게시글이 비활성화되었습니다.";
        return ResponseEntity.ok(message);
    }
}