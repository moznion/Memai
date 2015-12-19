package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.moznion.memai.memcached.protocol.response.type.DeleteResponseType;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class DeleteResponse implements Response<DeleteResponseType> {
    private DeleteResponseType responseType;
    private Optional<ErrorResponse> errorResponse;
    private boolean affected;
}
