package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.TextSetProtocol;

import java.util.concurrent.CompletableFuture;

public class TextSetCommand implements Command<TextSetProtocol> {
    private final Worker worker;
    private final TextSetProtocol textSetProtocol;

    public TextSetCommand(final TextSetProtocol textSetProtocol, final Worker worker) {
        this.textSetProtocol = textSetProtocol;
        this.worker = worker;
    }

    public CompletableFuture<TextSetProtocol> execute() {
        return worker.appendJob(textSetProtocol);
    }

    public TextSetCommand key(final String key) {
        textSetProtocol.key(key);
        return this;
    }

    public TextSetCommand data(final byte[] data) {
        textSetProtocol.data(data);
        return this;
    }

    public TextSetCommand flags(final int flags) {
        textSetProtocol.flags(flags);
        return this;
    }

    public TextSetCommand exptime(final int exptime) {
        textSetProtocol.exptime(exptime);
        return this;
    }

    public TextSetCommand noreply(final boolean noreply) {
        textSetProtocol.noreply(noreply);
        return this;
    }
}
