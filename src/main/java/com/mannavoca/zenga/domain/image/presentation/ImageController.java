package com.mannavoca.zenga.domain.image.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.infrastructure.s3.S3UploadService;
import com.mannavoca.zenga.domain.image.presentation.dto.response.UploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto<UploadResponseDto>> uploadFile(@RequestPart("image") MultipartFile multipartFile){
            String url = s3UploadService.uploadImg(multipartFile);
            
            return ResponseEntity.ok(ResponseDto.success(UploadResponseDto.of(url)));
    }
}
