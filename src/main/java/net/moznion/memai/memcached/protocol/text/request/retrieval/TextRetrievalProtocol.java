package net.moznion.memai.memcached.protocol.text.request.retrieval;

import net.moznion.memai.memcached.protocol.Protocol;

import java.util.List;
import java.util.stream.Collectors;

public interface TextRetrievalProtocol<T> extends Protocol {
    T setKeys(String... keys);

    T setKeys(List<String> keys);

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
