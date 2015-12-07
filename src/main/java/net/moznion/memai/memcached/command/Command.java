package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.protocol.response.Response;

import java.util.concurrent.CompletableFuture;

public interface Command<T extends Response> {
    CompletableFuture<T> execute();
}
