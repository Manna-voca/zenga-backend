package com.mannavoca.zenga.domain.block.domain.repository;

import com.mannavoca.zenga.domain.block.domain.entity.MemberBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBlockRepository extends JpaRepository<MemberBlock, Long>, MemberBlockRepositoryCustom{
}
