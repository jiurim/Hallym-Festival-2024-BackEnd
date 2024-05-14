package com.hallymfestival.HallymFestival2024BackEnd.domain.notice.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.entity.Notice;
import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository){
        this.noticeRepository=noticeRepository;
    }

    public NoticeDto createNotice(NoticeDto noticeDTO) {
        Notice notice = new Notice();
        log.info("일단 들어옴");
        notice.setTitle(noticeDTO.getTitle());
        log.info("title 들어옴");
        notice.setContent(noticeDTO.getContent());
        log.info("content 들어옴");
        noticeRepository.save(notice);
        log.info("저장됨");
        return noticeDTO;
    }

    public List<NoticeDto> getNotice(){
        List<Notice> noticeList = noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        for (Notice notice : noticeList) {
            NoticeDto noticeDto = new NoticeDto(notice.getId(),notice.getTitle(), notice.getContent());
            noticeDtoList.add(noticeDto);
        }

        return noticeDtoList;
    }

    public NoticeDto update(Integer id, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        noticeRepository.save(notice);

        return noticeDto;
    }
}