package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextDecrementProtocol;

import java.util.concurrent.CompletableFuture;

public class TextDecrementCommand implements Command<TextDecrementProtocol> {
    private final Worker worker;
    private final TextDecrementProtocol protocol;

    public TextDecrementCommand(final TextDecrementProtocol textDecrementProtocol, final Worker worker) {
        this.protocol = textDecrementProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextDecrementProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public TextDecrementCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public TextDecrementCommand value(final long value) {
        protocol.value(value);
        return this;
    }

    public TextDecrementCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
