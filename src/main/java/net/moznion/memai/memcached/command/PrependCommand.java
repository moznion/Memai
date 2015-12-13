package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.StorageResponse;
import net.moznion.memai.memcached.protocol.text.request.storage.TextPrependProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class PrependCommand implements Command<StorageResponse> {
    private final Worker worker;
    private final TextPrependProtocol protocol;

    public PrependCommand(final TextPrependProtocol textPrependProtocol, final Worker worker) {
        this.protocol = textPrependProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<StorageResponse> execute() {
        return worker.<StorageResponse>appendJob(protocol);
    }

    public PrependCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public PrependCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public PrependCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public PrependCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public PrependCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
