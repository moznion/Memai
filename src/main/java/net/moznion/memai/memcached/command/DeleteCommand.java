package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.DeleteResponse;
import net.moznion.memai.memcached.protocol.text.request.TextDeleteProtocol;
import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;

import java.util.concurrent.CompletableFuture;

public class DeleteCommand implements Command<DeleteResponse> {
    private final Worker worker;
    private final TextDeleteProtocol protocol;

    public DeleteCommand(final TextDeleteProtocol textDeleteProtocol, final Worker worker) {
        this.protocol = textDeleteProtocol;
        this.worker = worker;
    }

    public DeleteCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public DeleteCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }

    @Override
    public CompletableFuture<DeleteResponse> execute() {
        return worker.<TextRequestProtocol, DeleteResponse>appendJob(protocol);
    }
}
