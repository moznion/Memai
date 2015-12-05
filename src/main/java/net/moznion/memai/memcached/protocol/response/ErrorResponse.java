package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.ErrorResponseType;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final ErrorResponseType errorType;
    private final String errorMessage;
}
