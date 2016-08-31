package com.suse.docker.netapi.exception;

public class DockerUserUnauthorizedException extends DockerException {
	private static final long serialVersionUID = 3361511041646084953L;

	public DockerUserUnauthorizedException(String message) {
		super(message);
	}
}
