package com.suse.docker.netapi.results;

import java.util.List;

public class Catalog {
	private List<String> repositories;
	
	public Catalog(final List<String> repositories) {
		this.setRepositories(repositories);
	}

	public List<String> getRepositories() {
		return repositories;
	}

	public void setRepositories(final List<String> repositories) {
		this.repositories = repositories;
	}
}
