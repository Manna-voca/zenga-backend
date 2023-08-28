package com.mannavoca.zenga.domain.praise.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.domain.entity.Candidate;
import com.mannavoca.zenga.domain.praise.domain.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DomainService
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public Boolean existsCandidateHistory(Long memberId) {
        return candidateRepository.existsByMember_Id(memberId);
    }
    public void saveCandidateBulk(Member member, List<Member> candidates) {
        List<Candidate> candidateList = candidates.stream()
                .map(candidate -> Candidate.builder().member(member).candidate(candidate).build())
                .collect(Collectors.toList());
        candidateRepository.saveAll(candidateList);
    }

    public List<Member> getCandidates(Long memberId) {
        return candidateRepository.findCandidateMembersByMember_Id(memberId)
                .stream().map(Candidate::getCandidate).collect(Collectors.toList());
    }

    public void updateCandidates(Long memberId, List<Member> newCandidates) {
        if (newCandidates.isEmpty()) { throw BusinessException.of(Error.DATA_NOT_FOUND); }

        List<Candidate> candidates = candidateRepository.findCandidatesByMember_Id(memberId);

        if (candidates.size() != newCandidates.size()) { throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); }

        IntStream.range(0, candidates.size()).forEach(i -> candidates.get(i).updateCandidate(newCandidates.get(i)));

        candidateRepository.saveAll(candidates);
    }


}
