package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextIncrementProtocol implements TextIncrDecrProtocol<TextIncrementProtocol> {
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
}
