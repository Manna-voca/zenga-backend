package com.mannavoca.zenga.domain.praise.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_badge")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Praise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "category", nullable = false)
    private PraiseType category;

    @Builder
    public Praise(String description, PraiseType category) {
        this.description = description;
        this.category = category;
    }
}
