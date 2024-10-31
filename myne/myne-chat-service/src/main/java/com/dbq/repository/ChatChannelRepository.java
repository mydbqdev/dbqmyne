package com.dbq.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.dbq.model.ChatChannel;

import reactor.core.publisher.Mono;

@Repository
public interface ChatChannelRepository extends ReactiveMongoRepository<ChatChannel, String> {
    
	Mono<ChatChannel> findById(String channelId);
	Mono<ChatChannel> findByUsers(Set<String> users);
}
