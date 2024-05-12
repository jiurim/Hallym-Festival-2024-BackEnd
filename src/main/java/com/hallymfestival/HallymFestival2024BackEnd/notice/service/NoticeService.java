package com.hallymfestival.HallymFestival2024BackEnd.notice.service;

import com.hallymfestival.HallymFestival2024BackEnd.notice.domain.NoticeRepository;
import com.hallymfestival.HallymFestival2024BackEnd.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.notice.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public NoticeDto getNotice(){
        Notice notice = noticeRepository.findById(1).orElse(null);
        if (notice != null) {
            return new NoticeDto(notice.getTitle(), notice.getContent()); // getTitle() 추가
        } else {
            return null;
        }
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
