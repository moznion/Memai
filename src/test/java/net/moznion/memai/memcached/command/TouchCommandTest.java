package net.moznion.memai.memcached.command;

import net.moznion.memai.TestBase;
import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.TouchResponse;
import net.moznion.memai.memcached.protocol.response.type.TouchResponseType;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class TouchCommandTest extends TestBase {
    @Test
    public void testForTouchCommandWithExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        client.set("test-key", "blahblah").execute().join();

        assertThat(client.get("test-key").execute().join().getValues().get("test-key")).isNotNull();

        final CompletableFuture<TouchResponse> futureOfTouch = client.touch("test-key", 1).execute();
        final TouchResponse response = futureOfTouch.join();
        assertThat(response.getResponseType()).isEqualTo(TouchResponseType.TOUCHED);
        assertThat(response.getErrorResponse()).isEmpty();

        Thread.sleep(2000);

        assertThat(client.get("test-key").execute().join().getValues().get("test-key")).isNull();
    }

    @Test
    public void testForTouchCommandWithNotExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<TouchResponse> futureOfTouch = client.touch("test-key", 1).execute();
        final TouchResponse response = futureOfTouch.join();
        assertThat(response.getResponseType()).isEqualTo(TouchResponseType.NOT_FOUND);
        assertThat(response.getErrorResponse()).isEmpty();
    }
}