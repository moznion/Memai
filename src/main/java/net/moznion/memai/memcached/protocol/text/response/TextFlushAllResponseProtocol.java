package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.response.FlushAllResponse;
import net.moznion.memai.memcached.protocol.response.type.FlushAllResponseType;

import java.util.Optional;

public class TextFlushAllResponseProtocol implements TextResponseProtocol<FlushAllResponse> {
    public FlushAllResponse parse(final String response) {
        if (!response.trim().equals("OK")) {
            return new FlushAllResponse(FlushAllResponseType.FAILED, Optional.empty());
        }

        return new FlushAllResponse(FlushAllResponseType.OK, Optional.empty());
    }
}
