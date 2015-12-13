package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.RetrievalResponse;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetProtocol;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextRetrievalProtocol;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetCommand implements Command<RetrievalResponse> {
    private final Worker worker;
    private final TextGetProtocol protocol;

    public GetCommand(final TextGetProtocol textGetProtocol, final Worker worker) {
        this.protocol = textGetProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<RetrievalResponse> execute() {
        return worker.<TextRetrievalProtocol, RetrievalResponse>appendJob(protocol);
    }

    public GetCommand setKeys(String... keys) {
        protocol.setKeys(keys);
        return this;
    }

    public GetCommand setKeys(List<String> keys) {
        protocol.setKeys(keys);
        return this;
    }

    public GetCommand setKey(String key) {
        protocol.setKey(key);
        return this;
    }

    public GetCommand appendKeys(String... keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public GetCommand appendKeys(List<String> keys) {
        protocol.appendKeys(keys);
        return this;
    }

    public GetCommand appendKey(String key) {
        protocol.appendKey(key);
        return this;
    }
}