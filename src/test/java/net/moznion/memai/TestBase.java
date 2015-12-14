package net.moznion.memai;

import net.moznion.memai.memcached.Client;
import org.junit.Before;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TestBase {
    @Before
    public void before() throws IOException {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);
        client.flushAll().execute().join();
    }
}
