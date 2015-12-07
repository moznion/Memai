package net.moznion.memai.memcached.protocol.text.request.incremental;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.text.response.TextIncrementalResponseProtocol;

@Data
@Accessors(fluent = true)
public class TextDecrementProtocol implements TextIncrementalProtocol<TextDecrementProtocol> {
    String key;
    long value;
    boolean noreply;

    public TextDecrementProtocol(String key, long value) {
        this.key = key;
        this.value = value;

        this.noreply = false;
    }

    public String command() {
        return "decr";
    }

    @Override
    public TextIncrementalResponseProtocol getResponseProtocol() {
        return new TextIncrementalResponseProtocol();
    }
}
