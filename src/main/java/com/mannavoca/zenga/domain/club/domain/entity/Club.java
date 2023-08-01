package com.mannavoca.zenga.domain.club.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="capacity")
    private Integer capacity;

    @Column(name="logo_image_url")
    private String logoImageUrl;

    @Column(name="description")
    private String description;

    @Column(name="finished_at")
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> memberList;

    @Builder
    public Club(String name, Integer capacity, String logoImageUrl, String description, LocalDateTime finishedAt) {
        this.name = name;
        this.capacity = capacity;
        this.logoImageUrl = logoImageUrl;
        this.description = description;
        this.finishedAt = finishedAt;
    }
}
