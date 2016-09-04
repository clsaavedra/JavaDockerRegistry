package com.suse.docker.netapi.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.suse.docker.netapi.client.impl.HttpClientConnectionFactory;
import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.parser.JsonParser;
import com.suse.docker.netapi.results.Catalog;
import com.suse.docker.netapi.results.Tags;

/**
 * Client to connect with Docker Registry Service.
 * <p>
 * Implements the operation set to managed the Docker Registry Service.
 * </p>
 */
public class DockerClient {
	/**
	 * Configuration object
	 */
	private final ClientConfig clientConfig = new ClientConfig();

	/**
	 * Connection factory
	 */
	private final ConnectionFactory connectionFactory;

	/**
	 * Create a new DockerClient from URI in String format.
	 * 
	 * @param uri
	 *            string URI.
	 * @throws URISyntaxException
	 *             Mal formed string URI.
	 */
	public DockerClient(final String uri) throws URISyntaxException {
		this(new URI(uri));
	}

	/**
	 * Create a new DockerClient from URI.
	 * 
	 * @param uri
	 *            The URI to Docker Registry Service.
	 */
	public DockerClient(final URI uri) {
		this(uri, new HttpClientConnectionFactory());
	}

	/**
	 * Create a new DockerClient from URI.
	 * <p>
	 * This new DockerClient use the specific HttpClientConnectionFactory.
	 * </p>
	 * 
	 * @param url
	 *            The URI to Docker Registry Service.
	 * @param httpClientConnectionFactory
	 *            The HttpClientConnectionFactory to use.
	 */
	public DockerClient(final URI url, final HttpClientConnectionFactory httpClientConnectionFactory) {
		clientConfig.put(ClientConfig.URL, url);
		this.connectionFactory = httpClientConnectionFactory;
	}

	/**
	 * Get the ClientConfig.
	 * 
	 * @return The ClientConfig
	 */
	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	/**
	 * Execute the _catalog operation in the Docker Registry Service.
	 * 
	 * @return The catalog from the Docker Registry Server.
	 * @throws DockerException
	 *             In case of exception.
	 */
	public Catalog catalog() throws DockerException {
		final URI uri = clientConfig.get(ClientConfig.URL);
		return connectionFactory.create(uri.toString() + "/_catalog", JsonParser.CATALOG, clientConfig).getResult();
	}

	/**
	 * Execute the &lt;name&gt;/tags/list operation int the Docker Registry
	 * Service.
	 * 
	 * @param name
	 *            The repository name.
	 * @return The tags list from this repository.
	 */
	public Tags listTags(final String name) throws DockerException {
		final URI uri = clientConfig.get(ClientConfig.URL);
		return connectionFactory.create(uri.toString() + "/" + name + "/tags/list", JsonParser.TAGS, clientConfig)
				.getResult();
	}
}
