package net.moznion.memai.memcached.protocol.text;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.StorageProtocol;

@Setter
@Accessors(fluent = true)
public class TextSetProtocol implements StorageProtocol<TextSetProtocol> {
    private String key;
    private byte[] data;
    private int flags;
    private int exptime;
    private boolean noreply;

    public TextSetProtocol(final String key, final byte[] data) {
        if (data.length == 0) {
            throw new IllegalArgumentException("TODO");
        }

        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder("set ").append(key)
                .append(" ").append(flags)
                .append(" ").append(exptime)
                .append(" ").append(data.length);

        if (noreply) {
            sb.append(" ").append("noreply");
        }

        sb.append("\r\n")
                .append(new String(data)) // TODO ???
                .append("\r\n");

        return sb.toString().getBytes();
    }
}
