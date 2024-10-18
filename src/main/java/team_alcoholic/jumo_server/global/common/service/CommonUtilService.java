package team_alcoholic.jumo_server.global.common.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommonUtilService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String s3Bucket;

    @Value("${spring.cloud.aws.s3.uriprefix}")
    private String s3Endpoint;

    private final S3Template s3Template;

    /**
     * 파일을 S3에 업로드하는 메서드
     * @param imageFile "pathname/" 형식으로 전달
     * @throws IOException
     */
    public String uploadImageToS3(MultipartFile imageFile, String path) throws IOException {
        // 파일 이름 설정: 현재시간 + UUID + 확장자
        String originalFilename = imageFile.getOriginalFilename();
        int idx = originalFilename.lastIndexOf('.');
        String ext = (idx == -1) ? "" : originalFilename.substring(idx);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String filename = path + LocalDateTime.now().format(formatter) + "_" + UUID.randomUUID();
        String newFilename = filename + ext;

        // S3에 파일 업로드
        InputStream inputStream = imageFile.getInputStream();
        s3Template.upload(s3Bucket, newFilename, inputStream);

        // 접근 url 반환
        return s3Endpoint + newFilename;
    }
}
