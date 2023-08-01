package com.mannavoca.zenga.domain.user.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "zg_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="role")
    private RoleType role;

    @Column(name="name")
    private String name;

    @Column(name="gender")
    private GenderType gender;

    @Column(name="birth")
    private LocalDate birth;

    @Column(name="social_id", unique = true)
    private String socialId;

    @Builder
    public User(RoleType role, String name, GenderType gender, LocalDate birth, String socialId) {
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.socialId = socialId;
    }

    public static User toInitEntity(String socialId){
        return User.builder()
                .role(RoleType.USER)
                .socialId(socialId)
                .build();
    }
}
