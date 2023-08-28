package com.mannavoca.zenga.domain.comment.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.mannavoca.zenga.domain.comment.application.service.CommentCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentCreateUseCase commentCreateUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> writeComment
            (
                    @RequestBody CommentWriteRequestDto commentWriteRequestDto
            )
    {
        commentCreateUseCase.createComment(commentWriteRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
