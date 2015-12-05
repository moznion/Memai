package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.StorageResponseType;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class StorageResponse implements Response<StorageResponseType> {
    private boolean succeeded;
    private StorageResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
}
