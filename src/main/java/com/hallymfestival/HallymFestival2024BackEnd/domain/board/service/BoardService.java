package com.hallymfestival.HallymFestival2024BackEnd.domain.board.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.board.dto.BoardDTO;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    void saveBoard(BoardDTO boardDTO) throws IOException;
    void deleteBoard(Long id);
    BoardDTO getBoard(Long id);
    List<BoardDTO> getAllBoards();
    void updateBoard(BoardDTO boardDTO) throws IOException;
}
