package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextAddProtocol implements TextStorageProtocol<TextAddProtocol> {
    private String key;
    private String data;
    private int flags;
    private int exptime;
    private boolean noreply;

    public TextAddProtocol(final String key, final String data) {
        if (data.length() == 0) {
            throw new IllegalArgumentException("TODO");
        }

        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
    }

    @Override
    public String command() {
        return "add";
    }
}
