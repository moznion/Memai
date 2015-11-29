package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.Protocol;

@Data
@Accessors(fluent = true)
public class TextTouchProtocol implements Protocol {
    private String key;
    private int exptime;
    private boolean noreply;

    public TextTouchProtocol(String key, int exptime) {
        this.key = key;
        this.exptime = exptime;

        this.noreply = false;
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder("touch ")
                .append(key)
                .append(" ").append(exptime);

        if (noreply) {
            sb.append(" noreply");
        }

        sb.append("\r\n");

        return sb.toString().getBytes();
    }
}
