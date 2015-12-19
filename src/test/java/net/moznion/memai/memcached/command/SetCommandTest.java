package net.moznion.memai.memcached.command;

import net.moznion.memai.TestBase;
import net.moznion.memai.memcached.Client;
import net.moznion.memai.memcached.protocol.response.RetrievalResponse;
import net.moznion.memai.memcached.protocol.response.StorageResponse;
import net.moznion.memai.memcached.protocol.response.type.RetrievalResponseType;
import net.moznion.memai.memcached.protocol.response.type.StorageResponseType;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SetCommandTest extends TestBase {
    @Test
    public void testForSet() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<StorageResponse> futureOfSet = client.set("test-key", "blahblah").execute();
        final StorageResponse responseOfSet = futureOfSet.join();
        assertThat(responseOfSet.getResponseType()).isEqualTo(StorageResponseType.STORED);
        assertThat(responseOfSet.getErrorResponse()).isEmpty();

        final CompletableFuture<RetrievalResponse> futureOfGet = client.get("test-key").execute();
        final RetrievalResponse responseOfGet = futureOfGet.join();
        assertThat(responseOfGet.getResponseType()).isEqualTo(RetrievalResponseType.RETRIEVED);
        assertThat(responseOfGet.getValues().get("test-key").getData()).isEqualTo("blahblah"); // TODO
        assertThat(responseOfGet.isAffected()).isTrue();
    }

    @Test
    public void testForSetMultiByte() throws Exception {
        final Client client = new Client(new InetSocketAddress("127.0.0.1", 11211), 1);

        final CompletableFuture<StorageResponse> futureOfSet = client.set("テストキー", "こんにちは").execute();
        final StorageResponse responseOfSet = futureOfSet.join();
        assertThat(responseOfSet.getResponseType()).isEqualTo(StorageResponseType.STORED);
        assertThat(responseOfSet.getErrorResponse()).isEmpty();

        final CompletableFuture<RetrievalResponse> futureOfGet = client.get("テストキー").execute();
        final RetrievalResponse responseOfGet = futureOfGet.join();
        assertThat(responseOfGet.getResponseType()).isEqualTo(RetrievalResponseType.RETRIEVED);
        assertThat(responseOfGet.getValues().get("テストキー").getData()).isEqualTo("こんにちは"); // TODO
        assertThat(responseOfGet.isAffected()).isTrue();
    }
}