package net.moznion.memai.memcached.protocol.text;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.Protocol;

public class TextDeleteProtocol implements Protocol {
    private String key;

    @Setter
    @Accessors(fluent = true)
    private boolean noreply;

    public TextDeleteProtocol(String key) {
        this.key = key;
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder("delete ")
                .append(key);

        if (noreply) {
            sb.append(" ").append("noreply");
        }

        sb.append("\r\n");

        return sb.toString().getBytes();
    }
}
