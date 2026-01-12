# Lab10 - Users REST API

Spring Boot REST API project.

## Endpoints

### Get all users
GET /users

### Create user
POST /users

Request body:
```json
{
  "username": "test",
  "email": "test@test.com",
  "password": "1234"
}
