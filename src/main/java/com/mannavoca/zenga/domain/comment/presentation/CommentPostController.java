package com.mannavoca.zenga.domain.comment.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.mannavoca.zenga.domain.comment.application.service.CommentCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentCreateUseCase commentCreateUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> writeComment
            (
                    @RequestParam Long channelId,
                    @RequestBody CommentWriteRequestDto commentWriteRequestDto
            )
    {
        commentCreateUseCase.createComment(channelId, commentWriteRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
