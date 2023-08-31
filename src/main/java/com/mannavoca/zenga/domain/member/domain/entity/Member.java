package com.mannavoca.zenga.domain.member.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.badge.domain.entity.MemberBadge;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.point.domain.entity.Point;
import com.mannavoca.zenga.domain.praise.domain.entity.Candidate;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_member",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_channel_id_user_id",
                    columnNames = {"channel_id", "user_id"}
            )
        },
        indexes = @Index(name = "idx_channel_id", columnList = "channel_id")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="level")
    private LevelType level;

    @Column(name="profile_image_url")
    private String profileImageUrl;

    @Column(name="nickname")
    private String nickname;

    @Column(name="introduction")
    private String introduction;

    @Column(name="praise_modal")
    private Boolean praiseModal;

    @Column(name="point_modal")
    private Boolean pointModal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "praisedMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPraise> memberPraiseList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> praiseMember;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidateList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberBadge> memberBadgeList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> pointList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participationList;

    @Builder
    public Member(LevelType level, String profileImageUrl, String nickname, String introduction, User user, Channel channel) {
        this.level = level;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.introduction = introduction;
        this.user = user;
        this.channel = channel;
        this.praiseModal = true;
        this.pointModal = true;
    }

    public void updateProfile(String profileImageUrl, String nickname, String introduction) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.introduction = introduction;
    }
}
