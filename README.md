How to Run the Project

Clone the repository:
git clone https://github.com/21MH1A05D2/github-access-report.git
cd github-access-report

Set your GitHub Personal Access Token (PAT) as an environment variable:
Windows:
setx GITHUB_TOKEN "your_personal_access_token"
Linux/macOS:
export GITHUB_TOKEN="your_personal_access_token"

Configure your application properties in src/main/resources/application.properties:
github.token=${GITHUB_TOKEN}

Run the application:
mvn spring-boot:run
The app will start at: http://localhost:8080

Authentication
Uses a GitHub Personal Access Token (PAT).
Token is read from the environment variable GITHUB_TOKEN.
Required scopes:
repo → to access repository collaborators
read:org → to read organization information

API Endpoint:
GET http://localhost:8080/api/access-report?org=org-name

Sample Response
The API now returns the full GitHub permissions object for each repository:
{
  "users": {
    "21MH1A05D2": [
      {
        "repoName": "21MH1A05D2",
        "permissions": { "admin": true, "push": true, "pull": true }
      },
      {
        "repoName": "catalog",
        "permissions": { "admin": true, "push": true, "pull": true }
      },
      {
        "repoName": "github-access-report",
        "permissions": { "admin": true, "push": true, "pull": true }
      },
      {
        "repoName": "Recipehub",
        "permissions": { "admin": true, "push": true, "pull": true }
      },
      {
        "repoName": "travelbookingmanagement",
        "permissions": { "admin": true, "push": true, "pull": true }
      },
      {
        "repoName": "whatsappchatanalyzer",
        "permissions": { "admin": true, "push": true, "pull": true }
      }
    ],
    "alice": [
      {
        "repoName": "catalog",
        "permissions": { "admin": false, "push": true, "pull": true }
      }
    ]
  }
}

Assumptions & Design Decisions
Only repositories and users within the specified organization are included.
Permissions are reported exactly as provided by GitHub (admin, push, pull).
Repository owners are included with full permissions (admin=true, push=true, pull=true).
GitHub PAT is accessed securely via environment variables, not stored in code.
The service fetches all collaborators including users with direct and indirect access (via teams can be added in future).
Pagination is handled with per_page=100 for scalability (supports organizations with 100+ repositories and 1000+ users).

