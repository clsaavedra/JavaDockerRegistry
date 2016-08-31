package com.suse.docker.netapi.exception;

public class DockerException extends Exception {
	private static final long serialVersionUID = 4501272808612691724L;

	public DockerException(Throwable cause) {
		super(cause);
	}
	
	public DockerException(String message) {
		super(message);
	}
}
