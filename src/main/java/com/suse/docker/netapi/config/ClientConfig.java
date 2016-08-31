package com.suse.docker.netapi.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ClientConfig {
	public static final Key<URI> URL = new Key<>(URI.create("http://localhost:5000/v2"));
	public static final Key<Integer> CONNECT_TIMEOUT = new Key<>(10000);
	public static final Key<Integer> SOCKET_TIMEOUT = new Key<>(10000);
	public static final Key<String> PROXY_HOSTNAME = new Key<>();
	public static final Key<Integer> PROXY_PORT = new Key<>(3128);
	public static final Key<String> PROXY_USERNAME = new Key<>();
	public static final Key<String> PROXY_PASSWORD = new Key<>();
	public static final Key<String> TOKEN = new Key<>();
	
    /**
     * A key to use with {@link ClientConfig}.
     * @param <T> The type of the value associated with this key.
     */
    static class Key<T> {

        /** The default value of this key */
        public final T defaultValue;

        /**
         * Creates a new Key with the default value null.
         */
        public Key() {
            this(null);
        }

        /**
         * Creates a new key with the specified default value.
         *
         * @param defaultValue Default value for this key.
         */
        public Key(T defaultValue) {
            this.defaultValue = defaultValue;
        }

    }

	private final Map<Key<?>, Object> store = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T get(Key<T> key) {
		Object value = store .get(key);
		return value != null ? (T) value : key.defaultValue;
	}

	public <T> void put(Key<T> key, T value) {
		if(value == null || value.equals(key.defaultValue)) {
			remove(key);
		} else {
			store.put(key, value);
		}
	}

	private <T> void remove(Key<T> key) {
		store.remove(key);
	}
}
