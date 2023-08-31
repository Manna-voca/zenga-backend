package com.mannavoca.zenga.domain.comment.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.comment.application.dto.response.CommentInfoResponseDto;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.service.CommentService;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSearchUseCase {
    private final ParticipationService participationService;
    private final CommentService commentService;

    public SliceResponse<CommentInfoResponseDto> getParentCommentList(Long partyId, Long commentId, Pageable pageable) {
        Long partyMarkerId = participationService.getPartyMarkerId(partyId);
        Slice<Comment> commentSlice = commentService.findAllParentCommentInParty(partyId, commentId, pageable);

        List<CommentInfoResponseDto> parentCommentList = new ArrayList<>();
        Map<Long, List<CommentInfoResponseDto>> childCommentMap = new HashMap<>();

        commentSlice.getContent().stream()
                .map(comment -> CommentInfoResponseDto.of(comment, partyMarkerId))
                .forEach(commentDto -> {
                    if (commentDto.getParentId() == null) {
                        parentCommentList.add(commentDto);
                    } else {
                        Long parentId = commentDto.getParentId();
                        childCommentMap
                                .computeIfAbsent(parentId, k -> new ArrayList<>())
                                .add(commentDto);
                    }
                });

        parentCommentList.forEach(parent -> {
            List<CommentInfoResponseDto> children = childCommentMap.getOrDefault(parent.getCommentId(), Collections.emptyList());
            parent.getChildComments().addAll(children);
        });

        return SliceResponse.of(new SliceImpl<>(parentCommentList, pageable, commentSlice.hasNext()));
    }

}
