package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.FlushAllResponseType;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class FlushAllResponse implements Response<FlushAllResponseType> {
    private boolean succeeded;
    private FlushAllResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
}
