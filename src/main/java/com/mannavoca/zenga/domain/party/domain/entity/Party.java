package com.mannavoca.zenga.domain.party.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_party")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name="max_capacity")
    private Integer maxCapacity;

    @Column(name="start_at")
    private LocalDateTime startAt;

    @Column(name="end_at")
    private LocalDateTime endAt;

    @Column(name="party_image_url")
    private String partyImageUrl;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participationList;

    @Builder
    public Party(String title, String content, Integer maxCapacity, LocalDateTime startAt, LocalDateTime endAt, String partyImageUrl) {
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.startAt = startAt;
        this.endAt = endAt;
        this.partyImageUrl = partyImageUrl;
    }
}