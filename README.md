# task-management-app

A clean, secure, and scalable Task Management REST API built with Spring Boot 3.2, Spring Security, and MySQL. Perfect for team collaboration with role-based authorization and input validation.

Key Features
|User Management       |Task Operations                  |Authorization                    |
| -------------------- | ------------------------------- | ------------------------------- |
| Email validation     | Full CRUD                       | Creator/Assignee status updates |
| Duplicate prevention | Status filtering (?status=TODO) | Creator-only deletion           |
| BCrypt passwords     |              | Basic Auth (email:password)     |

# 3. API Ready
Base URL: http://localhost:8080
📋 API List
User APIs (No Authentication)
| Method | Endpoint        | Description             |
| ------ | --------------- | ----------------------- |
| POST   | /users/register | Create new user account |
| POST   | /users/login    | Validate credentials    |

Register Example:

text
POST /users/register

```json
{
  "name": "Dhanush",
  "email": "dhanush@gmail.com", 
  "password": "password123"
}
```


Login Example:

text
POST /users/login
```json
{
  "email": "dhanush@gmail.com",
  "password": "password123"
}
```

Response: "Login successful" or 401 Unauthorized "Invalid email or password"

### Task APIs (Basic Auth: email:password)
### Headers
```
Authorization: Basic auth
Content-Type: application/json
```
| Method | Endpoint           | Description   | Authorization       |
| ------ | ------------------ | ------------- | ------------------- |
| POST   | /tasks             | Create task   | Any authenticated   |
| GET    | /tasks?status=TODO | List tasks    | Creator/Assignee    |
| PATCH  | /tasks/{id}/status | Update status | Creator OR Assignee |
| DELETE | /tasks/{id}        | Delete task   | Creator ONLY        |

### Database Schema
sql
```
-- Users Table
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(500) NOT NULL
);
```
```
-- Tasks Table
CREATE TABLE tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  status ENUM('TODO','IN_PROGRESS','DONE') DEFAULT 'TODO',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by BIGINT NOT NULL,
  assigned_to BIGINT,
  FOREIGN KEY(created_by) REFERENCES users(id)
);
```

### Assumptions

**Basic Auth** used for task APIs (email:password via Postman Authorization tab)

**Login API** validates credentials (separate from Basic Auth)

**Email serves as username** for authentication

**All users have ROLE_USER** (no admin roles needed)

**assignedTo is optional** (null = task for self)

**Soft delete not implemented** (direct DELETE per spec)

**MySQL** localhost:3306 with root/password credentials

**Status values:** TODO, IN_PROGRESS, DONE only
