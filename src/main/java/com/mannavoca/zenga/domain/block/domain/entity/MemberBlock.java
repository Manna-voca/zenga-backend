package com.mannavoca.zenga.domain.block.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "zg_member_block",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_block_id_member_id",
                    columnNames = {"block_id","member_id"}
            )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBlock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private Block block;

    @Builder
    public MemberBlock(Member member, Block block) {
        this.member = member;
        this.block = block;
    }
}
