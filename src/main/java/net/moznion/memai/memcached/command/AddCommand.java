package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.storage.TextAddProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class AddCommand implements Command<TextAddProtocol> {
    private final Worker worker;
    private final TextAddProtocol protocol;

    public AddCommand(final TextAddProtocol textAddProtocol, final Worker worker) {
        this.protocol = textAddProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextAddProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public AddCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public AddCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public AddCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public AddCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public AddCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
