package com.suse.docker.netapi.client;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.suse.docker.netapi.client.impl.HttpClientConnectionFactory;
import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.parser.JsonParser;
import com.suse.docker.netapi.results.Return;

public class DockerClient {
	/**
	 * Configuration object
	 */
	private final ClientConfig clientConfig = new ClientConfig();
	
	/**
	 * Connection factory
	 */
	private final ConnectionFactory connectionFactory;
	
	public DockerClient(URI uri) {
		this(uri, new HttpClientConnectionFactory());
	}
	
	public DockerClient(URI url, HttpClientConnectionFactory httpClientConnectionFactory) {
		clientConfig.put(ClientConfig.URL, url);
		this.connectionFactory = httpClientConnectionFactory;
	}
	
	public ClientConfig getClientConfig() {
		return clientConfig;
	}
	
	public Return<List<Map<String, Object>>> catalog() throws DockerException {
		return connectionFactory.create("http://localhost:5000/v2/_catalog", JsonParser.RUN_RESULTS, clientConfig).getResult();
	}
}
