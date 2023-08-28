package com.mannavoca.zenga.domain.comment.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.comment.application.service.CommentDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentDeleteController {
    private final CommentDeleteUseCase commentDeleteUseCase;

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteMyComment
            (
                    @RequestParam Long channelId,
                    @RequestParam Long commentId
            )
    {
        commentDeleteUseCase.deleteComment(channelId, commentId);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
