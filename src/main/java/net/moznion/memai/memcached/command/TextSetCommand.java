package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.storage.TextSetProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class TextSetCommand implements Command<TextSetProtocol> {
    private final Worker worker;
    private final TextSetProtocol protocol;

    public TextSetCommand(final TextSetProtocol textSetProtocol, final Worker worker) {
        this.protocol = textSetProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextSetProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public TextSetCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public TextSetCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public TextSetCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public TextSetCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public TextSetCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
