package com.mannavoca.zenga.domain.point.domain.repository;

import com.mannavoca.zenga.domain.point.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {
}
