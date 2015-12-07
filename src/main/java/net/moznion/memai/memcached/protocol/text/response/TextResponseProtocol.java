package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedResponseProtocolException;
import net.moznion.memai.memcached.protocol.response.Response;

public interface TextResponseProtocol<T extends Response> {
    T parse(String protocol) throws IllegalMemcachedResponseProtocolException;
}
