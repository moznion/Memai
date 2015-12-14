package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.ValueResponse;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FlushAllCommandTest {
    @Test
    public void testForDeleteCommandWithExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        client.set("test-key1", "blahblah1").execute().join();
        client.set("test-key2", "blahblah2").execute().join();

        {
            final Map<String, ValueResponse> values = client.get("test-key1", "test-key2").execute().join().getValues();
            assertThat(values.get("test-key1").getData()).isEqualTo("blahblah1");
            assertThat(values.get("test-key2").getData()).isEqualTo("blahblah2");
        }

        client.flushAll().execute();

        {
            final Map<String, ValueResponse> values = client.get("test-key1", "test-key2").execute().join().getValues();
            assertThat(values.get("test-key1")).isNull();
            assertThat(values.get("test-key2")).isNull();
        }
    }
}
