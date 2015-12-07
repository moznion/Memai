package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextIncrementProtocol;

import java.util.concurrent.CompletableFuture;

public class IncrementCommand implements Command<TextIncrementProtocol> {
    private final Worker worker;
    private final TextIncrementProtocol protocol;

    public IncrementCommand(final TextIncrementProtocol textIncrementProtocol, final Worker worker) {
        this.protocol = textIncrementProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextIncrementProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public IncrementCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public IncrementCommand value(final long value) {
        protocol.value(value);
        return this;
    }

    public IncrementCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
