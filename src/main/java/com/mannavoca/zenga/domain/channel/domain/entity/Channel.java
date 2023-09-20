package com.mannavoca.zenga.domain.channel.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.channel.application.dto.request.UpdatingChannelRequestDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "logo_image_url")
    private String logoImageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    @Setter
    private String code;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Party> partyList;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> memberList;

    @Builder
    public Channel(String name, Integer capacity, String logoImageUrl, String description, LocalDateTime finishedAt) {
        this.name = name;
        this.capacity = capacity;
        this.logoImageUrl = logoImageUrl;
        this.description = description;
        this.finishedAt = finishedAt;
    }

    public void updateChannel(UpdatingChannelRequestDto updatingChannelRequestDto) {
        this.name = updatingChannelRequestDto.getName() == null ? this.name : updatingChannelRequestDto.getName();
        this.logoImageUrl = updatingChannelRequestDto.getLogoImageUrl() == null ? this.logoImageUrl : updatingChannelRequestDto.getLogoImageUrl();
    }
}
