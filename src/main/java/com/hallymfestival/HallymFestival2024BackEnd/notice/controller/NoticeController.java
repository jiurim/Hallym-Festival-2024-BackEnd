package com.hallymfestival.HallymFestival2024BackEnd.notice.controller;

import com.hallymfestival.HallymFestival2024BackEnd.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService=noticeService;
    }

    @PostMapping("/notice")
    public NoticeDto createNotice(@RequestBody NoticeDto noticeDto){
        return noticeService.createNotice(noticeDto);
    }

    @GetMapping("/notice")
    public NoticeDto getNotice(){
        return noticeService.getNotice();
    }

    @PutMapping("/board/{id}")
    public NoticeDto update(@PathVariable Integer id, @RequestBody NoticeDto noticeDto){
        return noticeService.update(id, noticeDto);
    }
}
