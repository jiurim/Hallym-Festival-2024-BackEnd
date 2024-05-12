package com.hallymfestival.HallymFestival2024BackEnd.find.service;


import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindAddRequest;
import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindApiResponse;

import java.io.IOException;
import java.util.List;

public interface FindService {

    List<FindApiResponse> getList();

    FindApiResponse addFind(FindAddRequest findAddRequest) throws IOException;

    FindApiResponse updateFind(long id, FindAddRequest request) throws IOException;

    void deleteFind(long id);

    void completeReturn(long id);

}