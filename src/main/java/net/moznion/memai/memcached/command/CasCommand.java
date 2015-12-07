package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.storage.TextCASProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class CASCommand implements Command<TextCASProtocol> {
    private final Worker worker;
    private final TextCASProtocol protocol;

    public CASCommand(final TextCASProtocol textReplaceProtocol, final Worker worker) {
        this.protocol = textReplaceProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextCASProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public CASCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public CASCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public CASCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public CASCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public CASCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }

    public CASCommand casUnique(final long casUnique) {
        protocol.casUnique(casUnique);
        return this;
    }
}
