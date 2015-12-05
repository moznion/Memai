package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.StatsResponseType;

import java.util.Map;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class StatsResponse implements Response<StatsResponseType> {
    private boolean succeeded;
    private StatsResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
    private Map<String, String> stats;
}
