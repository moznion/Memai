package net.moznion.memai.memcached.command;

import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.DeleteResponse;
import net.moznion.memai.memcached.protocol.response.type.DeleteResponseType;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DeleteCommandTest {
    @Test
    public void testForDeleteCommandWithExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        client.set("test-key", "blahblah").execute().join();

        final CompletableFuture<DeleteResponse> futureOfDelete = client.delete("test-key").execute();
        final DeleteResponse responseOfDelete = futureOfDelete.join();
        assertThat(responseOfDelete.getErrorResponse()).isEmpty();
        assertThat(responseOfDelete.getResponseType()).isEqualTo(DeleteResponseType.DELETED);
    }

    @Test
    public void testForDeleteCommandWithNotExistedKey() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<DeleteResponse> futureOfDelete = client.delete("test-key").execute();
        final DeleteResponse responseOfDelete = futureOfDelete.join();
        assertThat(responseOfDelete.getErrorResponse()).isEmpty();
        assertThat(responseOfDelete.getResponseType()).isEqualTo(DeleteResponseType.NOT_FOUND);
    }
}
