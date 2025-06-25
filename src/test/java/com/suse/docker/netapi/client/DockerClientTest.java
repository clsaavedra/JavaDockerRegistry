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

/**
 * Docker Client Test
 * <p>
 * Docker Client JUnit test.
 * </p>
 */
public class DockerClientTest {
	private static final String ASSERT_MESSAGE_REPOSITORY_DOES_NOT_EXIST = "Does not exists repositories. The registry must have at list one repository";
	/*
	 * Registry version
	 */
	private static final String DEFAULT_HTTP_VERSION = "/v2";
	/*
	 * Default HTTP URL
	 */
	private static final String DEFAULT_HTTP_URL = "http://localhost";
	/*
	 * Default TCP port to connect to Docker Registry Service.
	 */
	private static final int DEFAULT_HTTP_PORT = 5000;
	/*
	 * DockerClient reference
	 */
	private DockerClient dockerClient;

	/**
	 * SetUp the DockerClientTest.
	 */
	@Before
	public void init() {
		final URI uri = URI.create(DEFAULT_HTTP_URL + ":" + Integer.toString(DEFAULT_HTTP_PORT) + DEFAULT_HTTP_VERSION);
		dockerClient = new DockerClient(uri);
	}

	/**
	 * Verified the catalog method.
	 * 
	 * @throws DockerException
	 *             In case of exception.
	 */
	@Test
	public void catalog() throws DockerException {
		Catalog result = dockerClient.catalog();
		assertNotNull(result);
		assertNotNull(result.getRepositories());
		assertFalse(ASSERT_MESSAGE_REPOSITORY_DOES_NOT_EXIST, result.getRepositories().isEmpty());
	}

	/**
	 * Verified the listTags method.
	 * 
	 * @throws DockerException
	 *             In case of exception.
	 */
	@Test
	public void listTags() throws DockerException {
		Catalog catalog = dockerClient.catalog();
		final String name = catalog.getRepositories().get(0);
		Tags tag = dockerClient.listTags(name);
		assertNotNull(tag);
		assertNotNull(tag.getName());
		assertFalse(tag.getTags().isEmpty());
		assertTrue(tag.getTags().size() > 0);
	}
}
