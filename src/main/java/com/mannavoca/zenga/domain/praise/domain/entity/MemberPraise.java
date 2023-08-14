package com.mannavoca.zenga.domain.praise.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.TimeSectionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_member_badge")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPraise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_section")
    private TimeSectionType timeSection;

    @Column(name = "suffle_count")
    private int suffleCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "praise_member_id")
    private Member praiseMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "praised_member_id")
    private Member praisedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Praise praise;

    @Builder
    public MemberPraise(TimeSectionType timeSection, Member praiseMember, Member praisedMember, Praise praise) {
        this.timeSection = timeSection;
        this.suffleCount = 0;
        this.praiseMember = praiseMember;
        this.praisedMember = praisedMember;
        this.praise = praise;
    }

    public void updateSuffleCount(){
        this.suffleCount++;
    }
}
