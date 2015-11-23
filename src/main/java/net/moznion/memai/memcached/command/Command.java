package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.protocol.Protocol;

import java.util.concurrent.CompletableFuture;

public interface Command<T extends Protocol> {
    CompletableFuture<T> execute();
}
