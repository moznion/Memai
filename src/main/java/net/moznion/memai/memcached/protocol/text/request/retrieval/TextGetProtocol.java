package net.moznion.memai.memcached.protocol.text.request.retrieval;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextGetProtocol implements TextRetrievalProtocol<TextGetProtocol> {
    @Getter
    private List<String> keys;

    public TextGetProtocol(String key) {
        this.keys = new ArrayList<>();
        this.keys.add(key);
    }

    public TextGetProtocol(String... keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
    }

    public TextGetProtocol(List<String> keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(keys);
    }

    @Override
    public TextGetProtocol setKeys(String... keys) {
        this.keys.clear();
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }

    @Override
    public TextGetProtocol setKeys(List<String> keys) {
        this.keys.clear();
        this.keys.addAll(keys);
        return this;
    }

    @Override
    public TextGetProtocol setKey(String key) {
        this.keys.clear();
        this.keys.add(key);
        return this;
    }

    @Override
    public TextGetProtocol appendKeys(String... keys) {
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }

    @Override
    public TextGetProtocol appendKeys(List<String> keys) {
        this.keys.addAll(keys);
        return this;
    }

    @Override
    public TextGetProtocol appendKey(String key) {
        this.keys.add(key);
        return this;
    }

    @Override
    public String command() {
        return "get";
    }
}
