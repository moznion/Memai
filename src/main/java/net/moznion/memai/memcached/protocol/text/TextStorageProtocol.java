package net.moznion.memai.memcached.protocol.text;

import net.moznion.memai.memcached.protocol.Protocol;

public interface TextStorageProtocol<T> extends Protocol {
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
                .append(" ").append(data().length());

        if (noreply()) {
            sb.append(" noreply");
        }

        sb.append("\r\n")
                .append(data())
                .append("\r\n");

        return sb.toString().getBytes();
    }
}
