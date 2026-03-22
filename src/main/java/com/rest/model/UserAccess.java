package com.rest.model;

import java.util.List;

public class UserAccess {
    private String username;
    private List<RepoAccess> repositories;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<RepoAccess> getRepositories() {
		return repositories;
	}
	public void setRepositories(List<RepoAccess> repositories) {
		this.repositories = repositories;
	}
    
}