package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.storage.TextSetProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class SetCommand implements Command<TextSetProtocol> {
    private final Worker worker;
    private final TextSetProtocol protocol;

    public SetCommand(final TextSetProtocol textSetProtocol, final Worker worker) {
        this.protocol = textSetProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextSetProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public SetCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public SetCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public SetCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public SetCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public SetCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
