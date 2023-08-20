package com.mannavoca.zenga.domain.party.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
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

    @Column(name="location")
    private String location;

    @Column(name="party_date")
    private LocalDateTime partyDate;

    @Column(name="party_image_url")
    private String partyImageUrl;

    @Column(name="card_image_url")
    private String cardImageUrl;

    @Column(name="is_open")
    private Boolean isOpen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participationList;

    @Builder
    public Party(String title, String content, Integer maxCapacity, String location, LocalDateTime partyDate, String partyImageUrl, String cardImageUrl, Channel channel, Boolean isOpen) {
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.location = location;
        this.partyDate = partyDate;
        this.partyImageUrl = partyImageUrl;
        this.cardImageUrl = cardImageUrl;
        this.isOpen = isOpen;
        this.channel = channel;
    }

    public void updateIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
