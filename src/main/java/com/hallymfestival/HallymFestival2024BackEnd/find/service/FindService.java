package com.hallymfestival.HallymFestival2024BackEnd.find.service;


import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindAddRequest;
import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindApiResponse;

import java.util.List;

public interface FindService {

    List<FindApiResponse> getList();
    FindApiResponse addFind(FindAddRequest findAddRequest);

    FindApiResponse getFindById(long findid);

    FindApiResponse updateFind(long findid, FindAddRequest request);

    void deleteFind(long findid);

    FindApiResponse completeReturn(long findid);

}
