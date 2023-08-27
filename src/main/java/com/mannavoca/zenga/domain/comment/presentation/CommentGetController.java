package com.mannavoca.zenga.domain.comment.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.comment.application.dto.response.CommentInfoResponseDto;
import com.mannavoca.zenga.domain.comment.application.service.CommentSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentGetController {
    private final CommentSearchUseCase commentSearchUseCase;

    @GetMapping("")
    public ResponseEntity<ResponseDto<SliceResponse<CommentInfoResponseDto>>> getParentCommentList
            (
                    @RequestParam Long partyId,
                    @RequestParam(required = false) Long commentId,
                    @PageableDefault(size = 6) Pageable pageable
            )
    {
        SliceResponse<CommentInfoResponseDto> parentCommentList = commentSearchUseCase.getParentCommentList(partyId, commentId, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "모임 댓글 조회 성공하였습니다.", parentCommentList));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseDto<List<CommentInfoResponseDto>>> getChildCommentList
            (
                    @PathVariable Long commentId
            )
    {
        List<CommentInfoResponseDto> childCommentList = commentSearchUseCase.getChildCommentList(commentId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "대댓글 조회을 성공하였습니다.", childCommentList));
    }
}
