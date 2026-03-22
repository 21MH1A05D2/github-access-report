package com.rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    
    private List<Map<String, Object>> getWithHeaders(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github.v3+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                List.class
        );

        return response.getBody();
    }

    public Map<String, Object> getAccessReport(String org) {
        List<Map<String, Object>> repos;

   
        try {
            String orgUrl = "https://api.github.com/orgs/" + org + "/repos?per_page=100";
            repos = getWithHeaders(orgUrl);
        } catch (Exception e) {
            
            String userUrl = "https://api.github.com/users/" + org + "/repos?per_page=100";
            repos = getWithHeaders(userUrl);
        }

        Map<String, List<Map<String, Object>>> userAccessMap = new HashMap<>();

        if (repos == null) return Collections.emptyMap();

        for (Map<String, Object> repo : repos) {
            String repoName = (String) repo.get("name");
            String owner = (String) ((Map<String, Object>) repo.get("owner")).get("login");

            Map<String, Object> ownerPermissions = new HashMap<>();
            ownerPermissions.put("admin", true);
            ownerPermissions.put("push", true);
            ownerPermissions.put("pull", true);

            Map<String, Object> ownerDetails = new HashMap<>();
            ownerDetails.put("repoName", repoName);
            ownerDetails.put("permissions", ownerPermissions);

            userAccessMap.computeIfAbsent(owner, k -> new ArrayList<>()).add(ownerDetails);

            
            String collabUrl = "https://api.github.com/repos/" + owner + "/" + repoName + "/collaborators?permission=all&per_page=100";

            List<Map<String, Object>> collaborators;
            try {
                collaborators = getWithHeaders(collabUrl);
            } catch (Exception e) {
                continue;
            }

            if (collaborators == null) continue;

            for (Map<String, Object> user : collaborators) {
                String username = (String) user.get("login");
                Map<String, Object> permissions = (Map<String, Object>) user.get("permissions");

                Map<String, Object> repoDetails = new HashMap<>();
                repoDetails.put("repoName", repoName);
                repoDetails.put("permissions", permissions); 
                userAccessMap.computeIfAbsent(username, k -> new ArrayList<>()).add(repoDetails);
            }
        }

        
        Map<String, Object> response = new HashMap<>();
        response.put("organization", org);
        response.put("generated_at", new Date().toString());
        response.put("users", userAccessMap);
        response.put("metadata", Map.of(
                "total_users", userAccessMap.size(),
                "total_repositories", repos.size()
        ));

        return response;
    }
}
