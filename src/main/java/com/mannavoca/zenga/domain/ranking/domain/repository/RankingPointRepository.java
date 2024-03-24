package com.mannavoca.zenga.domain.ranking.domain.repository;

import com.mannavoca.zenga.domain.ranking.domain.model.RankingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingPointRepository extends JpaRepository<RankingPoint, Long> {

}
