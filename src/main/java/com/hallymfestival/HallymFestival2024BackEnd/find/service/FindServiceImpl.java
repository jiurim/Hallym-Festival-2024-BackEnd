package com.hallymfestival.HallymFestival2024BackEnd.find.service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.hallymfestival.HallymFestival2024BackEnd.find.domain.FindRepository;
import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindAddRequest;
import com.hallymfestival.HallymFestival2024BackEnd.find.dto.FindApiResponse;
import com.hallymfestival.HallymFestival2024BackEnd.find.entity.FindEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FindServiceImpl implements FindService {

    private FindRepository findRepository;

    @Override
    public List<FindApiResponse> getList() {
        List<FindEntity> findEntities = findRepository.findAll();
        return findEntities.stream()
                .map(this::mapToApiResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FindApiResponse addFind(FindAddRequest findAddRequest) {
        FindEntity entity = mapToEntity(findAddRequest);
        FindEntity savedEntity = findRepository.save(entity);
        return mapToApiResponse(savedEntity);
    }

    @Override
    public FindApiResponse getFindById(long id) {
        Optional<FindEntity> findEntityOptional = findRepository.findById(id);
        if (findEntityOptional.isPresent()) {
            FindEntity findEntity = findEntityOptional.get();
            return mapToApiResponse(findEntity);
        }
        return null;
    }

    @Override
    public FindApiResponse updateFind(long findid, FindAddRequest request) {
        Optional<FindEntity> findEntityOptional = findRepository.findById(findid);
        if (findEntityOptional.isPresent()) {
            FindEntity findEntity = findEntityOptional.get();
            // Update the entity with the new data
            findEntity.setName(request.getName());
            findEntity.setPoint(request.getPoint());
            findEntity.setLocation(request.getLocation());
            findEntity.setImage_url(request.getImage_url());
            findEntity.setUpdate_time(LocalDateTime.now());
            findEntity.setReturnYn(request.isReturnYn());
            // Save the updated entity
            FindEntity updatedEntity = findRepository.save(findEntity);
            return mapToApiResponse(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteFind(long id) {
        findRepository.deleteById(id);
    }

    @Override
    public FindApiResponse completeReturn(long id) {
        Optional<FindEntity> findEntityOptional = findRepository.findById(id);
        //왜 굳이 optional인지 생각해보면 좋을듯하다.

        if (findEntityOptional.isEmpty() || findEntityOptional.get().isReturnYn()) {
            // 아이디가 없거나, 이미 회수 완료된 경우
            return null;
        }

        // 정상경우
        if (findEntityOptional.isPresent()) {
            FindEntity findEntity = findEntityOptional.get();
            findEntity.setReturnYn(true);
            FindEntity updatedEntity = findRepository.save(findEntity);
            return mapToApiResponse(updatedEntity);
        }

        return null;
    }

    private FindApiResponse mapToApiResponse(FindEntity findEntity) {
        return FindApiResponse.builder()
                .name(findEntity.getName())
                .point(findEntity.getPoint())
                .location(findEntity.getLocation())
                .image_url(findEntity.getImage_url())
                .update_time(findEntity.getUpdate_time())
                .returnYn(findEntity.isReturnYn())
                .build();
    }

    private FindEntity mapToEntity(FindAddRequest findAddRequest) {
        return FindEntity.builder()
                .name(findAddRequest.getName())
                .point(findAddRequest.getPoint())
                .location(findAddRequest.getLocation())
                .image_url(findAddRequest.getImage_url())
                .update_time(LocalDateTime.now())
                .returnYn(findAddRequest.isReturnYn())
                .build();
    }

}