package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetProtocol;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TextGetCommand implements Command<TextGetProtocol> {
    private final Worker worker;
    private final TextGetProtocol protocol;

    public TextGetCommand(final TextGetProtocol textGetProtocol, final Worker worker) {
        this.protocol = textGetProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextGetProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public TextGetCommand setKeys(String... keys) {
        protocol.setKeys(keys);
        return this;
    }

    public TextGetCommand setKeys(List<String> keys) {
        protocol.setKeys(keys);
        return this;
    }

    public TextGetCommand setKey(String key) {
        protocol.setKey(key);
        return this;
    }

    public TextGetCommand appendKeys(String... keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public TextGetCommand appendKeys(List<String> keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public TextGetCommand appendKey(String key) {
        protocol.appendKey(key);
        return this;
    }
}