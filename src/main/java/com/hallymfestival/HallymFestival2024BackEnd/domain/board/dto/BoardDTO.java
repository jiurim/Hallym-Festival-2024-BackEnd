package com.hallymfestival.HallymFestival2024BackEnd.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BoardDTO {
    private long id;
    private String title;
    private String content;
    private String createdBy;
    private List<String> imageUrls; // 이미지 URL을 여러 개 받기 위한 필드
    private List<MultipartFile> files;
}
