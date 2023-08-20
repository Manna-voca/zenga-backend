package com.mannavoca.zenga.domain.channel.domain.repository;

import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom {

    Optional<Channel> findByCode(String code);
}
