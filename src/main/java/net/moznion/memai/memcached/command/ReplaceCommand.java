package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.StorageResponse;
import net.moznion.memai.memcached.protocol.text.request.storage.TextReplaceProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ReplaceCommand implements Command<StorageResponse> {
    private final Worker worker;
    private final TextReplaceProtocol protocol;

    public ReplaceCommand(final TextReplaceProtocol textReplaceProtocol, final Worker worker) {
        this.protocol = textReplaceProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<StorageResponse> execute() {
        return worker.<StorageResponse>appendJob(protocol);
    }

    public ReplaceCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public ReplaceCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public ReplaceCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public ReplaceCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public ReplaceCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
