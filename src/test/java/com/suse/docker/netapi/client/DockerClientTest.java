package com.suse.docker.netapi.client;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.results.Return;

public class DockerClientTest {
	private static final int MOCK_HTTP_PORT = 5000;
	private DockerClient dockerClient;

	@Before
	public void init() {
		URI uri = URI.create("http://localhost:" + Integer.toString(MOCK_HTTP_PORT));
		dockerClient = new DockerClient(uri);
	}

	@Test
	public void catalog() throws DockerException {
		Return<List<Map<String, Object>>> result = dockerClient.catalog();
		assertNotNull(result);
	}
}
