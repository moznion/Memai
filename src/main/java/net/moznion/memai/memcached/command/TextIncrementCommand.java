package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextIncrementProtocol;

import java.util.concurrent.CompletableFuture;

public class TextIncrementCommand implements Command<TextIncrementProtocol> {
    private final Worker worker;
    private final TextIncrementProtocol protocol;

    public TextIncrementCommand(final TextIncrementProtocol textIncrementProtocol, final Worker worker) {
        this.protocol = textIncrementProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextIncrementProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public TextIncrementCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public TextIncrementCommand value(final long value) {
        protocol.value(value);
        return this;
    }

    public TextIncrementCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
