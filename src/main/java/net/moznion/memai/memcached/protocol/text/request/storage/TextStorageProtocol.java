package net.moznion.memai.memcached.protocol.text.request.storage;

import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextStorageResponseProtocol;

import java.nio.charset.StandardCharsets;

public interface TextStorageProtocol<T> extends TextRequestProtocol<TextStorageResponseProtocol> {
    T key(String key);

    T data(String data);

    T flags(int flags);

    T exptime(int exptime);

    T noreply(boolean noreply);

    String key();

    String data();

    int flags();

    int exptime();

    boolean noreply();

    String command();

    @Override
    default byte[] build() {
        final StringBuilder sb = new StringBuilder(command())
                .append(" ").append(key())
                .append(" ").append(flags())
                .append(" ").append(exptime())
                .append(" ").append(data().getBytes(StandardCharsets.UTF_8).length);

        if (noreply()) {
            sb.append(" noreply");
        }

        sb.append("\r\n")
                .append(data())
                .append("\r\n");

        return sb.toString().getBytes();
    }
}
