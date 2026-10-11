# BarberBook — Backend

> REST API for the BarberBook appointment booking system, built with Spring Boot 3, Spring Security, JWT authentication, and MySQL.

## Tech Stack

- **Java 21** + **Spring Boot 3.4.5**
- **Spring Security** — stateless JWT-based authentication
- **Hibernate / JPA** — ORM with MySQL
- **JJWT 0.12.6** — JSON Web Token generation & validation
- **Lombok** — boilerplate reduction
- **BCrypt** — password hashing
- **Maven** — build tool

## Features

- User registration & login with JWT tokens
- Role-based access control (`USER` / `ADMIN`)
- Full booking CRUD (create, read, update status, delete)
- Admin dashboard API — view and manage all bookings
- User API — view personal booking history
- CORS configured for Angular frontend at `localhost:4200`
- Auto-creates admin account on first startup

## Getting Started

### Prerequisites

- Java 21
- MySQL 8+
- Maven

### Database Setup

```sql
CREATE DATABASE booking_db;
```

### Configuration

Set the required environment variables before starting the application. Keep these values out of Git and use a long, random Base64 value for `JWT_SECRET`.

```bash
export DB_URL='jdbc:mysql://localhost:3306/booking_db'
export DB_USERNAME='your_username'
export DB_PASSWORD='your_password'
export JWT_SECRET='your-base64-encoded-secret'
```

For local development only, an initial admin can be created deliberately:

```bash
export BOOTSTRAP_ADMIN_ENABLED=true
export BOOTSTRAP_ADMIN_EMAIL='admin@example.com'
export BOOTSTRAP_ADMIN_PASSWORD='choose-a-strong-password'
```

Admin bootstrapping is disabled by default. Never use a sample or shared admin password in a deployed environment.

### Run

```bash
mvn spring-boot:run
```

The server starts on `http://localhost:8080`.

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register new user | Public |
| POST | `/api/auth/login` | Login & receive JWT | Public |

### Bookings
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/bookings` | Create a booking | Public |
| GET | `/api/my-bookings` | Get user's bookings | User |

### Admin
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/bookings` | Get all bookings | Admin |
| PATCH | `/api/admin/bookings/{id}/status` | Update booking status | Admin |
| DELETE | `/api/admin/bookings/{id}` | Delete a booking | Admin |

## Project Structure

```
src/main/java/com/sebastian/booking_backend/
├── config/           # Security, CORS, DataInitializer
├── controller/       # REST controllers
├── model/            # JPA entities + DTOs
├── repository/       # Spring Data JPA repositories
├── security/         # JWT filter & utility
└── service/          # Business logic
```

## Related

- **Frontend:** [barberbook-frontend](https://github.com/sebxstixn15/barberbook-frontend)
