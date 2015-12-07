package net.moznion.memai.memcached;

import net.moznion.memai.memcached.command.DecrementCommand;
import net.moznion.memai.memcached.command.GetCommand;
import net.moznion.memai.memcached.command.GetsCommand;
import net.moznion.memai.memcached.command.IncrementCommand;
import net.moznion.memai.memcached.command.SetCommand;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextDecrementProtocol;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextIncrementProtocol;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetProtocol;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetsProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextSetProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private int cursor;
    private final List<Worker> workers;
    private final int numberOfWorkers;
    private final InetSocketAddress address;

    public Client(final InetSocketAddress address, final int numberOfWorkers) throws IOException {
        this.address = address;
        this.workers = new ArrayList<>(numberOfWorkers);
        this.numberOfWorkers = numberOfWorkers;

        for (int i = 0; i < numberOfWorkers; i++) {
            final Worker worker = new Worker(address, new LinkedBlockingQueue<>());
            workers.add(worker);
            new Thread(worker).start();
        }
    }

    public SetCommand set(final String key, final String data) {
        balance();
        return new SetCommand(new TextSetProtocol(key, data), workers.get(cursor));
    }

    public GetCommand get(final String key) {
        balance();
        return new GetCommand(new TextGetProtocol(key), workers.get(cursor));
    }

    public GetCommand get(final String... keys) {
        balance();
        return new GetCommand(new TextGetProtocol(keys), workers.get(cursor));
    }

    public GetCommand get(final List<String> keys) {
        balance();
        return new GetCommand(new TextGetProtocol(keys), workers.get(cursor));
    }

    public GetsCommand gets(final String key) {
        balance();
        return new GetsCommand(new TextGetsProtocol(key), workers.get(cursor));
    }

    public GetsCommand gets(final String... keys) {
        balance();
        return new GetsCommand(new TextGetsProtocol(keys), workers.get(cursor));
    }

    public GetsCommand gets(final List<String> keys) {
        balance();
        return new GetsCommand(new TextGetsProtocol(keys), workers.get(cursor));
    }

    public IncrementCommand incr(final String key, final long value) {
        balance();
        return new IncrementCommand(new TextIncrementProtocol(key, value), workers.get(cursor));
    }

    public DecrementCommand decr(final String key, final long value) {
        balance();
        return new DecrementCommand(new TextDecrementProtocol(key, value), workers.get(cursor));
    }

    public void shutdown() {
        // TODO
    }

    private void balance() {
        cursor++;
        if (cursor % numberOfWorkers == 0) {
            cursor = 0;
        }
    }
}
