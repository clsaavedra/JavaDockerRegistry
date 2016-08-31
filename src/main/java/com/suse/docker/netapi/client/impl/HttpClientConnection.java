package com.suse.docker.netapi.client.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.suse.docker.netapi.client.Connection;
import com.suse.docker.netapi.config.ClientConfig;
import com.suse.docker.netapi.exception.DockerException;
import com.suse.docker.netapi.exception.DockerUserUnauthorizedException;
import com.suse.docker.netapi.parser.JsonParser;

public class HttpClientConnection<T> implements Connection<T> {
	private final String endpoint;
	private final ClientConfig clientConfig;
	private final JsonParser<T> parser;

	public HttpClientConnection(String endpoint, JsonParser<T> parser, ClientConfig clientConfig) {
		this.endpoint = endpoint;
		this.clientConfig = clientConfig;
		this.parser = parser;
	}

	@Override
	public T getResult() throws DockerException {
		return getResult(null);
	}

	@Override
	public T getResult(String data) throws DockerException {
		return request(data);
	}

	private T request(String data) throws DockerException {
		try (CloseableHttpClient httpClient = initializeHttpClient().build()) {
			return executeRequest(httpClient, prepareRequest(data));
		} catch (IOException e) {
			throw new DockerException(e);
		}
	}

	private T executeRequest(CloseableHttpClient httpClient, HttpUriRequest httpRequest)
			throws DockerException, IOException {
		try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_ACCEPTED) {
				return (T) parser.parse(response.getEntity().getContent());
			} else {
				throw createDockerException(statusCode);
			}
		}
	}

	private DockerException createDockerException(int statusCode) {
		if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
			return new DockerUserUnauthorizedException("Docker user does not have sufficient permissions");
		}
		return new DockerException("Response code: " + statusCode);
	}

	private HttpUriRequest prepareRequest(String data) throws UnsupportedEncodingException {
		URI uri = clientConfig.get(ClientConfig.URL).resolve(endpoint);
		HttpUriRequest httpRequest;
		if (data != null) {
			// POST data
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			httpPost.setEntity(new StringEntity(data));
			httpRequest = httpPost;
		} else {
			// GET request
			httpRequest = new HttpGet(uri);
		}
		httpRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

		// Token authentication
		String token = clientConfig.get(ClientConfig.TOKEN);
		if (token != null) {
			httpRequest.addHeader("X-Auth-Token", token);
		}
		return httpRequest;
	}

	private HttpClientBuilder initializeHttpClient() {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		configureTimeouts(httpClientBuilder);
		configureProxyIfSpecified(httpClientBuilder);
		return httpClientBuilder;
	}

	private void configureProxyIfSpecified(HttpClientBuilder httpClientBuilder) {
		String proxyHost = clientConfig.get(ClientConfig.PROXY_HOSTNAME);
		if (proxyHost != null) {
			int proxyPort = clientConfig.get(ClientConfig.PROXY_PORT);
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			httpClientBuilder.setProxy(proxy);

			String proxyUsername = clientConfig.get(ClientConfig.PROXY_USERNAME);
			String proxyPassword = clientConfig.get(ClientConfig.PROXY_PASSWORD);

			// Proxy authentication
			if (proxyUsername != null && proxyPassword != null) {
				CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
						new UsernamePasswordCredentials(proxyUsername, proxyPassword));
				httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}
		}
	}

	private void configureTimeouts(HttpClientBuilder httpClientBuilder) {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(clientConfig.get(ClientConfig.CONNECT_TIMEOUT))
				.setSocketTimeout(clientConfig.get(ClientConfig.SOCKET_TIMEOUT)).build();
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
	}
}
