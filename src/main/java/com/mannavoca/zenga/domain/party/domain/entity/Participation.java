package com.mannavoca.zenga.domain.party.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_participation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_id_party_id",
                        columnNames = {"member_id", "party_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_maker")
    private Boolean isMaker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "text")
    private String text;

    @Builder
    public Participation(Boolean isMaker, Member member, Party party, String text) {
        this.isMaker = isMaker;
        this.member = member;
        this.party = party;
        this.isPrivate = false;
        this.text = text;
    }
}
