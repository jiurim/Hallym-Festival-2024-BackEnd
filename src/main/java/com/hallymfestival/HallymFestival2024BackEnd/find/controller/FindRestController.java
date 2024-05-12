package com.hallymfestival.HallymFestival2024BackEnd.find.controller;

import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindAddRequest;
import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindApiResponse;
import com.hallymfestival.HallymFestival2024BackEnd.find.service.FindServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FindRestController {
    private final FindServiceImpl findService;


    @GetMapping("/find")
    public ResponseEntity<List<FindApiResponse>> getBoardList(Model model) {
        List<FindApiResponse> findList = findService.getList();
        return ResponseEntity.ok().body(findList);
    }

    @PostMapping("/find")
    public ResponseEntity<FindApiResponse> addFind(@RequestBody FindAddRequest findAddRequest){
        FindApiResponse createFind = findService.addFind(findAddRequest);
        return ResponseEntity.ok().body(createFind);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FindApiResponse> getFindById(@PathVariable long findid) {
        FindApiResponse find = findService.getFindById(findid);
        return ResponseEntity.ok().body(find);
    }

    @PutMapping("/find/{id}")
    public ResponseEntity<FindApiResponse> updateFind(@PathVariable long findid, @RequestBody FindAddRequest request) {
        FindApiResponse updatedFind = findService.updateFind(findid, request);
        return ResponseEntity.ok().body(updatedFind);
    }

    @DeleteMapping("/find/{id}")
    public ResponseEntity<Void> deleteFind(@PathVariable long findid) {
        findService.deleteFind(findid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/find/return/{findid}")
    public ResponseEntity<FindApiResponse> completeReturn(@PathVariable long findid) {
        FindApiResponse returnedFind = findService.completeReturn(findid);

        if ( returnedFind == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(returnedFind);
    }
}