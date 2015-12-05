package net.moznion.memai.memcached.protocol.text.request.storage;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextCasProtocol implements TextStorageProtocol<TextCasProtocol> {
    private String key;
    private String data;
    private int flags;
    private int exptime;
    private boolean noreply;
    private long casUnique;

    public TextCasProtocol(final String key, final String data) {
        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
        this.casUnique = 0;
    }

    @Override
    public String command() {
        return "cas";
    }

    @Override
    public byte[] build() {
        final StringBuilder sb = new StringBuilder(command())
                .append(" ").append(key())
                .append(" ").append(flags())
                .append(" ").append(exptime())
                .append(" ").append(data().length())
                .append(" ").append(casUnique());

        if (noreply()) {
            sb.append(" noreply");
        }

        sb.append("\r\n")
                .append(data())
                .append("\r\n");

        return sb.toString().getBytes();
    }
}
