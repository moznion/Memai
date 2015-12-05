package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.RetrievalResponseType;

import java.util.Map;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class RetrievalResponse implements Response<RetrievalResponseType> {
    private RetrievalResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
    private Map<String, ValueResponse> values;
}
