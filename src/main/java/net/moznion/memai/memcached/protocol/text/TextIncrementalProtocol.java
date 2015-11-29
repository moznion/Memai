package net.moznion.memai.memcached.protocol.text;

import net.moznion.memai.memcached.protocol.Protocol;

public interface TextIncrementalProtocol<T> extends Protocol {
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

        return sb.toString().getBytes();
    }
}
