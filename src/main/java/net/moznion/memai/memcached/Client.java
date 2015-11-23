package net.moznion.memai.memcached;

import net.moznion.memai.memcached.command.TextSetCommand;
import net.moznion.memai.memcached.protocol.text.TextSetProtocol;

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

    public static void main(String... args) throws IOException {
        final Client client = new Client(new InetSocketAddress("localhost", 11211), 1);
        client.set("fuga", "un".getBytes())
                .exptime(100)
                .execute();
    }

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

    public TextSetCommand set(final String key, final byte[] data) {
        balance();
        return new TextSetCommand(new TextSetProtocol(key, data), workers.get(cursor));
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
