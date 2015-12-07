package net.moznion.memai.memcached.protocol.text.request.incremental;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.text.response.TextIncrementalResponseProtocol;

@Data
@Accessors(fluent = true)
public class TextIncrementProtocol implements TextIncrementalProtocol<TextIncrementProtocol> {
    String key;
    long value;
    boolean noreply;

    public TextIncrementProtocol(String key, long value) {
        this.key = key;
        this.value = value;
    }

    public String command() {
        return "incr";
    }

    @Override
    public TextIncrementalResponseProtocol getResponseProtocol() {
        return new TextIncrementalResponseProtocol();
    }
}
