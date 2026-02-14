# task-management-app

A clean, secure, and scalable Task Management REST API built with Spring Boot 3.2, Spring Security, and MySQL. Perfect for team collaboration with role-based authorization and input validation.

Key Features
|User Management       |Task Operations                  |Authorization                    |
| -------------------- | ------------------------------- | ------------------------------- |
| Email validation     | Full CRUD                       | Creator/Assignee status updates |
| Duplicate prevention | Status filtering (?status=TODO) | Creator-only deletion           |
| BCrypt passwords     | Auto timestamps                 | Basic Auth (email:password)     |

## 1. Clone & Run
git clone https://github.com/venkatadhanushkumar/task-management-app.git
cd task-management-app
mvn spring-boot:run

