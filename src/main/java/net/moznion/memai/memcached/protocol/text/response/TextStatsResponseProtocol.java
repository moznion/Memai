package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedStatsResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.ErrorResponse;
import net.moznion.memai.memcached.protocol.response.StatsResponse;
import net.moznion.memai.memcached.protocol.response.type.StatsResponseType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TextStatsResponseProtocol implements TextResponseProtocol<StatsResponse> {
    public StatsResponse parse(final String response) throws IllegalMemcachedStatsResponseProtocolException {
        final Map<String, String> stats = new HashMap<>();

        for (String line : response.split("\r\n")) {
            final List<String> splitted = Arrays.asList(line.split(" "));
            final String cmd = splitted.get(0);

            if (cmd.equals("END")) {
                break;
            }

            if (!cmd.equals("STAT")) {
                final ErrorResponse errorResponse;
                try {
                    errorResponse = new TextErrorResponseProtocol().parse(response);
                    return new StatsResponse(StatsResponseType.FAILED, Optional.of(errorResponse), Collections.emptyMap(), false);
                } catch (IllegalMemcachedErrorResponseProtocolException e) {
                    throw new IllegalMemcachedStatsResponseProtocolException();
                }
            }

            if (splitted.size() != 3) {
                throw new IllegalMemcachedStatsResponseProtocolException();
            }

            stats.put(splitted.get(1), splitted.get(2));
        }

        return new StatsResponse(StatsResponseType.OK, Optional.empty(), stats, true);
    }
}
