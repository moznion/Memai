package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetsProtocol;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TextGetsCommand implements Command<TextGetsProtocol> {
    private final Worker worker;
    private final TextGetsProtocol protocol;

    public TextGetsCommand(final TextGetsProtocol textGetsProtocol, final Worker worker) {
        this.protocol = textGetsProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextGetsProtocol> execute() {
        return this.worker.appendJob(protocol);
    }

    public TextGetsCommand setKeys(String... keys) {
        protocol.setKeys(keys);
        return this;
    }

    public TextGetsCommand setKeys(List<String> keys) {
        protocol.setKeys(keys);
        return this;
    }

    public TextGetsCommand setKey(String key) {
        protocol.setKey(key);
        return this;
    }

    public TextGetsCommand appendKeys(String... keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public TextGetsCommand appendKeys(List<String> keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public TextGetsCommand appendKey(String key) {
        protocol.appendKey(key);
        return this;
    }
}
