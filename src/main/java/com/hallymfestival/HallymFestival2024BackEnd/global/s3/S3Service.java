package com.hallymfestival.HallymFestival2024BackEnd.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

@Slf4j // 롬복을 사용하여 로깅을 위한 Logger를 자동으로 생성
@Service // Spring의 Service로 등록
public class S3Service {

    private final AmazonS3 amazonS3; // AmazonS3 클라이언트
    private final String bucket; // S3 버킷 이름

    // 생성자 주입을 통해 AmazonS3 클라이언트와 S3 버킷 이름을 주입받음
    public S3Service(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
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


    // 이미지 수정으로 인해 기존 이미지 삭제 메소드
    public void deleteFile(String fileUrl) {
        try {
            String decodedFileName = URLDecoder.decode(fileUrl, "UTF-8");
            String splitStr = ".com/";
            String fileName = decodedFileName.substring(decodedFileName.lastIndexOf(splitStr) + splitStr.length());
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
            log.info("File deleted successfully from S3: " + fileName);
        } catch (UnsupportedEncodingException e) {
            log.error("Error while decoding the file name: {}", e.getMessage());
        }
    }

//    // S3에서 파일 삭제하는 메서드
//    public void deleteFile(String fileName) {
//        try {
//            // URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
//            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
//            // URL에서 파일 이름을 추출합니다.
//            String s3fileName = decodedFileName.substring(decodedFileName.lastIndexOf("/") + 1);
//            log.info("Extracted file name from URL: " + s3fileName);
//
//            amazonS3.deleteObject(bucket,"board/"+ s3fileName);
//            // 성공적으로 삭제되었음을 로그로 남깁니다.
//            log.info("File deleted successfully from S3: " + decodedFileName);
//        } catch (UnsupportedEncodingException e) {
//            log.error("Error while decoding the file name: {}", e.getMessage());
//        }
//    }



//    // S3에서 파일 삭제하는 메서드
//    public void deleteFile(String fileName) {
//        try {
//            // URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
//            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
//            log.info("Deleting file from S3: " + decodedFileName);
//            amazonS3.deleteObject(bucket, decodedFileName);
//            // 성공적으로 삭제되었음을 로그로 남깁니다.
//            log.info("File deleted successfully from S3: " + decodedFileName);
//        } catch (UnsupportedEncodingException e) {
//            log.error("Error while decoding the file name: {}", e.getMessage());
//        }
//    }


//    // find image from s3
//    public String getThumbnailPath(String path) {
//        return amazonS3.getUrl(bucket, path).toString();
//    }
//
//    //remove s3 object
//    public void deleteFile(String fileName){
//        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
//        amazonS3.deleteObject(request);
//    }
//


//    // 파일 업데이트 메서드
//    public String updateFile(MultipartFile newFile, String oldFileName, String dirName) throws IOException {
//        // 기존 파일 삭제
//        log.info("S3 oldFileName: " + oldFileName);
//        deleteFile(oldFileName);
//
//        // 새 파일 업로드
//        return upload(newFile, dirName);
//    }
}
