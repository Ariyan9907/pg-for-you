
````markdown
# 🏠 PGForYou

PGForYou is a web-based PG (Paying Guest) accommodation management and discovery application built using Java and Spring Boot.

The application allows users to browse PGs, search PGs by location, view rooms and availability, register and log in, book rooms, view booking history, cancel bookings, and submit reviews.

The project demonstrates practical backend development using Spring Boot, Spring MVC, Spring Data JPA, Hibernate, PostgreSQL, Spring Security, JWT authentication, JSP, DTOs, validation, exception handling, and unit testing.

---

# 🎯 Project Objective

The main objective of PGForYou is to build a real-world accommodation management application while applying backend development concepts.

The application focuses on:

- PG listing and searching
- Room management
- Room availability
- User registration and login
- JWT-based authentication
- Role-based authorization
- Booking management
- Booking cancellation
- Booking history
- PG reviews
- REST APIs
- JSP-based frontend
- DTO-based data transfer
- Jakarta Bean Validation
- Global exception handling
- Transaction management
- Unit testing

---

# 🚀 Features

## 1. PG Management

Users can:

- View all PGs
- View individual PG details
- Search PGs by location
- View PG images
- View PG rent and location

Each PG contains:

- Name
- Location
- Rent
- Image URL

---

## 2. Room Management

Each PG can have multiple rooms.

Example:

```text
Sri Sai PG
│
├── Single Sharing
├── Double Sharing
├── Triple Sharing
├── Four Sharing
└── Six Sharing
````

Each room contains:

* Sharing type
* Rent
* Total number of rooms
* Available number of rooms
* Room image
* Associated PG

Room availability is displayed as:

```text
Available: 2 / 3
```

---

## 3. Room Booking

Users can book an available room.

Booking flow:

```text
View PG
    ↓
View Room
    ↓
Book Now
    ↓
Enter Name & Phone
    ↓
Submit Booking
    ↓
Booking Confirmed
```

When a booking is successfully created:

```text
availableRooms--
```

Example:

```text
Before booking:
Available: 3 / 3

After booking:
Available: 2 / 3
```

The booking status is initially:

```text
CONFIRMED
```

---

## 4. Booking History

Users can view their booking records through the booking history page.

The booking history displays:

* Booking ID
* Name
* Phone
* Room ID
* Booking date
* Booking status

---

## 5. Booking Cancellation

A confirmed booking can be cancelled.

When cancelled:

```text
CONFIRMED → CANCELLED
```

The room availability is restored:

```text
availableRooms++
```

The booking record is retained in the database instead of being deleted.

---

## 6. PG Reviews

Users can submit reviews for a PG.

A review contains:

* Name
* Rating
* Comment
* Associated PG

Example:

```text
Aryan
⭐⭐⭐⭐⭐

Very good PG and excellent location.
```

Reviews are displayed on the PG details page.

---

## 7. Search

Users can search PGs by location.

Example:

```text
Search:
Electronic City
```

The application returns PGs matching the specified location.

---

# 🔐 Authentication & Authorization

PGForYou uses **Spring Security with JWT authentication**.

The authentication system provides:

* User registration
* User login
* BCrypt password hashing
* JWT token generation
* JWT-based authentication
* HTTP-only JWT cookie
* Role-based authorization
* Stateless security
* Custom 401 and 403 error handling

---

## User Registration

During registration, the password is encrypted using:

```text
BCryptPasswordEncoder
```

The plain-text password is never stored in the database.

Registration flow:

```text
Register Form
      ↓
AuthController
      ↓
AuthService
      ↓
PasswordEncoder
      ↓
UserRepository
      ↓
PostgreSQL
```

---

## User Login

Login uses Spring Security's `AuthenticationManager`.

Flow:

```text
Login Form
     ↓
AuthController
     ↓
AuthService
     ↓
AuthenticationManager
     ↓
DaoAuthenticationProvider
     ↓
UserService
     ↓
UserRepository
     ↓
BCryptPasswordEncoder
     ↓
Authentication Successful
     ↓
JwtService
     ↓
JWT Token
     ↓
HTTP-only Cookie
```

---

## JWT Authentication

After successful login, a JWT is generated containing the user's email as the subject.

The JWT is stored inside an HTTP-only cookie:

```text
jwt
```

Example:

```text
Cookie:
jwt=eyJhbGciOiJIUzI1NiJ9...
```

The cookie is configured with:

```text
HttpOnly
Path=/
SameSite=Lax
Max-Age=3600
```

The `HttpOnly` flag prevents JavaScript from directly accessing the JWT cookie.

---

# 🛡️ JWT Authentication Filter

A custom:

```java
JwtAuthenticationFilter
```

extends:

```java
OncePerRequestFilter
```

For every protected request, the filter:

1. Reads the JWT from the cookie.
2. Extracts the user's email from the token.
3. Loads the user using `UserService`.
4. Checks whether the token is expired.
5. Creates an `Authentication` object.
6. Stores the authentication in `SecurityContext`.

Flow:

```text
Browser
   ↓
JWT Cookie
   ↓
JwtAuthenticationFilter
   ↓
JwtService
   ↓
Extract Email
   ↓
UserService
   ↓
UserRepository
   ↓
UserDetails
   ↓
Authentication
   ↓
SecurityContext
   ↓
Controller
```

---

# 👥 Role-Based Authorization

The application supports user roles such as:

```text
USER
ADMIN
```

Admin APIs require:

```java
.hasRole("ADMIN")
```

Regular authenticated APIs allow:

```java
.hasAnyRole("USER", "ADMIN")
```

Example:

```text
/api/admin/**
        ↓
      ADMIN

/api/pgs/**
/api/rooms/**
/api/bookings/**
/api/reviews/**
        ↓
   USER or ADMIN
```

---

# 🔒 Stateless Security

The application uses:

```java
SessionCreationPolicy.STATELESS
```

This means Spring Security does not maintain a traditional server-side login session.

Instead:

```text
Every request
     ↓
JWT Cookie
     ↓
JWT Filter
     ↓
Authentication
```

The JWT represents the authenticated user.

---

# 🚫 CSRF Configuration

Since the application uses JWT-based stateless authentication, CSRF protection is disabled in the current configuration:

```java
.csrf(csrf -> csrf.disable())
```

The application uses the JWT stored in an HTTP-only cookie for authentication.

---

# ⚠️ Custom Security Error Handling

The application provides custom handling for authentication and authorization errors.

## 401 Unauthorized

Returned when the request is not authenticated.

Example:

```json
{
  "error": "Unauthorized - Please login"
}
```

---

## 403 Forbidden

Returned when the user is authenticated but does not have sufficient permission.

Example:

```json
{
  "error": "Access denied - Insufficient permissions"
}
```

---

# 🐛 Security Debugging Experience

During JWT implementation, an important issue occurred with JSP forwarding.

The registration page was configured as public:

```text
/register
```

The controller returned:

```java
return "register";
```

Spring MVC then internally forwarded the request to:

```text
/WEB-INF/views/register.jsp
```

The JWT filter was also processing this internal JSP request.

The logs showed:

```text
REGISTER CONTROLLER CALLED
JWT FILTER: /WEB-INF/views/register.jsp
```

Because the JSP request did not contain a JWT, security rejected the request.

---

## Solution

The JWT filter was configured to skip internal JSP views:

```java
path.startsWith("/WEB-INF/views/")
```

This allowed the JSP to render without attempting JWT authentication.

---

# 🧠 permitAll() vs shouldNotFilter()

An important Spring Security concept learned during debugging is that:

```java
permitAll()
```

and:

```java
shouldNotFilter()
```

are different.

### permitAll()

Controls Spring Security authorization.

```java
.requestMatchers("/register", "/login")
.permitAll()
```

Meaning:

```text
Anyone can access these URLs.
```

### shouldNotFilter()

Controls whether the custom JWT filter executes.

```java
@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    ...
}
```

For example:

```java
path.startsWith("/WEB-INF/views/")
```

means:

```text
Do not execute JwtAuthenticationFilter
for internal JSP view requests.
```

---

# 🌐 Favicon Debugging

Another request noticed during debugging was:

```text
/favicon.ico
```

Browsers automatically request a favicon when loading a website.

The JWT filter was receiving:

```text
JWT FILTER: /favicon.ico
```

Since the favicon does not require authentication, it was excluded from JWT processing and permitted publicly:

```java
.requestMatchers("/favicon.ico")
.permitAll()
```

and:

```java
path.equals("/favicon.ico")
```

was added to `shouldNotFilter()`.

---

# 📊 HTTP Error Codes

The project distinguishes between different types of errors.

| Status | Meaning            | Example                                         |
| ------ | ------------------ | ----------------------------------------------- |
| 401    | Unauthenticated    | No valid JWT                                    |
| 403    | Forbidden          | USER accessing ADMIN resource                   |
| 404    | Resource not found | PG/Room/Booking does not exist                  |
| 500    | Server-side error  | Unexpected application/JSP processing exception |

---

# 🗄️ Database Design

PostgreSQL is used as the database.

The main tables are:

```text
PG
Room
Booking
Review
Users
```

---

# 📊 Entity Relationships

## PG → Room

One PG can have many rooms.

```text
PG 1 ──────────── * Room
```

The `Room` entity is the owning side:

```java
@ManyToOne
@JoinColumn(name = "pg_id")
private PG pg;
```

---

## Room → Booking

One room can have multiple booking records over time.

```text
Room 1 ──────────── * Booking
```

Each booking references the room that was booked.

---

## PG → Review

One PG can have multiple reviews.

```text
PG 1 ──────────── * Review
```

---

## User

Users are stored in the `users` table.

Important fields include:

```text
id
name
email
password
role
```

The email is unique.

The password is stored as a BCrypt hash.

---

# 🏗️ Architecture

The project follows a layered architecture.

```text
                     PGForYou
                        │
              ┌─────────┴─────────┐
              │                   │
          REST APIs            JSP UI
              │                   │
              ↓                   ↓
       REST Controllers      ViewController
              │                   │
              └─────────┬─────────┘
                        ↓
                     Service
                        ↓
                   Repository
                        ↓
                     JPA
                        ↓
                   Hibernate
                        ↓
                   PostgreSQL
```

Security is applied before protected controllers:

```text
Request
   ↓
Spring Security
   ↓
JwtAuthenticationFilter
   ↓
Authentication
   ↓
Authorization
   ↓
Controller
   ↓
Service
   ↓
Repository
```

---

# 📦 Project Layers

## Controller

Handles HTTP requests.

Examples:

```text
AuthController
ViewController
PGController
RoomController
BookingController
ReviewController
```

---

## Service

Contains business logic.

Examples:

```text
AuthService
PGService
RoomService
BookingService
ReviewService
UserService
JwtService
```

---

## Repository

Handles database operations using Spring Data JPA.

Examples:

```text
UserRepository
PGRepository
RoomRepository
BookingRepository
ReviewRepository
```

---

## Model

Contains JPA entities.

```text
User
PG
Room
Booking
Review
```

---

## DTO

DTOs are used to transfer data between the client and application.

Examples:

```text
RegisterRequestDTO
LoginRequestDTO
BookingRequestDTO
ReviewRequestDTO
PGRequestDTO
RoomRequestDTO
```

Response DTOs are used to return controlled data instead of exposing entities directly.

---

# 🌐 REST API Endpoints

## Authentication

### Register

```http
POST /auth/register
```

### Login

```http
POST /auth/login
```

---

## PG APIs

### Get all PGs

```http
GET /api/pgs
```

### Get PG by ID

```http
GET /api/pgs/{id}
```

### Add PG

```http
POST /api/pgs
```

### Update PG

```http
PUT /api/pgs/{id}
```

### Delete PG

```http
DELETE /api/pgs/{id}
```

### Search PG by location

```http
GET /api/pgs/search?location=Electronic%20City
```

---

## Room APIs

```http
GET /api/rooms
```

```http
GET /api/rooms/{id}
```

```http
GET /api/pgs/{pgId}/rooms
```

```http
POST /api/rooms
```

```http
PUT /api/rooms/{id}
```

```http
DELETE /api/rooms/{id}
```

---

## Booking APIs

```http
POST /api/bookings
```

```http
GET /api/bookings
```

```http
GET /api/bookings/{id}
```

```http
PUT /api/bookings/{id}/cancel
```

---

## Review APIs

```http
POST /api/pgs/{pgId}/reviews
```

```http
GET /api/pgs/{pgId}/reviews
```

---

# 🖥️ JSP Pages

The application uses JSP for the frontend.

```text
src/main/webapp
└── WEB-INF
    └── views
        ├── home.jsp
        ├── pg-details.jsp
        ├── booking.jsp
        ├── booking-success.jsp
        ├── bookings.jsp
        ├── review.jsp
        ├── register.jsp
        ├── login.jsp
        └── error.jsp
```

---

# 🎨 Frontend

The frontend uses:

* JSP
* JSTL
* HTML
* CSS

CSS:

```text
src/main/resources/static/css/style.css
```

Static images:

```text
src/main/resources/static/images/
├── pg/
└── rooms/
```

---

# 🔄 Complete Application Flow

```text
                     PGForYou
                         │
                         ↓
                       Login
                         │
                         ↓
                    Authentication
                         │
                         ↓
                    JWT Generated
                         │
                         ↓
                  JWT Cookie Stored
                         │
                         ↓
                       Home
                         │
              ┌──────────┴──────────┐
              ↓                     ↓
           Search               View PG
              │                     │
              └──────────┬──────────┘
                         ↓
                    PG Details
                         │
                  ┌──────┴──────┐
                  ↓             ↓
                Rooms         Reviews
                  │             │
               Book Now     Write Review
                  ↓
             Booking Form
                  ↓
          Booking Confirmation
                  ↓
           Booking History
                  ↓
           Cancel Booking
                  ↓
         Room Availability
              Restored
```

---

# 💼 Booking Transaction Management

Booking creation changes multiple pieces of data:

```text
Room availability
        +
Booking record
```

Therefore, the booking operation uses:

```java
@Transactional
```

Example:

```java
@Transactional
public BookingResponseDTO addBooking(
        BookingRequestDTO requestDTO) {

    Room room = roomRepository.findById(
            requestDTO.getRoomId()
    ).orElseThrow(...);

    if (room.getAvailableRooms() <= 0) {
        throw new RoomUnavailableException(
                "Room is not available"
        );
    }

    room.setAvailableRooms(
            room.getAvailableRooms() - 1
    );

    roomRepository.save(room);

    Booking savedBooking =
            bookingRepository.save(booking);

    return convertToResponseDTO(savedBooking);
}
```

This ensures that related database operations are handled as one transaction.

---

# ⚠️ Exception Handling

The project uses custom exceptions.

Examples:

```text
PGNotFoundException
RoomNotFoundException
BookingNotFoundException
RoomUnavailableException
```

A global exception handler handles application exceptions.

For REST APIs, structured JSON error responses are returned.

For JSP/MVC requests, errors are displayed using:

```text
error.jsp
```

---

# 🧪 Unit Testing

JUnit 5 and Mockito are used for unit testing.

Representative tests include:

```text
PGServiceTest
RoomServiceTest
BookingServiceTest
ReviewServiceTest
GlobalExceptionHandlerTest
```

Examples of tested functionality:

```text
PGService
    → Get PG by ID

RoomService
    → Get rooms by PG

BookingService
    → Add booking
    → Decrease room availability

ReviewService
    → Add review

GlobalExceptionHandler
    → Handle not-found exceptions
```

Mockito is used to mock repositories and isolate service-layer business logic.

---

# 🛠️ Technologies Used

## Backend

* Java
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* Jakarta Validation

## Authentication

* JWT
* JJWT
* BCryptPasswordEncoder
* DaoAuthenticationProvider
* AuthenticationManager

## Database

* PostgreSQL

## Frontend

* JSP
* JSTL
* HTML
* CSS

## Testing

* JUnit 5
* Mockito

## Build Tool

* Maven

## Version Control

* Git
* GitHub

---

# 📁 Project Structure

```text
PGForYou
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org.bridgelabz.pgforyou
│   │   │       │
│   │   │       ├── config
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── JwtAuthenticationFilter.java
│   │   │       │
│   │   │       ├── controller
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── ViewController.java
│   │   │       │   └── ...
│   │   │       │
│   │   │       ├── dto
│   │   │       │   ├── request
│   │   │       │   └── response
│   │   │       │
│   │   │       ├── exception
│   │   │       │
│   │   │       ├── model
│   │   │       │   ├── User.java
│   │   │       │   ├── PG.java
│   │   │       │   ├── Room.java
│   │   │       │   ├── Booking.java
│   │   │       │   └── Review.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │
│   │   │       └── service
│   │   │           ├── AuthService.java
│   │   │           ├── UserService.java
│   │   │           ├── JwtService.java
│   │   │           ├── PGService.java
│   │   │           ├── RoomService.java
│   │   │           ├── BookingService.java
│   │   │           └── ReviewService.java
│   │   │
│   │   ├── resources
│   │   │   ├── static
│   │   │   │   ├── css
│   │   │   │   │   └── style.css
│   │   │   │   └── images
│   │   │   │
│   │   │   └── application.properties
│   │   │
│   │   └── webapp
│   │       └── WEB-INF
│   │           └── views
│   │               ├── home.jsp
│   │               ├── pg-details.jsp
│   │               ├── booking.jsp
│   │               ├── booking-success.jsp
│   │               ├── bookings.jsp
│   │               ├── review.jsp
│   │               ├── register.jsp
│   │               ├── login.jsp
│   │               └── error.jsp
│   │
│   └── test
│       └── java
│           └── org.bridgelabz.pgforyou
│               ├── service
│               │   ├── PGServiceTest.java
│               │   ├── RoomServiceTest.java
│               │   ├── BookingServiceTest.java
│               │   └── ReviewServiceTest.java
│               │
│               └── exception
│                   └── GlobalExceptionHandlerTest.java
│
├── pom.xml
└── README.md
```

---

# ⚙️ Setup and Installation

## 1. Clone the repository

```bash
git clone <your-github-repository-url>
```

## 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

## 3. Configure PostgreSQL

Create a PostgreSQL database:

```sql
CREATE DATABASE pgforyou;
```

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pgforyou
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.web.encoding.charset=UTF-8
spring.web.encoding.enabled=true
spring.web.encoding.force=true
```

> For production, sensitive configuration such as database passwords and JWT secrets should be stored using environment variables or a secure configuration mechanism rather than committing them to Git.

---

## 4. Build the project

```bash
mvn clean package
```

---

## 5. Run the application

```bash
mvn spring-boot:run
```

Or run the Spring Boot application class from IntelliJ IDEA.

---

## 6. Open the application

```text
http://localhost:8080/
```

---

# 🔑 Authentication Flow Summary

```text
REGISTER
    ↓
Password encoded using BCrypt
    ↓
User saved to PostgreSQL


LOGIN
    ↓
AuthenticationManager
    ↓
DaoAuthenticationProvider
    ↓
UserService
    ↓
UserRepository
    ↓
Password verification
    ↓
JWT generated
    ↓
JWT stored in HttpOnly cookie


PROTECTED REQUEST
    ↓
JWT cookie
    ↓
JwtAuthenticationFilter
    ↓
Validate JWT
    ↓
Load UserDetails
    ↓
SecurityContext
    ↓
Authorization
    ↓
Controller
```

---

# 💡 Key Concepts Demonstrated

This project demonstrates practical usage of:

* Object-Oriented Programming
* Spring IoC and Dependency Injection
* Constructor Injection
* Spring MVC
* REST APIs
* Spring Security
* JWT Authentication
* HTTP-only Cookies
* Role-Based Authorization
* AuthenticationManager
* DaoAuthenticationProvider
* UserDetailsService
* BCrypt Password Encoding
* Spring Data JPA
* Hibernate ORM
* Entity Relationships
* DTO Pattern
* Repository Pattern
* Service Layer
* Jakarta Bean Validation
* Custom Exceptions
* Global Exception Handling
* Transaction Management
* JSP and JSTL
* PostgreSQL
* JUnit 5
* Mockito
* Maven
* Git/GitHub

---

# 🐞 Important Debugging Lessons

During development, several practical issues were identified and resolved.

### JSP Security Issue

```text
/register
   ↓
AuthController
   ↓
register.jsp
   ↓
/WEB-INF/views/register.jsp
   ↓
JWT Filter
   ↓
401
```

Solution:

```java
path.startsWith("/WEB-INF/views/")
```

was added to `shouldNotFilter()`.

---

### Favicon 401

The browser automatically requested:

```text
/favicon.ico
```

Solution:

```java
.requestMatchers("/favicon.ico")
.permitAll()
```

and excluded it from JWT filtering.

---

### HTTP Status Understanding

```text
401 → Authentication problem
403 → Authorization problem
404 → Resource not found
500 → Server/application error
```

---

# 🔮 Future Improvements

Possible future improvements include:

* Admin dashboard
* User-specific booking history
* PG owner management
* Online payments
* Image upload instead of image URLs
* Advanced search and filtering
* Pagination
* Sorting by rent/location
* Email or SMS booking notifications
* Refresh token mechanism
* Secure externalized JWT secret configuration

---

# 👨‍💻 Author

**Aryan Pujari**

Java Backend Developer

### Technologies

```text
Java | Spring Boot | Spring Security | JWT |
Spring MVC | JPA | Hibernate | PostgreSQL |
REST API | JSP | JUnit | Mockito | Maven
```

---

# ⭐ Project Summary

PGForYou is a real-world Spring Boot application designed around PG accommodation management.

It demonstrates a complete application architecture using:

```text
Spring Boot
      +
Spring MVC
      +
Spring Security
      +
JWT Authentication
      +
Spring Data JPA
      +
Hibernate
      +
PostgreSQL
      +
REST APIs
      +
JSP
      +
Exception Handling
      +
Unit Testing
```

The project focuses on clean separation of concerns, practical database relationships, transactional booking logic, secure authentication, role-based authorization, and a simple JSP-based user interface.

---

# 📌 Git Workflow

The project uses Git and GitHub for version control.

Typical feature development workflow:

```bash
git switch main

git pull origin main

git switch -c feature-name

# Make changes

git add .

git commit -m "Implement feature"

git switch main

git pull origin main

git merge feature-name

git push origin main
```

If uncommitted changes need to be temporarily stored:

```bash
git stash
```

Restore them using:

```bash
git stash pop
```

---

# ❤️ PGForYou

Built as a practical Java + Spring Boot project to learn backend development, REST APIs, database management, Spring Security, JWT authentication, and real-world application debugging.

```

**One correction I deliberately made from the old README:** authentication is now presented as an actual implemented feature, rather than a future improvement. Your current project now has a much stronger story: **CRUD + relationships + transactional booking + validation + exception handling + JWT security + role authorization + testing.** 🔥
```
