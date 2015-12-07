package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.TextStatsProtocol;

import java.util.concurrent.CompletableFuture;

public class StatsCommand implements Command<TextStatsProtocol> {
    private final Worker worker;
    private final TextStatsProtocol protocol;

    public StatsCommand(final TextStatsProtocol textStatsProtocol, final Worker worker) {
        this.protocol = textStatsProtocol;
        this.worker = worker;
    }

    public StatsCommand args(final String args) {
        protocol.args(args);
        return this;
    }

    @Override
    public CompletableFuture<TextStatsProtocol> execute() {
        return worker.appendJob(protocol);
    }
}
