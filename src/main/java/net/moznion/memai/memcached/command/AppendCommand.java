package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.response.StorageResponse;
import net.moznion.memai.memcached.protocol.text.request.storage.TextAppendProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextStorageProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class AppendCommand implements Command<StorageResponse> {
    private final Worker worker;
    private final TextAppendProtocol protocol;

    public AppendCommand(final TextAppendProtocol textAppendProtocol, final Worker worker) {
        this.protocol = textAppendProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<StorageResponse> execute() {
        return worker.<TextStorageProtocol, StorageResponse>appendJob(protocol);
    }

    public AppendCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public AppendCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public AppendCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public AppendCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public AppendCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }
}
