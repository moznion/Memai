package net.moznion.memai.memcached;

import net.moznion.memai.memcached.command.AddCommand;
import net.moznion.memai.memcached.command.AppendCommand;
import net.moznion.memai.memcached.command.CasCommand;
import net.moznion.memai.memcached.command.DecrementCommand;
import net.moznion.memai.memcached.command.GetCommand;
import net.moznion.memai.memcached.command.GetsCommand;
import net.moznion.memai.memcached.command.IncrementCommand;
import net.moznion.memai.memcached.command.PrependCommand;
import net.moznion.memai.memcached.command.ReplaceCommand;
import net.moznion.memai.memcached.command.SetCommand;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextDecrementProtocol;
import net.moznion.memai.memcached.protocol.text.request.incremental.TextIncrementProtocol;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetProtocol;
import net.moznion.memai.memcached.protocol.text.request.retrieval.TextGetsProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextAddProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextAppendProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextCasProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextPrependProtocol;
import net.moznion.memai.memcached.protocol.text.request.storage.TextReplaceProtocol;
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

    public AddCommand add(final String key, final String data) {
        balance();
        return new AddCommand(new TextAddProtocol(key, data), workers.get(cursor));
    }

    public AppendCommand append(final String key, final String data) {
        balance();
        return new AppendCommand(new TextAppendProtocol(key, data), workers.get(cursor));
    }

    public CasCommand cas(final String key, final String data) {
        balance();
        return new CasCommand(new TextCasProtocol(key, data), workers.get(cursor));
    }

    public PrependCommand prepend(final String key, final String data) {
        balance();
        return new PrependCommand(new TextPrependProtocol(key, data), workers.get(cursor));
    }

    public ReplaceCommand replace(final String key, final String data) {
        balance();
        return new ReplaceCommand(new TextReplaceProtocol(key, data), workers.get(cursor));
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
