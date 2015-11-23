package net.moznion.memai.memcached.protocol;

public interface StorageProtocol<T> extends Protocol {
    T key(String key);

    T data(byte[] data);

    T flags(int flags);

    T exptime(int exptime);

    T noreply(boolean noreply);
}
