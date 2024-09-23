package com.omniscient.omniscientback.board.service;

import com.omniscient.omniscientback.board.model.Board;
import com.omniscient.omniscientback.board.repository.BoardRepository;
import com.omniscient.omniscientback.board.model.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardDTO> getAllActiveBoards() {
        return boardRepository.findByStatusTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BoardDTO getBoard(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + id));
        return convertToDTO(board);
    }

    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = convertToEntity(boardDTO);
        board.setStatus(true); // 새로 생성된 게시글은 활성 상태로 설정
        Board savedBoard = boardRepository.save(board);
        return convertToDTO(savedBoard);
    }

    @Transactional
    public BoardDTO updateBoard(Integer id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + id));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setCategory(boardDTO.getCategory());

        Board updatedBoard = boardRepository.save(board);
        return convertToDTO(updatedBoard);
    }

    @Transactional
    public void updateBoardStatus(Integer id, boolean status) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + id));
        board.setStatus(status);
        boardRepository.save(board);
    }

    private BoardDTO convertToDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setCategory(board.getCategory());
        boardDTO.setStatus(board.getStatus());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setUpdatedAt(board.getUpdatedAt());
        return boardDTO;
    }

    private Board convertToEntity(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setCategory(boardDTO.getCategory());
        board.setStatus(boardDTO.getStatus());
        return board;
    }
}