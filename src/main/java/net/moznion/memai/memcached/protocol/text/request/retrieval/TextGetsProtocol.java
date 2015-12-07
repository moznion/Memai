package net.moznion.memai.memcached.protocol.text.request.retrieval;

import lombok.Getter;
import net.moznion.memai.memcached.protocol.text.response.TextRetrievalResponseProtocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextGetsProtocol implements TextRetrievalProtocol<TextGetsProtocol> {
    @Getter
    private List<String> keys;

    public TextGetsProtocol(String key) {
        this.keys = new ArrayList<>();
        this.keys.add(key);
    }

    public TextGetsProtocol(String... keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
    }

    public TextGetsProtocol(List<String> keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(keys);
    }

    @Override
    public TextGetsProtocol setKeys(String... keys) {
        this.keys.clear();
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }

    @Override
    public TextGetsProtocol setKeys(List<String> keys) {
        this.keys.clear();
        this.keys.addAll(keys);
        return this;
    }

    @Override
    public TextGetsProtocol setKey(String key) {
        this.keys.clear();
        this.keys.add(key);
        return this;
    }

    @Override
    public TextGetsProtocol appendKeys(String... keys) {
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }

    @Override
    public TextGetsProtocol appendKeys(List<String> keys) {
        this.keys.addAll(keys);
        return this;
    }

    @Override
    public TextGetsProtocol appendKey(String key) {
        this.keys.add(key);
        return this;
    }

    @Override
    public String command() {
        return "gets";
    }

    @Override
    public TextRetrievalResponseProtocol getResponseProtocol() {
        return new TextRetrievalResponseProtocol();
    }
}
