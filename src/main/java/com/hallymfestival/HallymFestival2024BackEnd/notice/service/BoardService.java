package com.hallymfestival.HallymFestival2024BackEnd.notice.service;

import com.hallymfestival.HallymFestival2024BackEnd.notice.domain.BoardRepository;
import com.hallymfestival.HallymFestival2024BackEnd.notice.dto.BoardDto;
import com.hallymfestival.HallymFestival2024BackEnd.notice.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }

    public BoardDto createBoard(BoardDto boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        boardRepository.save(board);
        return boardDTO;
    }

    public BoardDto getBoard(){
        Board board = boardRepository.findById(1).orElse(null);
        if (board != null) {
            return new BoardDto(board.getTitle(),board.getContent()); // getTitle() 추가
        } else {
            return null;
        }
    }

    public BoardDto update(Integer id, BoardDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        boardRepository.save(board);

        return boardDto;
    }
}
