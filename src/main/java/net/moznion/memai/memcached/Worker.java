package net.moznion.memai.memcached;

import lombok.AllArgsConstructor;
import net.moznion.memai.memcached.protocol.response.Response;
import net.moznion.memai.memcached.protocol.text.request.TextRequestProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextDeleteResponseProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextIncrementalResponseProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextResponseProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextStorageResponseProtocol;
import net.moznion.memai.memcached.protocol.text.response.TextTouchResponseProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Pattern;

public class Worker implements Runnable {
    private static final Pattern STANDARD_TERMINATOR_PATTERN = Pattern.compile("^(?:OK|END|ERROR)\r\n$");
    private static final Pattern ONE_LINER_TERMINATOR_PATTERN = Pattern.compile("\r\n$");

    private final InetSocketAddress address;
    private final BlockingQueue<JobWithFuture> queue;

    public Worker(final InetSocketAddress address) throws IOException {
        this.queue = new LinkedBlockingDeque<>();
        this.address = address;
    }

    public <U extends Response> CompletableFuture<U> appendJob(TextRequestProtocol job) {
        final CompletableFuture<U> future = new CompletableFuture<>();

        final JobWithFuture<U> jobWithFuture = new JobWithFuture<>(job, future);
        queue.add(jobWithFuture);
        return future;
    }

    @Override
    public void run() {
        while (true) {
            JobWithFuture jobWithFuture;
            try {
                jobWithFuture = queue.take();
                final TextRequestProtocol job = jobWithFuture.job;
                final byte[] built = job.build();

                final TextResponseProtocol responseProtocol = job.getResponseProtocol();

                Pattern terminatorPattern = STANDARD_TERMINATOR_PATTERN;
                if (responseProtocol instanceof TextStorageResponseProtocol ||
                        responseProtocol instanceof TextDeleteResponseProtocol ||
                        responseProtocol instanceof TextIncrementalResponseProtocol ||
                        responseProtocol instanceof TextTouchResponseProtocol) {
                    terminatorPattern = ONE_LINER_TERMINATOR_PATTERN;
                }

                // TODO
                final Socket socket = new Socket();
                socket.connect(address);

                try (final OutputStream out = socket.getOutputStream()) {
                    out.write(built);
                    out.flush();

                    try (final InputStream in = socket.getInputStream()) {
                        // TODO noreply support
                        final String result = readResult(in, terminatorPattern);

                        jobWithFuture.future.complete(responseProtocol.parse(result));
                    }
                }
            } catch (Throwable e) {
                // TODO
            } finally {
                // TODO
            }
        }
    }

    private String readResult(InputStream in, Pattern terminatorPattern) throws IOException {
        StringBuilder sbForLine = new StringBuilder();
        final StringBuilder sbForResult = new StringBuilder();

        boolean isBeforeCR = false;

        try (final BufferedReader buff = new BufferedReader(new InputStreamReader(in))) {
            for (int c = buff.read(); c >= 0; c = buff.read()) {
                sbForLine.append((char) c);
                if (c == 13) {
                    isBeforeCR = true;
                    continue;
                }

                if (c == 10) {
                    if (isBeforeCR) {
                        final String line = sbForLine.toString();
                        sbForResult.append(line);

                        if (terminatorPattern.matcher(line).find()) {
                            break;
                        }
                        sbForLine = new StringBuilder();
                    }
                }
                isBeforeCR = false;
            }
        }

        return sbForResult.toString();
    }

    @AllArgsConstructor
    private static class JobWithFuture<U extends Response> {
        private TextRequestProtocol job;
        private CompletableFuture<U> future;
    }
}
