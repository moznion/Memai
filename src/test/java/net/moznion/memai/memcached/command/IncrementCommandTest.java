package net.moznion.memai.memcached.command;

import net.moznion.memai.TestBase;
import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.IncrementalResponse;
import net.moznion.memai.memcached.protocol.response.type.IncrementalResponseType;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class IncrementCommandTest extends TestBase {
    @Test
    public void testForIncrCommandWithExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        client.set("test-key", "1").execute().join();

        final CompletableFuture<IncrementalResponse> resultOfIncr = client.incr("test-key", 100).execute();
        final IncrementalResponse responseOfIncr = resultOfIncr.join();
        assertThat(responseOfIncr.getResponseType()).isEqualTo(IncrementalResponseType.VALUE_CHANGED);
        final OptionalLong maybeValue = responseOfIncr.getValue();
        assertThat(maybeValue).isPresent();
        assertThat(maybeValue.getAsLong()).isEqualTo(101);
    }

    @Test
    public void testForIncrCommandWithNotExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<IncrementalResponse> resultOfIncr = client.incr("test-key", 100).execute();
        final IncrementalResponse responseOfIncr = resultOfIncr.join();
        assertThat(responseOfIncr.getResponseType()).isEqualTo(IncrementalResponseType.NOT_FOUND);
        final OptionalLong maybeValue = responseOfIncr.getValue();
        assertThat(maybeValue).isEmpty();
    }
}
