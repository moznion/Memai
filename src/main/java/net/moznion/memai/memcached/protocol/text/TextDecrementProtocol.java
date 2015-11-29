package net.moznion.memai.memcached.protocol.text;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TextDecrementProtocol implements TextIncrDecrProtocol<TextDecrementProtocol> {
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
}
