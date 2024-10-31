package com.dbq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO {
    private String message;
    private String channelId;
    private String from;
    private String to;
    private String id;
}
