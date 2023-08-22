package com.mannavoca.zenga.domain.image.presentation;

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
    public ResponseEntity<UploadResponseDto> uploadFile(@RequestPart("image") MultipartFile multipartFile){
            String url = s3UploadService.uploadImg(multipartFile);
            // TODO 응답 포맷 어캐할지 얘기하고 리턴 포맷만 바꿔주면 됨
//            return ResponseEntity.ok(UploadResponseDto.of(url));
            return null;
    }
}
