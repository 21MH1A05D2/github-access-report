package com.rest.controller;


import com.rest.service.GitHubService;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccessController {

	 private final GitHubService gitHubService;

	    public AccessController(GitHubService gitHubService) {
	        this.gitHubService = gitHubService;
	    }

	    @GetMapping("/access-report")
	    public Map<String, Object> getAccessReport(@RequestParam String org) {
	        return gitHubService.getAccessReport(org);
	    }
}


