package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.IncrementalResponse;
import net.moznion.memai.memcached.protocol.response.type.IncrementalResponseType;

import java.util.Optional;
import java.util.OptionalLong;

public class TextIncrementalResponseProtocol implements TextResponseProtocol<IncrementalResponse> {
    public IncrementalResponse parse(final String response) throws IllegalMemcachedErrorResponseProtocolException {
        final String trimmed = response.trim();

        if (trimmed.equals("NOT_FOUND")) {
            return new IncrementalResponse(IncrementalResponseType.NOT_FOUND, Optional.empty(), OptionalLong.empty());
        }

        try {
            final long l = Long.parseLong(trimmed, 10);
            return new IncrementalResponse(IncrementalResponseType.VALUE_CHANGED, Optional.empty(), OptionalLong.of(l));
        } catch (NumberFormatException e) {
            return new IncrementalResponse(IncrementalResponseType.ERROR,
                    Optional.of(new TextErrorResponseProtocol().parse(response)), OptionalLong.empty());
        }
    }
}
