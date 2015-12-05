package net.moznion.memai.memcached.protocol.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.OptionalLong;

@Getter
@AllArgsConstructor
public class ValueResponse {
    private int flags;
    private long bytes;
    private OptionalLong casUnique;
    private String data;
}
