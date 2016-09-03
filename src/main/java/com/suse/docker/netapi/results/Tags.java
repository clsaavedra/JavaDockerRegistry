package com.suse.docker.netapi.results;

import java.util.List;

public class Tags {
	private String name;
	private List<String> tags;
	
	public Tags(String name, List<String> tags) {
		this.setName(name);
		this.setTags(tags);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
