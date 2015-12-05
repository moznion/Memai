package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedRetrievalResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.RetrievalResponse;
import net.moznion.memai.memcached.protocol.response.ValueResponse;
import net.moznion.memai.memcached.protocol.response.type.RetrievalResponseType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

public class TextRetrievalResponseProtocol implements TextResponseProtocol<RetrievalResponse> {
    public RetrievalResponse parse(final String response) throws IllegalMemcachedRetrievalResponseProtocolException {
        final List<String> lines = Arrays.asList(response.split("\r\n"));

        final Map<String, ValueResponse> values = new HashMap<>();

        final int size = lines.size();
        for (int i = 0; i < size; i++) {
            String line = lines.get(i);
            if (line.equals("END")) {
                break;
            }

            final List<String> splited = Arrays.asList(line.split(" "));
            final String key;
            final int flags;
            final long bytes;
            final OptionalLong casUnique;
            switch (splited.size()) {
                case 4:
                    key = splited.get(1);
                    flags = Integer.parseInt(splited.get(2), 10);
                    bytes = Long.parseLong(splited.get(3), 10);
                    casUnique = OptionalLong.empty();
                    break;
                case 5:
                    key = splited.get(1);
                    flags = Integer.parseInt(splited.get(2), 10);
                    bytes = Long.parseLong(splited.get(3), 10);
                    casUnique = OptionalLong.of(Long.parseLong(splited.get(4), 10));
                    break;
                default:
                    throw new IllegalMemcachedRetrievalResponseProtocolException();
            }

            line = lines.get(++i);

            values.put(key, new ValueResponse(flags, bytes, casUnique, line));
        }

        return new RetrievalResponse(true, RetrievalResponseType.RETRIEVED, Optional.empty(), values);
    }
}
