package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.Protocol;

@Data
@Accessors(fluent = true)
public class TextDeleteProtocol implements Protocol {
    private String key;
    private boolean noreply;

    public TextDeleteProtocol(String key) {
        this.key = key;
        this.noreply = false;
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder("delete ")
                .append(key);

        if (noreply) {
            sb.append(" noreply");
        }

        sb.append("\r\n");

        return sb.toString().getBytes();
    }
}
