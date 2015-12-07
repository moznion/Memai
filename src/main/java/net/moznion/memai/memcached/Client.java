package net.moznion.memai.memcached;

import net.moznion.memai.memcached.command.TextGetCommand;
import net.moznion.memai.memcached.command.TextGetsCommand;
import net.moznion.memai.memcached.command.TextSetCommand;
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

    public TextSetCommand set(final String key, final String data) {
        balance();
        return new TextSetCommand(new TextSetProtocol(key, data), workers.get(cursor));
    }

    public TextGetCommand get(final String key) {
        balance();
        return new TextGetCommand(new TextGetProtocol(key), workers.get(cursor));
    }

    public TextGetCommand get(final String... keys) {
        balance();
        return new TextGetCommand(new TextGetProtocol(keys), workers.get(cursor));
    }

    public TextGetCommand get(final List<String> keys) {
        balance();
        return new TextGetCommand(new TextGetProtocol(keys), workers.get(cursor));
    }

    public TextGetsCommand gets(final String key) {
        balance();
        return new TextGetsCommand(new TextGetsProtocol(key), workers.get(cursor));
    }

    public TextGetsCommand gets(final String... keys) {
        balance();
        return new TextGetsCommand(new TextGetsProtocol(keys), workers.get(cursor));
    }

    public TextGetsCommand gets(final List<String> keys) {
        balance();
        return new TextGetsCommand(new TextGetsProtocol(keys), workers.get(cursor));
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
