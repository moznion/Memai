package net.moznion.memai.memcached.protocol.response;

import java.util.Optional;

public interface Response<T> {
    T getResponseType();

    Optional<ErrorResponse> getErrorResponse();
}
