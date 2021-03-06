package net.moznion.memai.memcached.protocol.text.request.storage;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.text.response.TextStorageResponseProtocol;

@Data
@Accessors(fluent = true)
public class TextAppendProtocol implements TextStorageProtocol<TextAppendProtocol> {
    private String key;
    private String data;
    private int flags;
    private int exptime;
    private boolean noreply;

    public TextAppendProtocol(final String key, final String data) {
        this.key = key;
        this.data = data;
        this.flags = 0;
        this.exptime = 0;
        this.noreply = false;
    }

    @Override
    public String command() {
        return "append";
    }

    @Override
    public TextStorageResponseProtocol getResponseProtocol() {
        return new TextStorageResponseProtocol();
    }
}
