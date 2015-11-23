package net.moznion.memai.memcached;

import net.moznion.memai.memcached.protocol.Protocol;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class Worker implements Runnable {
    private final Socket socket;
    private final InetSocketAddress address;
    private final BlockingQueue<Protocol> queue;

    public Worker(final InetSocketAddress address, final BlockingQueue<Protocol> queue) throws IOException {
        this.queue = queue;
        this.socket = new Socket();
        this.address = address;
    }

    public <T extends Protocol> CompletableFuture<T> appendJob(T job) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        queue.add(job);
        return future;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Protocol job = queue.take();
                final byte[] built = job.build();

                if (!socket.isConnected()) {
                    socket.connect(address);
                }

                final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.write(built);
                out.flush();

                final DataInputStream in = new DataInputStream(socket.getInputStream());
                try (final BufferedReader buff = new BufferedReader(new InputStreamReader(in))) {
                    final String res = buff.readLine();
                }

                out.close();
                in.close();
            } catch (InterruptedException e) {
                // TODO
            } catch (Throwable e) {
                // TODO
            }
        }
    }
}
