package net.moznion.memai.memcached.protocol.response;

import java.util.Optional;

public interface Response<T> {
    boolean isSucceeded();

    T getResponseType();

    Optional<ErrorResponse> getErrorResponse();
}
