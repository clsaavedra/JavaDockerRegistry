package com.suse.docker.netapi.client;


import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.parser.JsonParser;

public interface ConnectionFactory {
	<T> Connection<T> create(String endpoing, JsonParser<T> parser, ClientConfig config);
}
