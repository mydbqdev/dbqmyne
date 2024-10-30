package com.dbq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Document(collection = "chatchannels")
@Getter
@Setter
@NoArgsConstructor
public class ChatChannel {
    @Id
    private String id;
    private String channelId;
    private Set<String> users=new HashSet<String>();
}
