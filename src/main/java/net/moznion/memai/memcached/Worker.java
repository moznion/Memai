package net.moznion.memai.memcached;

import lombok.AllArgsConstructor;
import net.moznion.memai.memcached.protocol.Protocol;
import net.moznion.memai.memcached.protocol.response.Response;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;

public class Worker implements Runnable {
    private final Socket socket;
    private final InetSocketAddress address;
    private final BlockingQueue<JobWithFuture<? extends Protocol>> queue;

    public Worker(final InetSocketAddress address) throws IOException {
        this.queue = new LinkedBlockingDeque<>();
        this.socket = new Socket();
        this.address = address;
    }

    public <T extends Protocol> CompletableFuture<? extends Response> appendJob(T job) {
        final CompletableFuture<? extends Response> future = new CompletableFuture<>();
        final JobWithFuture<T> jobWithFuture = new JobWithFuture<>(job, future);
        queue.add(jobWithFuture);
        return future;
    }

    @Override
    public void run() {
        while (true) {
            JobWithFuture<? extends Protocol> jobWithFuture = null;
            try {
                jobWithFuture = queue.take();
                final Protocol job = jobWithFuture.job;
                final byte[] built = job.build();

                if (!socket.isConnected()) {
                    socket.connect(address);
                }

                final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.write(built);
                out.flush();

                // TODO noreply support
                final DataInputStream in = new DataInputStream(socket.getInputStream());
                try (final BufferedReader buff = new BufferedReader(new InputStreamReader(in))) {
                    final String res = buff.readLine();
                }

                out.close();
                in.close();
            } catch (Throwable e) {
                // TODO
            } finally {
                // TODO
            }
        }
    }

    @AllArgsConstructor
    private static class JobWithFuture<T extends Protocol> {
        private T job;
        private CompletableFuture<? extends Response> future;
    }
}
