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
                .map(board -> {
                    BoardDTO boardDTO = new BoardDTO();
                    boardDTO.setBoardid(board.getBoardid());
                    boardDTO.setTitle(board.getTitle());
                    boardDTO.setContent(board.getContent());
                    boardDTO.setCategory(board.getCategory());
                    boardDTO.setStatus(board.getStatus());
                    boardDTO.setCreatedAt(board.getCreatedAt());
                    boardDTO.setUpdatedAt(board.getUpdatedAt());
                    return boardDTO;
                })
                .collect(Collectors.toList());
    }

    public BoardDTO getBoard(Integer boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + boardId));

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardid(board.getBoardid());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setCategory(board.getCategory());
        boardDTO.setStatus(board.getStatus());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setUpdatedAt(board.getUpdatedAt());

        return boardDTO;
    }

    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setCategory(boardDTO.getCategory());
        board.setStatus(true); // 새로 생성된 게시글은 활성 상태로 설정

        Board savedBoard = boardRepository.save(board);

        BoardDTO savedBoardDTO = new BoardDTO();
        savedBoardDTO.setBoardid(savedBoard.getBoardid());
        savedBoardDTO.setTitle(savedBoard.getTitle());
        savedBoardDTO.setContent(savedBoard.getContent());
        savedBoardDTO.setCategory(savedBoard.getCategory());
        savedBoardDTO.setStatus(savedBoard.getStatus());
        savedBoardDTO.setCreatedAt(savedBoard.getCreatedAt());
        savedBoardDTO.setUpdatedAt(savedBoard.getUpdatedAt());

        return savedBoardDTO;
    }

    @Transactional
    public BoardDTO updateBoard(Integer boardId, BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + boardId));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setCategory(boardDTO.getCategory());

        Board updatedBoard = boardRepository.save(board);

        BoardDTO updatedBoardDTO = new BoardDTO();
        updatedBoardDTO.setBoardid(updatedBoard.getBoardid());
        updatedBoardDTO.setTitle(updatedBoard.getTitle());
        updatedBoardDTO.setContent(updatedBoard.getContent());
        updatedBoardDTO.setCategory(updatedBoard.getCategory());
        updatedBoardDTO.setStatus(updatedBoard.getStatus());
        updatedBoardDTO.setCreatedAt(updatedBoard.getCreatedAt());
        updatedBoardDTO.setUpdatedAt(updatedBoard.getUpdatedAt());

        return updatedBoardDTO;
    }

    @Transactional
    public void updateBoardStatus(Integer boardId, boolean status) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다: " + boardId));

        board.setStatus(status);
        boardRepository.save(board);
    }
}
