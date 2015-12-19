package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.StatsResponse;
import net.moznion.memai.memcached.protocol.response.type.StatsResponseType;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class StatsCommandTest {
    @Test
    public void testForStatsCommand() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<StatsResponse> futureForStats = client.stats().execute();
        final StatsResponse statsResponse = futureForStats.join();

        assertThat(statsResponse.getErrorResponse()).isEmpty();
        assertThat(statsResponse.getResponseType()).isEqualTo(StatsResponseType.OK);
        assertThat(statsResponse.getStats().get("threads")).isNotNull();
        assertThat(statsResponse.getStats().get("DUMMY-KEY-FOR-TEST")).isNull();
    }

    @Test
    public void testForStatsCommandWithArgument() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        // With constructor
        {
            final CompletableFuture<StatsResponse> futureForStats = client.stats("settings").execute();
            final StatsResponse statsResponse = futureForStats.join();

            assertThat(statsResponse.getErrorResponse()).isEmpty();
            assertThat(statsResponse.getResponseType()).isEqualTo(StatsResponseType.OK);
            assertThat(statsResponse.getStats().get("slab_reassign")).isNotNull();
            assertThat(statsResponse.getStats().get("DUMMY-KEY-FOR-TEST")).isNull();
        }

        // With method
        {
            final CompletableFuture<StatsResponse> futureForStats = client.stats().args("settings").execute();
            final StatsResponse statsResponse = futureForStats.join();

            assertThat(statsResponse.getErrorResponse()).isEmpty();
            assertThat(statsResponse.getResponseType()).isEqualTo(StatsResponseType.OK);
            assertThat(statsResponse.getStats().get("slab_reassign")).isNotNull();
            assertThat(statsResponse.getStats().get("DUMMY-KEY-FOR-TEST")).isNull();
        }
    }
}
