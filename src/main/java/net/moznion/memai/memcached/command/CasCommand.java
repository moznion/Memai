package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Worker;
import net.moznion.memai.memcached.protocol.text.request.storage.TextCasProtocol;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class CasCommand implements Command<TextCasProtocol> {
    private final Worker worker;
    private final TextCasProtocol protocol;

    public CasCommand(final TextCasProtocol textReplaceProtocol, final Worker worker) {
        this.protocol = textReplaceProtocol;
        this.worker = worker;
    }

    @Override
    public CompletableFuture<TextCasProtocol> execute() {
        return worker.appendJob(protocol);
    }

    public CasCommand key(final String key) {
        protocol.key(key);
        return this;
    }

    public CasCommand data(final byte[] data) {
        protocol.data(Arrays.toString(data));
        return this;
    }

    public CasCommand flags(final int flags) {
        protocol.flags(flags);
        return this;
    }

    public CasCommand exptime(final int exptime) {
        protocol.exptime(exptime);
        return this;
    }

    public CasCommand noreply(final boolean noreply) {
        protocol.noreply(noreply);
        return this;
    }

    public CasCommand casUnique(final long casUnique) {
        protocol.casUnique(casUnique);
        return this;
    }
}
