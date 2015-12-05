package net.moznion.memai.memcached.protocol.text.request.storage;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextSetProtocol implements TextStorageProtocol<TextSetProtocol> {
    private String key;
    private String data;
    private int flags;
    private int exptime;
    private boolean noreply;

    public TextSetProtocol(final String key, final String data) {
        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
    }

    @Override
    public String command() {
        return "set";
    }
}
