package com.mannavoca.zenga.domain.praise.domain.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.domain.entity.Candidate;
import com.mannavoca.zenga.domain.praise.domain.repository.CandidateRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final JdbcTemplate jdbcTemplate;

    public Boolean existsCandidateHistory(Long memberId) {
        return candidateRepository.existsByMember_Id(memberId);
    }
    public void saveCandidateBulk(Member member, List<Member> candidates) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO zg_candidate (member_id, candidate_id) VALUES (?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, member.getId());
                    ps.setLong(2, candidates.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return candidates.size();
                }
            }
        );
    }

    public List<Member> getCandidates(Long memberId) {
        return candidateRepository.findCandidateMembersByMember_Id(memberId)
                .stream().map(Candidate::getCandidate).collect(Collectors.toList());
    }

    public void deletedAndSaveBulkCandidates(Member member, List<Member> newCandidates) {
        if (newCandidates.isEmpty()) { throw BusinessException.of(Error.DATA_NOT_FOUND); }

        // bulk delete
        candidateRepository.bulkDeleteCandidate(member.getId());

        // bulk insert
        saveCandidateBulk(member, newCandidates);
    }



}
