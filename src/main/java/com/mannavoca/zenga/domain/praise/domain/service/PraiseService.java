package com.mannavoca.zenga.domain.praise.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import com.mannavoca.zenga.domain.praise.domain.repository.PraiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class PraiseService {
    private final PraiseRepository praiseRepository;

    public Praise findRandomPraise() {
        return praiseRepository.findRandomPraise();
    }
}
