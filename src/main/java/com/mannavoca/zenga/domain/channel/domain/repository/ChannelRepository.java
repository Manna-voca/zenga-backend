package com.mannavoca.zenga.domain.channel.domain.repository;

import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom {

}
