package com.suse.docker.netapi.client;

import java.net.URI;

import com.suse.docker.netapi.client.impl.HttpClientConnectionFactory;
import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.parser.JsonParser;
import com.suse.docker.netapi.results.Catalog;
import com.suse.docker.netapi.results.Tags;

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

	public Catalog catalog() throws DockerException {
		final URI uri = clientConfig.get(ClientConfig.URL);
		return connectionFactory.create(uri.toString() + "/_catalog", JsonParser.CATALOG, clientConfig).getResult();
	}
	
	public Tags listTags(final String name) throws DockerException {
		final URI uri = clientConfig.get(ClientConfig.URL);
		return connectionFactory.create(uri.toString() + "/" + name + "/tags/list", JsonParser.TAGS, clientConfig).getResult();
	}
}
