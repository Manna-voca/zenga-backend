package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PraiseRepository extends JpaRepository<Praise, Long>, PraiseRepositoryCustom {
}
