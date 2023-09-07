package com.mannavoca.zenga.domain.notification.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="is_check")
    private Boolean isCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void check() {
        this.isCheck = true;
    }

    public static Notification createNotification(String title, Member member, String content) {
        return Notification.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
    @Builder
    public Notification(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.isCheck = false;
        this.member = member;
    }
}
