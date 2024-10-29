package com.dbq.authservice.dto;

import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String token;
    @JsonInclude(JsonInclude.Include. NON_NULL)
    private String refreshToken;
}
