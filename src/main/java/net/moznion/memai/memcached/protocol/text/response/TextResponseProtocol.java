package net.moznion.memai.memcached.protocol.text.response;

import net.moznion.memai.memcached.protocol.exception.IllegalMemcachedResponseProtocolException;

public interface TextResponseProtocol<T> {
    T parse(String protocol) throws IllegalMemcachedResponseProtocolException;
}
