package com.hallymfestival.HallymFestival2024BackEnd.global.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/make/image")
public class MakeImage {
    private final AmazonS3 amazonS3; // AmazonS3 클라이언트
    private final String bucket; // S3 버킷 이름
    public MakeImage(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3  = amazonS3;
        this.bucket = bucket;
    }

    @PostMapping
    public ResponseEntity<String> addFind(@RequestParam("image") MultipartFile image) throws IOException {
        String imageUrl = upload(image, "front");
        return ResponseEntity.ok(imageUrl);
    }


    // 파일 업로드 메서드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
        String originalFileName = multipartFile.getOriginalFilename();
        // UUID를 파일명에 추가하여 중복 방지
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
        // 업로드할 파일 경로 생성
        String fileName = dirName + "/" + uniqueFileName;
        log.info("fileName: " + fileName);
        // MultipartFile을 File로 변환
        File uploadFile = convert(multipartFile);
        // S3에 파일 업로드 후 URL 반환
        String uploadImageUrl = putS3(uploadFile, fileName);
        // 업로드한 파일 삭제
        removeNewFile(uploadFile);
        // 업로드한 파일의 URL 반환
        return uploadImageUrl;
    }

    // MultipartFile을 File로 변환하는 메서드
    private File convert(MultipartFile file) throws IOException {
        // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
        // 변환된 파일 객체 생성
        File convertFile = new File(uniqueFileName);
        // 파일 생성 후 MultipartFile의 내용을 쓰기
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("파일 변환 중 오류 발생: {}", e.getMessage());
                throw e;
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
    }

    // S3에 파일 업로드하는 메서드
    private String putS3(File uploadFile, String fileName) {
        // S3에 파일 업로드 후 공개 읽기 권한 설정하여 URL 반환
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 업로드한 파일 삭제하는 메서드
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

}
