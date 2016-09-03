package com.suse.docker.netapi.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.results.Catalog;
import com.suse.docker.netapi.results.Tags;

public class DockerClientTest {
	private static final int MOCK_HTTP_PORT = 5000;
	private DockerClient dockerClient;

	@Before
	public void init() {
		URI uri = URI.create("http://localhost:" + Integer.toString(MOCK_HTTP_PORT) + "/v2");
		dockerClient = new DockerClient(uri);
	}

	@Test
	public void catalog() throws DockerException {
		Catalog result = dockerClient.catalog();
		assertNotNull(result);
		assertNotNull(result.getRepositories());
		assertFalse(result.getRepositories().isEmpty());
		assertTrue(result.getRepositories().size() > 0);
		assertTrue(result.getRepositories().get(0).equalsIgnoreCase("ubuntu"));
	}
	
	@Test
	public void listTags() throws DockerException {
		Catalog catalog = dockerClient.catalog();
		assertNotNull(catalog);
		assertNotNull(catalog.getRepositories());
		assertFalse(catalog.getRepositories().isEmpty());
		assertTrue(catalog.getRepositories().size() > 0);
		final String name = catalog.getRepositories().get(0);
		Tags tag = dockerClient.listTags(name);
		assertNotNull(tag);
		assertNotNull(tag.getName());
		assertFalse(tag.getTags().isEmpty());
		assertTrue(tag.getTags().size() > 0);
	}
}
