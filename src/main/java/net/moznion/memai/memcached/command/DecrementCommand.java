package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.IncrementalResponse;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextDecrementProtocol;

import java.util.concurrent.CompletableFuture;

public class DecrementCommand implements Command<IncrementalResponse> {
    private final Worker worker;
    private final TextDecrementProtocol protocol;

    public DecrementCommand(final TextDecrementProtocol textDecrementProtocol, final Worker worker) {
        this.protocol = textDecrementProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<IncrementalResponse> execute() {
        return worker.appendJob(protocol);
    }

    public DecrementCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public DecrementCommand value(final long value) {
        protocol.value(value);
        return this;
    }

    public DecrementCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
