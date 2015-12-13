package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.TouchResponse;
import net.moznion.memai.memcached.protocol.text.request.TextTouchProtocol;

import java.util.concurrent.CompletableFuture;

public class TouchCommand implements Command<TouchResponse> {
    private final Worker worker;
    private final TextTouchProtocol protocol;

    public TouchCommand(final TextTouchProtocol textTouchProtocol, final Worker worker) {
        this.protocol = textTouchProtocol;
        this.worker = worker;
    }

    public TouchCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public TouchCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public TouchCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }

    @Override
    public CompletableFuture<TouchResponse> execute() {
        return worker.<TouchResponse>appendJob(protocol);
    }
}
