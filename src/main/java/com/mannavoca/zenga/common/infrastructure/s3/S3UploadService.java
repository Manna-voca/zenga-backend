package com.mannavoca.zenga.common.infrastructure.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.s3.img_bucket}")
//    private String bucket;
//
//    public List<String> uploadImgList(List<MultipartFile> imgList) {
//
//        if(Objects.isNull(imgList)) return null;
//        if(imgList.isEmpty()) return null;
//        List<String> uploadUrl = new ArrayList<>();
//        for (MultipartFile img : imgList) {
//            uploadUrl.add(uploadImg(img));
//        }
//        return uploadUrl;
//    }
//
//
//    //단일 이미지 업로드
//    public String uploadImg(MultipartFile file) {
//        if(Objects.isNull(file)) return null;
//        if(file.isEmpty()) return null;
//
//        String originFileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);
//        String fileName = createFileName(originFileName);
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(file.getSize());
//        objectMetadata.setContentType(file.getContentType());
//
//        try (InputStream inputStream = file.getInputStream()) {
//            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (IOException e) {
//            throw BusinessException.of(Error.FILE_UPLOAD_ERROR);
//        }
//        return amazonS3.getUrl(bucket, fileName).toString();
//    }
//
//    //파일명 난수화
//    private String createFileName(String fileName) {
//        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
//    }
//
//    //파일 확장자 체크
//    private String getFileExtension(String fileName) {
//        String ext = fileName.substring(fileName.lastIndexOf('.'));
//        if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".jpeg") && !ext.equals(".svg+xml") && !ext.equals(".svg")) {
//            throw BusinessException.of(Error.FILE_EXTENTION_ERROR);
//        }
//        return ext;
//    }
}
