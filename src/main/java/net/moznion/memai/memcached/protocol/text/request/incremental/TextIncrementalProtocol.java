package net.moznion.memai.memcached.protocol.text.request.incremental;

import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextIncrementalResponseProtocol;

public interface TextIncrementalProtocol<T> extends TextRequestProtocol<TextIncrementalResponseProtocol> {
    T key(String key);

    T value(long value);

    T noreply(boolean noreply);

    String key();

    long value();

    boolean noreply();

    String command();

    @Override
    default byte[] build() {
        final StringBuilder sb = new StringBuilder(command())
                .append(" ").append(key())
                .append(" ").append(value());

        if (noreply()) {
            sb.append(" noreply");
        }

        sb.append("\r\n");

        return sb.toString().getBytes();
    }
}
