package com.suse.docker.netapi.client;

import com.suse.docker.netapi.exception.DockerException;

public interface Connection<T> {
	T getResult() throws DockerException;
	T getResult(String data) throws DockerException;
}
