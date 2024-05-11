package com.hallymfestival.HallymFestival2024BackEnd.notice.controller;

import com.hallymfestival.HallymFestival2024BackEnd.notice.dto.BoardDto;
import com.hallymfestival.HallymFestival2024BackEnd.notice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService=boardService;
    }

    @PostMapping("/notice")
    public BoardDto createBoard(@RequestBody BoardDto boardDto){
        return boardService.createBoard(boardDto);
    }

    @GetMapping("/notice")
    public BoardDto getBoard(){
        return boardService.getBoard();
    }

    @PutMapping("/board/{id}")
    public BoardDto update(@PathVariable Integer id, @RequestBody BoardDto boardDto){
        return boardService.update(id,boardDto);
    }
}
