package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.DeleteResponse;
import net.moznion.memai.memcached.protocol.response.type.DeleteResponseType;

import java.util.Optional;

public class TextDeleteResponseProtocol implements TextResponseProtocol<DeleteResponse> {
    public DeleteResponse parse(final String response) throws IllegalMemcachedErrorResponseProtocolException {
        final String trimmed = response.trim();

        switch (trimmed) {
            case "DELETED":
                return new DeleteResponse(DeleteResponseType.DELETED, Optional.empty());
            case "NOT_FOUND":
                return new DeleteResponse(DeleteResponseType.NOT_FOUND, Optional.empty());
            default:
                return new DeleteResponse(DeleteResponseType.ERROR,
                        Optional.of(new TextErrorResponseProtocol().parse(response)));
        }
    }
}
