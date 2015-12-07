package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.TextFlushAllProtocol;

import java.util.concurrent.CompletableFuture;

public class FlushAllCommand implements Command<TextFlushAllProtocol> {
    private final Worker worker;
    private final TextFlushAllProtocol protocol;

    public FlushAllCommand(final TextFlushAllProtocol textFlushAllProtocol, final Worker worker) {
        this.protocol = textFlushAllProtocol;
        this.worker = worker;
    }

    public FlushAllCommand duration(final int duration) {
        protocol.duration(duration);
        return this;
    }

    @Override
    public CompletableFuture<TextFlushAllProtocol> execute() {
        return worker.appendJob(protocol);
    }
}
