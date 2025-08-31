package com.dev.dechanel.auth.service.domains.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private Integer code;

    private String timestamp;


}
