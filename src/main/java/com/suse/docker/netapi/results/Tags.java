package com.suse.docker.netapi.results;

import java.util.List;

/**
 * The list of tags for a repository element.
 * <p>
 * This class translate from JSON to Java Object a Docker Registry Response.
 * </p>
 */
public class Tags {
	/*
	 * repository name.
	 */
	private String name;

	/*
	 * tags list.
	 */
	private List<String> tags;

	/**
	 * Create a new Object with the tags of this repository.
	 * 
	 * @param name
	 *            Repository name.
	 * @param tags
	 *            Tags list.
	 */
	public Tags(final String name, final List<String> tags) {
		this.setName(name);
		this.setTags(tags);
	}

	/**
	 * The repository name.
	 * 
	 * @return Repository name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the repository name
	 * 
	 * @param name
	 *            Repository name.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Tags list.
	 * 
	 * @return Tags list.
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Set the tags list.
	 * 
	 * @param tags
	 *            Tags list.
	 */
	public void setTags(final List<String> tags) {
		this.tags = tags;
	}
}
