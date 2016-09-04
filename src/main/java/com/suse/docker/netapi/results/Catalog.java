package com.suse.docker.netapi.results;

import java.util.List;

/**
 * List of repositories from the Docker Registry.
 * <p>
 * Translate from JSON to Java Object the repository list from the Docker
 * Registry Service.
 * </p>
 */
public class Catalog {
	/*
	 * repository list.
	 */
	private List<String> repositories;

	/**
	 * Create a new Catalog.
	 * 
	 * @param repositories
	 *            Repository list.
	 */
	public Catalog(final List<String> repositories) {
		this.setRepositories(repositories);
	}

	/**
	 * Repository list.
	 * 
	 * @return Repository list.
	 */
	public List<String> getRepositories() {
		return repositories;
	}

	/**
	 * Set the repository list.
	 * 
	 * @param repositories
	 *            Repository list.
	 */
	public void setRepositories(final List<String> repositories) {
		this.repositories = repositories;
	}
}
