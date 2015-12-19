package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.IncrementalResponseType;

import java.util.Optional;
import java.util.OptionalLong;

@Getter
@AllArgsConstructor
public class IncrementalResponse implements Response<IncrementalResponseType> {
    private IncrementalResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
    private OptionalLong value;
    private boolean affected;
}
