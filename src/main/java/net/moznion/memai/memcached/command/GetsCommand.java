package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.RetrievalResponse;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetsProtocol;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetsCommand implements Command<RetrievalResponse> {
    private final Worker worker;
    private final TextGetsProtocol protocol;

    public GetsCommand(final TextGetsProtocol textGetsProtocol, final Worker worker) {
        this.protocol = textGetsProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<RetrievalResponse> execute() {
        return worker.<RetrievalResponse>appendJob(protocol);
    }

    public GetsCommand setKeys(String... keys) {
        protocol.setKeys(keys);
        return this;
    }

    public GetsCommand setKeys(List<String> keys) {
        protocol.setKeys(keys);
        return this;
    }

    public GetsCommand setKey(String key) {
        protocol.setKey(key);
        return this;
    }

    public GetsCommand appendKeys(String... keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public GetsCommand appendKeys(List<String> keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public GetsCommand appendKey(String key) {
        protocol.appendKey(key);
        return this;
    }
}
