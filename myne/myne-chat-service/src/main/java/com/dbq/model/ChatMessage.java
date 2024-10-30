package com.dbq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Document(collection = "chatmessages")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private String message;
    private String from;
    
    @CreatedDate
    private Instant createdDate = Instant.now();
    private String channelId;
}
