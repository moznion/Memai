package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.TouchResponse;
import net.moznion.memai.memcached.protocol.response.type.TouchResponseType;

import java.util.Optional;

public class TextTouchResponseProtocol implements TextResponseProtocol<TouchResponse> {
    public TouchResponse parse(final String response) throws IllegalMemcachedErrorResponseProtocolException {
        final String trimmed = response.trim();

        switch (trimmed) {
            case "TOUCHED":
                return new TouchResponse(TouchResponseType.TOUCHED, Optional.empty());
            case "NOT_FOUND":
                return new TouchResponse(TouchResponseType.NOT_FOUND, Optional.empty());
            default:
                return new TouchResponse(TouchResponseType.ERROR,
                        Optional.of(new TextErrorResponseProtocol().parse(response)));
        }
    }
}
