package net.moznion.memai.memcached.protocol.text.request;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.Protocol;

@Data
@Accessors(fluent = true)
public class TextFlushAllProtocol implements Protocol {
    private Integer duration;

    public TextFlushAllProtocol() {
        duration = null;
    }

    public TextFlushAllProtocol(int duration) {
        this.duration = duration;
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder("flush_all ");

        if (duration != null) {
            sb.append(duration);
        }

        return sb.append("\r\n")
                .toString()
                .getBytes();
    }
}
