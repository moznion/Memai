package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.TouchResponseType;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TouchResponse implements Response<TouchResponseType> {
    private TouchResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
}
