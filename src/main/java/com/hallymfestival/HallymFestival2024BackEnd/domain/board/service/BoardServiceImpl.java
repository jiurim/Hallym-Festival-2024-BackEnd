package com.hallymfestival.HallymFestival2024BackEnd.domain.board.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.board.dto.BoardDTO;
import com.hallymfestival.HallymFestival2024BackEnd.domain.board.entity.BoardEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.board.entity.BoardImageEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.board.repository.BoardImageRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.board.repository.BoardRepository;
import com.hallymfestival.HallymFestival2024BackEnd.global.s3.S3Service;
import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardImageRepository boardImageRepository;
    private final BoardRepository boardRepository;
    private final S3Service s3Service;

    // 게시물 저장 메서드
    @Override
    public void saveBoard(BoardDTO boardDTO) throws IOException {
        // S3에 이미지 업로드 후 URL 리스트 가져오기
        List<String> imageUrls = uploadImagesToS3(boardDTO.getFiles());
        // BoardEntity 생성
        BoardEntity boardEntity = createBoardEntity(boardDTO, imageUrls);
        // 저장
        boardRepository.save(boardEntity);
    }

    // 게시물 삭제 메서드
    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


    // ID로 게시물 조회 메서드
    @Override
    public BoardDTO getBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElse(null);
        return boardEntity != null ? convertToBoardDTO(boardEntity) : null;
    }

    // 모든 게시물 조회 메서드
    @Override
    public List<BoardDTO> getAllBoards() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        return boardEntities.stream().map(this::convertToBoardDTO).collect(Collectors.toList());
    }

    // S3에 이미지 업로드 및 URL 리스트 반환
    private List<String> uploadImagesToS3(List<MultipartFile> files) throws IOException {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // 파일의 크기가 0이 아닌 경우에만 업로드를 시도합니다.
            if (file.getSize() > 0) {
                try {
                    String imageUrl = s3Service.upload(file, "board");
                    uploadedUrls.add(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
                }
            }
        }

        return uploadedUrls;
    }

//    // S3에 이미지 업로드 및 URL 리스트 반환
//    private List<String> uploadImagesToS3(List<MultipartFile> files) throws IOException {
//        return files.stream().map(file -> {
//            try {
//                return s3Service.upload(file, "board");
//            } catch (IOException e) {
//                throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
//            }
//        }).collect(Collectors.toList());
//    }

    // BoardDTO를 BoardEntity로 변환
    private BoardEntity createBoardEntity(BoardDTO boardDTO, List<String> imageUrls) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setCreatedBy(boardDTO.getCreatedBy());

        List<BoardImageEntity> images = imageUrls.stream().map(imageUrl -> {
            BoardImageEntity imageEntity = new BoardImageEntity();
            imageEntity.setBoard(boardEntity);
            imageEntity.setImageUrl(imageUrl);
            return imageEntity;
        }).collect(Collectors.toList());

        boardEntity.setImages(images);
        return boardEntity;
    }

    // BoardEntity를 BoardDTO로 변환
    private BoardDTO convertToBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setCreatedBy(boardEntity.getCreatedBy());
        boardDTO.setImageUrls(boardEntity.getImages().stream()
                .map(BoardImageEntity::getImageUrl)
                .collect(Collectors.toList()));
        return boardDTO;
    }




    @Override
    public void updateBoard(BoardDTO boardDTO) throws IOException {
        // 게시물 ID로 데이터베이스에서 해당 게시물을 찾습니다.
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDTO.getId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();

            // 삭제된 이미지의 URL을 가져와서 S3에서 삭제하고 엔티티에서도 제거합니다.
            for (String deletedImageUrl : boardDTO.getImageUrls()) {
                // 이미지 URL을 통해 해당 이미지를 찾습니다.
                BoardImageEntity imageEntity = boardImageRepository.findByImageUrl(deletedImageUrl);
                if (imageEntity != null) {
                    Long imgID = imageEntity.getId();
                    // 삭제된 이미지를 데이터베이스에서도 삭제합니다.
                    boardImageRepository.deleteById(imgID);
                    // S3에서 이미지를 삭제합니다.
                    s3Service.deleteFile(deletedImageUrl);
                    // 이미지 리스트에서도 해당 이미지를 제거합니다.
                    boardEntity.getImages().remove(imageEntity);
                    System.out.println("Deleted Image URL: " + deletedImageUrl);
                    System.out.println("Image removed from entity: " + deletedImageUrl);
                }
            }

            // 게시물 정보를 업데이트합니다.
            boardEntity.setTitle(boardDTO.getTitle());
            boardEntity.setContent(boardDTO.getContent());
            boardEntity.setCreatedBy(boardDTO.getCreatedBy());

            // 새로 업로드된 이미지들을 엔티티에 추가합니다.
            List<String> uploadedImageUrls = uploadImagesToS3(boardDTO.getFiles());
            List<BoardImageEntity> newImages = uploadedImageUrls.stream()
                    .map(imageUrl -> {
                        BoardImageEntity imageEntity = new BoardImageEntity();
                        imageEntity.setBoard(boardEntity);
                        imageEntity.setImageUrl(imageUrl);
                        return imageEntity;
                    })
                    .collect(Collectors.toList());
            boardEntity.setImages(newImages); // 새 이미지로 대체

            // 업데이트된 게시물 엔티티를 데이터베이스에 저장합니다.
            boardRepository.save(boardEntity);
        } else {
            throw new NotFoundException("해당 ID로 게시물을 찾을 수 없습니다: " + boardDTO.getId());
        }
    }


//    public void updateBoard(BoardDTO boardDTO) throws IOException {
//        // 게시물 ID로 데이터베이스에서 해당 게시물을 찾습니다.
//        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDTO.getId());
//        if (optionalBoardEntity.isPresent()) {
//            BoardEntity boardEntity = optionalBoardEntity.get();
//
//            // 삭제된 이미지의 URL을 가져와서 S3에서 삭제하고 엔티티에서도 제거합니다.
//            for (String deletedImageUrl : boardDTO.getImageUrls()) {
//                // 게시물 엔티티에서도 해당 이미지를 제거합니다.
//                System.out.println("Deleted Image URL: " + deletedImageUrl);
//                // 이미지가 삭제되었는지 확인하기 위해 로그를 출력합니다.
//                boardEntity.getImages().removeIf(image -> {
//                    if (image.getImageUrl().equals(deletedImageUrl)) {
//                        System.out.println("Image removed from entity: " + deletedImageUrl);
//                        return true;
//                    }
//                    return false;
//                });
//                // S3에서 이미지를 삭제합니다.
//                s3Service.deleteFile(deletedImageUrl);
//
//            }
//
//            // 게시물 정보를 업데이트합니다.
//            boardEntity.setTitle(boardDTO.getTitle());
//            boardEntity.setContent(boardDTO.getContent());
//            boardEntity.setCreatedBy(boardDTO.getCreatedBy());
//
//            uploadImagesToS3(boardDTO.getFiles());
//            // 업데이트된 게시물 엔티티를 데이터베이스에 저장합니다.
//            boardRepository.save(boardEntity);
//        } else {
//            throw new NotFoundException("해당 ID로 게시물을 찾을 수 없습니다: " + boardDTO.getId());
//        }
//    }



}









//package Nosunwoo.NiceTest.test.board.service;
//
//import Nosunwoo.NiceTest.test.board.dto.BoardDTO;
//import Nosunwoo.NiceTest.test.board.entity.BoardEntity;
//import Nosunwoo.NiceTest.test.board.entity.BoardImageEntity;
//import Nosunwoo.NiceTest.test.board.repository.BoardRepository;
//import Nosunwoo.NiceTest.test.s3.S3Service;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class BoardServiceImpl implements BoardService {
//
//    private final BoardRepository boardRepository;
//    private final S3Service s3Service;
//
//    @Override
//    public void saveBoard(BoardDTO boardDTO) throws IOException {
//        // 파일을 S3에 업로드하고 이미지 URL 리스트를 가져옴
//        List<String> imageUrls = boardDTO.getFiles().stream()
//                .map(file -> {
//                    try {
//                        return s3Service.upload(file, "board");
//                    } catch (IOException e) {
//                        throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
//                    }
//                })
//                .collect(Collectors.toList());
//
//        // BoardEntity 객체 생성 및 저장
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setTitle(boardDTO.getTitle());
//        boardEntity.setContent(boardDTO.getContent());
//        boardEntity.setCreatedBy(boardDTO.getCreatedBy());
//        // BoardImageEntity 객체 생성 및 BoardEntity와 연결 후 저장
//        List<BoardImageEntity> images = imageUrls.stream()
//                .map(imageUrl -> {
//                    BoardImageEntity imageEntity = new BoardImageEntity();
//                    imageEntity.setBoard(boardEntity);
//                    imageEntity.setImageUrl(imageUrl);
//                    return imageEntity;
//                })
//                .collect(Collectors.toList());
//        boardEntity.setImages(images);
//        boardRepository.save(boardEntity);
//    }
//
//    @Override
//    public void deleteBoard(Long id) {
//        boardRepository.deleteById(id);
//    }
//
//    @Override
//    public BoardDTO getBoard(Long id) {
//        // BoardEntity를 BoardDTO로 변환하여 반환
//        BoardEntity boardEntity = boardRepository.findById(id).orElse(null);
//        if (boardEntity != null) {
//            BoardDTO boardDTO = new BoardDTO();
//            boardDTO.setId(boardEntity.getId());
//            boardDTO.setTitle(boardEntity.getTitle());
//            boardDTO.setContent(boardEntity.getContent());
//            boardDTO.setCreatedBy(boardEntity.getCreatedBy());
//            boardDTO.setImageUrls(boardEntity.getImages().stream()
//                    .map(BoardImageEntity::getImageUrl)
//                    .collect(Collectors.toList()));
//            return boardDTO;
//        }
//        return null;
//    }
//
//    @Override
//    public List<BoardDTO> getAllBoards() {
//        // 모든 BoardEntity를 BoardDTO로 변환하여 리스트로 반환
//        List<BoardEntity> boardEntities = boardRepository.findAll();
//        return boardEntities.stream()
//                .map(boardEntity -> {
//                    BoardDTO boardDTO = new BoardDTO();
//                    boardDTO.setId(boardEntity.getId());
//                    boardDTO.setTitle(boardEntity.getTitle());
//                    boardDTO.setContent(boardEntity.getContent());
//                    boardDTO.setCreatedBy(boardEntity.getCreatedBy());
//                    boardDTO.setImageUrls(boardEntity.getImages().stream()
//                            .map(BoardImageEntity::getImageUrl)
//                            .collect(Collectors.toList()));
//                    return boardDTO;
//                })
//                .collect(Collectors.toList());
//    }
//}
