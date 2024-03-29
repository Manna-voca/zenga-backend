package com.mannavoca.zenga.domain.user.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.point.domain.entity.Point;
import com.mannavoca.zenga.domain.user.application.dto.request.UpdatingUserInfoRequestDto;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Point> points;

    @Builder
    public User(String name, GenderType gender, LocalDate birth, String socialId) {
        this.role = RoleType.USER;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.socialId = socialId;
    }

//    public static User toInitEntity(String socialId) {
//        return User.builder()
//                .role(RoleType.USER)
//                .socialId(socialId)
//                .build();
//    }

    public void updateUser(final UpdatingUserInfoRequestDto updatingUserInfoRequestDto) {
        this.name = updatingUserInfoRequestDto.getName();
        this.gender = updatingUserInfoRequestDto.getGender();
        this.birth = LocalDate.parse(updatingUserInfoRequestDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
