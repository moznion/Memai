package net.moznion.memai.memcached.protocol.text.request;

import net.moznion.memai.memcached.protocol.Protocol;
import net.moznion.memai.memcached.protocol.text.response.TextResponseProtocol;

public interface TextRequestProtocol<T extends TextResponseProtocol> extends Protocol {
    T getResponseProtocol();
}
