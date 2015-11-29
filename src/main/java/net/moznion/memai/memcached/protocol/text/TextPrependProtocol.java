package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextPrependProtocol implements TextStorageProtocol<TextPrependProtocol> {
    private String key;
    private String data;
    private int flags;
    private int exptime;
    private boolean noreply;

    public TextPrependProtocol(final String key, final String data) {
        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
    }

    @Override
    public String command() {
        return "prepend";
    }
}
