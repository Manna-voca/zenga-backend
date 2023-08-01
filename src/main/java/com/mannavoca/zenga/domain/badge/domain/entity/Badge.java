package com.mannavoca.zenga.domain.badge.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.badge.domain.entity.enumType.BadgeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_badge")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category", nullable = false)
    private BadgeType category;

    @Column(name="badge_image_url")
    private String badgeImageUrl;

    @Builder
    public Badge(String name, String description, BadgeType category, String badgeImageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.badgeImageUrl = badgeImageUrl;
    }
}
