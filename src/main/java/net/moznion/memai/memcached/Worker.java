package net.moznion.memai.memcached;

import lombok.AllArgsConstructor;
import net.moznion.memai.memcached.protocol.response.Response;
import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextResponseProtocol;

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
    private final BlockingQueue<JobWithFuture<? extends TextRequestProtocol<TextResponseProtocol>, ? extends Response>> queue;

    public Worker(final InetSocketAddress address) throws IOException {
        this.queue = new LinkedBlockingDeque<>();
        this.socket = new Socket();
        this.address = address;
    }

    public <T extends TextRequestProtocol<TextResponseProtocol>, U extends Response> CompletableFuture<U> appendJob(T job) {
        final CompletableFuture<U> future = new CompletableFuture<>();
        final JobWithFuture<T, U> jobWithFuture = new JobWithFuture<>(job, future);
        queue.add(jobWithFuture);
        return future;
    }

    @Override
    public void run() {
        while (true) {
            JobWithFuture<? extends TextRequestProtocol<TextResponseProtocol>, ? extends Response> jobWithFuture = null;
            try {
                jobWithFuture = queue.take();
                final TextRequestProtocol<TextResponseProtocol> job = jobWithFuture.job;
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
                    final String result = buff.readLine();
                    jobWithFuture.future.complete(job.getResponseProtocol().parse(result));
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
    private static class JobWithFuture<T extends TextRequestProtocol<TextResponseProtocol>, U extends Response> {
        private T job;
        private CompletableFuture<Response> future;
    }
}
