package com.mannavoca.zenga.domain.block.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "block_type")
    private BlockType blockType;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberBlock> memberBlockList;

    @Builder
    public Block(BlockType blockType, String description) {
        this.blockType = blockType;
        this.description = description;
    }
}
