package com.dbq.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.dbq.model.ChatChannel;

import reactor.core.publisher.Mono;

@Repository
public interface ChatChannelRepository extends ReactiveMongoRepository<ChatChannel, String> {
    
	Mono<ChatChannel> findByChannelId(String channelId);
}
