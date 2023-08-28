package com.mannavoca.zenga.domain.praise.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_candidate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Member candidate;

    @Builder
    public Candidate(Member member, Member candidate) {
        this.member = member;
        this.candidate = candidate;
    }

    public void updateCandidate(Member newCandidate){
        this.candidate = newCandidate;
    }
}
