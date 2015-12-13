package net.moznion.memai.memcached.protocol.text.request.retrieval;

import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextRetrievalResponseProtocol;

import java.util.List;
import java.util.stream.Collectors;

public interface TextRetrievalProtocol<T> extends TextRequestProtocol<TextRetrievalResponseProtocol> {
    T setKeys(String... keys);

    T setKeys(List<String> keys);

    T setKey(String key);

    T appendKeys(String... keys);

    T appendKeys(List<String> keys);

    T appendKey(String key);

    List<String> getKeys();

    String command();

    @Override
    default byte[] build() {
        return (command() + " " + getKeys().stream().collect(Collectors.joining(" ")) + "\r\n").getBytes();
    }
}
