package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedErrorResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.ErrorResponse;
import net.moznion.memai.memcached.protocol.response.type.ErrorResponseType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextErrorResponseProtocol {
    public ErrorResponse parse(final String response) throws IllegalMemcachedErrorResponseProtocolException {
        final List<String> splitted = Arrays.asList(response.trim().split(" "));

        if (splitted.size() == 1) {
            if (splitted.get(0).equals("ERROR")) {
                return new ErrorResponse(ErrorResponseType.NONEXISTENT_COMMAND, "");
            }
        } else if (splitted.size() >= 2) {
            final String type = splitted.remove(0);
            if (type.equals("CLIENT_ERROR")) {
                return new ErrorResponse(ErrorResponseType.CLIENT, buildErrorMessage(splitted));
            } else if (type.equals("SERVER_ERROR")) {
                return new ErrorResponse(ErrorResponseType.SERVER, buildErrorMessage(splitted));
            }
        }

        throw new IllegalMemcachedErrorResponseProtocolException();
    }

    private static String buildErrorMessage(final List<String> terms) {
        return terms.stream()
                .collect(Collectors.joining(" "));
    }
}
