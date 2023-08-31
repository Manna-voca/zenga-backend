package com.mannavoca.zenga.domain.comment.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.comment.application.dto.request.CommentChangeRequestDto;
import com.mannavoca.zenga.domain.comment.application.service.CommentUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentPatchController {
    private final CommentUpdateUseCase commentUpdateUseCase;

    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> changeCommentContent
            (
                    @RequestBody CommentChangeRequestDto changeRequestDto
            )
    {
        commentUpdateUseCase.updateCommentContent(changeRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
