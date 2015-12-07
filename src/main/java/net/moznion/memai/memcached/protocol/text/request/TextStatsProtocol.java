package net.moznion.memai.memcached.protocol.text.request;

import lombok.Data;
import lombok.experimental.Accessors;
import net.moznion.memai.memcached.protocol.text.response.TextStatsResponseProtocol;

@Data
@Accessors(fluent = true)
public class TextStatsProtocol implements TextRequestProtocol<TextStatsResponseProtocol> {
    private String args;

    public TextStatsProtocol() {
        this("");
    }

    public TextStatsProtocol(final String args) {
        this.args = args;
    }

    @Override
    public byte[] build() {
        return ("stats " + args + "\r\n").getBytes();
    }

    @Override
    public TextStatsResponseProtocol getResponseProtocol() {
        return new TextStatsResponseProtocol();
    }
}
