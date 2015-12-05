package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.StorageResponse;
import net.moznion.memai.memcached.protocol.response.type.StorageResponseType;

import java.util.Optional;

public class TextStorageResponseProtocol implements TextResponseProtocol<StorageResponse> {
    public StorageResponse parse(final String response) throws IllegalMemcachedErrorResponseProtocolException {
        final String trimmed = response.trim();

        switch (trimmed) {
            case "STORED":
                return new StorageResponse(StorageResponseType.STORED, Optional.empty());
            case "NOT_STORED":
                return new StorageResponse(StorageResponseType.NOT_STORED, Optional.empty());
            case "EXISTS":
                return new StorageResponse(StorageResponseType.EXISTS, Optional.empty());
            case "NOT_FOUND":
                return new StorageResponse(StorageResponseType.NOT_FOUND, Optional.empty());
            default:
                return new StorageResponse(StorageResponseType.ERROR,
                        Optional.of(new TextErrorResponseProtocol().parse(response)));
        }
    }
}
