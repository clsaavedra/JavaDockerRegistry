package com.suse.docker.netapi.client.impl;


import com.suse.docker.netapi.client.Connection;
import com.suse.docker.netapi.client.ConnectionFactory;
import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.parser.JsonParser;

public class HttpClientConnectionFactory implements ConnectionFactory {

	@Override
	public <T> Connection<T> create(String endpoing, JsonParser<T> parser, ClientConfig config) {
		return new HttpClientConnection<>(endpoing, parser, config);
	}

}
