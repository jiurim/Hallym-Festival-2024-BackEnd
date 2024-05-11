package com.hallymfestival.HallymFestival2024BackEnd.domain.board.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.board.dto.BoardDTO;
import com.hallymfestival.HallymFestival2024BackEnd.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {


    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


//    @PostMapping
//    public ResponseEntity<?> createBoard(
//            @RequestParam("title") String title,
//            @RequestParam("content") String content,
//            @RequestParam("createdBy") String createdBy,
//            @RequestParam("images") List<MultipartFile> images // 여러 개의 이미지를 받기 위해 List로 변경
//    ) {
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setTitle(title);
//        boardDTO.setContent(content);
//        boardDTO.setCreatedBy(createdBy);
//        boardDTO.setFiles(images);
//
//        try {
//            boardService.saveBoard(boardDTO);
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body("파일 업로드 실패 : " + e.getMessage());
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createBoard(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("createdBy") String createdBy,
            @RequestPart("images") List<MultipartFile> images // 여러 개의 이미지를 받기 위해 List로 변경
    ) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(title);
        boardDTO.setContent(content);
        boardDTO.setCreatedBy(createdBy);
        boardDTO.setFiles(images);
        try {
            boardService.saveBoard(boardDTO);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("파일 업로드 실패 : " + e.getMessage());
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        BoardDTO boardDTO = boardService.getBoard(id);
        if (boardDTO != null) {
            return ResponseEntity.ok(boardDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boardDTOs = boardService.getAllBoards();
        return ResponseEntity.ok(boardDTOs);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBoard(
//            @PathVariable Long id,
//            @RequestParam("title") String title,
//            @RequestParam("content") String content,
//            @RequestParam("createdBy") String createdBy,
//            @RequestPart("images") List<MultipartFile> images
//    ) {
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setId(id); // 수정할 게시물의 ID를 설정
//        boardDTO.setTitle(title);
//        boardDTO.setContent(content);
//        boardDTO.setCreatedBy(createdBy);
//        boardDTO.setFiles(images);
//
//        try {
//            boardService.updateBoard(boardDTO); // 게시물 수정 서비스 호출
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body("게시물 수정 실패 : " + e.getMessage());
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("createdBy") String createdBy,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(value = "deletedImageUrls") List<String> deletedImageUrls
    ) {
        try {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(id);
            boardDTO.setTitle(title);
            boardDTO.setContent(content);
            boardDTO.setCreatedBy(createdBy);
            boardDTO.setFiles(images);
            boardDTO.setImageUrls(deletedImageUrls); // 삭제하고 싶은 사진
            boardService.updateBoard(boardDTO);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("게시물 수정 실패 : " + e.getMessage());
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }
}
