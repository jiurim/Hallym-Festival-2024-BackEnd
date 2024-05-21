package com.hallymfestival.HallymFestival2024BackEnd.domain.find.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.hallymfestival.HallymFestival2024BackEnd.domain.find.domain.FindRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.find.dto.FindAddRequest;
import com.hallymfestival.HallymFestival2024BackEnd.domain.find.dto.FindApiResponse;
import com.hallymfestival.HallymFestival2024BackEnd.domain.find.entity.FindEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindServiceImpl implements FindService {

    private final FindRepository findRepository;
    private final com.hallymfestival.HallymFestival2024BackEnd.domain.find.service.S3Service s3Service;

    @Autowired
    public FindServiceImpl(FindRepository findRepository, com.hallymfestival.HallymFestival2024BackEnd.domain.find.service.S3Service s3Service) {
        this.findRepository = findRepository;
        this.s3Service = s3Service;
    }

    @Override
    public List<FindApiResponse> getList() {
        List<FindEntity> findEntities = findRepository.findAll();
        return findEntities.stream()
                .map(this::mapToApiResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FindApiResponse addFind(FindAddRequest findAddRequest) throws IOException {
        FindEntity entity = mapToEntity(findAddRequest);
        String imageUrl = s3Service.upload(findAddRequest.getImage(), "find");
        entity.setImage_url(imageUrl);
        FindEntity savedEntity = findRepository.save(entity);
        return mapToApiResponse(savedEntity);
    }

    @Override
    public FindApiResponse updateFind(long id, FindAddRequest request) throws IOException {
        Optional<FindEntity> optionalFindEntity = findRepository.findById(id);
        // ID에 해당하는 엔티티가 존재하는지 확인
        if (optionalFindEntity.isPresent()) {
            FindEntity nowFindEntity = optionalFindEntity.get();
            nowFindEntity.setName(request.getName());
            nowFindEntity.setLocation(request.getLocation());
            if (request.getDelete_image_url() != null) {
                s3Service.deleteFile(request.getDelete_image_url());
                String imageUrl = s3Service.upload(request.getImage(), "find");
                nowFindEntity.setImage_url(imageUrl);
            }
            nowFindEntity = findRepository.save(nowFindEntity);
            return mapToApiResponse(nowFindEntity);
        } else {
            return null;
        }
    }


    @Override
    public void deleteFind(long id) {
        Optional<FindEntity> optionalFindEntity = findRepository.findById(id);
        if (optionalFindEntity.isPresent()) {
            FindEntity nowFindEntity = optionalFindEntity.get();
            s3Service.deleteFile(nowFindEntity.getImage_url());
            findRepository.deleteById(id);
        }
    }

//    @Override
//    public void completeReturn(long id) {
//        Optional<FindEntity> optionalFindEntity = findRepository.findById(id);
//        if (optionalFindEntity.isPresent()) {
//            FindEntity nowFindEntity = optionalFindEntity.get();
//            nowFindEntity.set_return(true);
//            findRepository.save(nowFindEntity);
//        }
//    }


    private FindApiResponse mapToApiResponse(FindEntity findEntity) {
        return FindApiResponse.builder()
                .id(findEntity.getId())
                .name(findEntity.getName())
                .location(findEntity.getLocation())
                .image_url(findEntity.getImage_url())
                .upload_time(findEntity.getUpload_time())
                .build();
    }

    private FindEntity mapToEntity(FindAddRequest findAddRequest) {
        return FindEntity.builder()
                .name(findAddRequest.getName())
                .location(findAddRequest.getLocation())
                .upload_time(LocalDateTime.now())
                .build();
    }

}