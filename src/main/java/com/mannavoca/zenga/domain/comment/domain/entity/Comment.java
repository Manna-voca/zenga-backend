package com.mannavoca.zenga.domain.comment.domain.entity;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "zg_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(Party party, Member writer, String content) {
        this.party = party;
        this.writer = writer;
        this.content = content;
    }

    public static Comment toEntity(Party party, Member member, String content) {
        return Comment.builder()
                .party(party)
                .writer(member)
                .content(content)
                .build();
    }

    public void updateParent(Comment parent) {
        this.parent = parent;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
