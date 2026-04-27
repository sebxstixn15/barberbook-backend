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

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/booking_db
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your-base64-encoded-secret
jwt.expiration=86400000
```

### Run

```bash
mvn spring-boot:run
```

The server starts on `http://localhost:8080`.

On first run, an admin account is created automatically:
- **Email:** `admin@booking.at`
- **Password:** `admin123`

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
