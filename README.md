## Overview
**ToDa App** is a microservices-based project consisting of two independent services:

- **User Service**: Handles user management, authentication, authorization (with JWT), password reset via OTP, and user registration.
- **Todo Service**: Manages CRUD operations on todo items, each linked to a specific user.

This project demonstrates secure API development with best practices, MySQL integration, global exception handling, Swagger API documentation, and unit testing.

---

## Technologies
- Java
- Spring Boot
- Spring Security with JWT
- MySQL
- Swagger
- JUnit 5
- JavaMailSender (for OTP emails)
- Maven

---

## Database Design

### User Service
- **users** (`id`, `email`, `password`, `enabled`)
- **otp** (`id`, `otp`, `expiration_time`, `user_id`)
- **jwt** (`id`, `token`, `user_id`, `created_at`, `expiration_date`, `token_type`)

### Todo Service
- **items** (`id`, `title`, `user_id`, `item_details_id`)
- **item_details** (`id`, `description`, `created_at`, `priority`, `status`, `startAt`, `finishAt`)

---

## API Endpoints

### User Service - Auth APIs
- `/login`
- `/register`
- `/activate`
- `/checkToken`
- `/forgetPassword`
- `/changePassword`
- `/regenrateOtp`

### User Service - User Operations
- `/delete`
- `/update`

### Todo Service
- `/add`
- `/delete/{id}`
- `/update/{id}`
- `/search/{id}`

> All Todo APIs require JWT validation via `/checkToken` call to User Service.

---

## ERD Diagram

```mermaid
erDiagram
    USER ||--o{ OTP : generates
    USER ||--o{ JWT : issues
    USER ||--o{ ITEM : owns
    ITEM ||--o{ ITEMDETAILS : has

    USER {
        id Long PK
        email String
        password String
        enabled Boolean
    }

    OTP {
        id Long PK
        otp String
        expiration_time DateTime
        user_id Long FK
    }

    JWT {
        id Long PK
        token String
        user_id Long FK
        created_at DateTime
        expiration_date DateTime
        token_type String
    }

    ITEM {
        id Long PK
        title String
        user_id Long FK
        item_details_id Long FK
    }

    ITEMDETAILS {
        id Long PK
        description String
        created_at DateTime
        priority String
        status String
        startAt DateTime
        finishAt DateTime
    }
