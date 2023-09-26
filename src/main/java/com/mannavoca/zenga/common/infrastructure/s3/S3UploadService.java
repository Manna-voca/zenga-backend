package com.mannavoca.zenga.common.infrastructure.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.img_bucket}")
    private String bucket;

    @Value("${cdn.url}")
    private String cloudFrontUrl;

    public List<String> uploadImgList(List<MultipartFile> imgList) {

        if(Objects.isNull(imgList)) return null;
        if(imgList.isEmpty()) return null;
        List<String> uploadUrl = new ArrayList<>();
        for (MultipartFile img : imgList) {
            uploadUrl.add(uploadImg(img));
        }
        return uploadUrl;
    }


    //단일 이미지 업로드
    public String uploadImg(MultipartFile file) {
        if(Objects.isNull(file)) return null;
        if(file.isEmpty()) return null;

        String originFileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);
        String fileName = createFileName(originFileName);
        ObjectMetadata objectMetadata = createObjectMetadata(file);

        try (ByteArrayOutputStream baos = resizeImage(file)) {
            uploadToS3(fileName, baos, objectMetadata);
        } catch (IOException e) {
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
        String s3Path = amazonS3.getUrl(bucket, fileName).getPath();
        String cdnUrl = cloudFrontUrl + s3Path;
        return cdnUrl;
    }

    //파일명 난수화
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //파일 확장자 체크
    private String getFileExtension(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".jpeg") && !ext.equals(".svg+xml") && !ext.equals(".svg") && !ext.equals(".webp")) {
            throw BusinessException.of(Error.FILE_EXTENTION_ERROR);
        }
        return ext;
    }

    private ByteArrayOutputStream resizeImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage resizedImage = Thumbnails.of(originalImage)
                .size(960, 960)
                .asBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", baos);
        return baos;
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    private void uploadToS3(String fileName, ByteArrayOutputStream baos, ObjectMetadata metadata) {
        metadata.setContentLength(baos.size());
        try (InputStream inputStream = new ByteArrayInputStream(baos.toByteArray())) {
            PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
        } catch (Exception e) {
            log.info("S3 업로드 실패 : {}", e.getMessage());
            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
        }
    }
}
