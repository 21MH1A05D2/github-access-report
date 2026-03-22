GitHub Access Report

How to Run the Project
Clone the repository:
git clone https://github.com/21MH1A05D2/github-access-report.git
cd github-access-report

Set your GitHub Personal Access Token (PAT) as an environment variable:
Windows:
setx GITHUB_TOKEN "your_personal_access_token"
Linux/macOS:
export GITHUB_TOKEN="your_personal_access_token"

Configure your organization name in src/main/resources/application.properties:
github.token=${GITHUB_TOKEN}


Run the application:
mvn spring-boot:run
The app will start at http://localhost:8080.

How Authentication is Configured
Uses a GitHub Personal Access Token (PAT).
Token is read from the environment variable GITHUB_TOKEN.
Required scopes: repo and read:org.
How to Call the API Endpoint

Endpoint:
GET http://localhost:8080/api/access-report?org=org-name

Sample Response:

{
  "users": {
    "21MH1A05D2": [
      {"repoName": "21MH1A05D2", "permission": "owner"},
      {"repoName": "catalog", "permission": "owner"},
      {"repoName": "github-access-report", "permission": "owner"},
      {"repoName": "Recipehub", "permission": "owner"},
      {"repoName": "travelbookingmanagement", "permission": "owner"},
      {"repoName": "whatsappchatanalyzer", "permission": "owner"}
    ]
  }
}

Assumptions or Design Decisions
Only repositories and users within the specified organization are included.
Permissions are reported as provided by GitHub (owner, write, read).
GitHub PAT is accessed securely via environment variables, not stored in code.
