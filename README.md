Yep bro 👍 You want the **actual `README.md` file content**, not wrapped in an explanation.

Copy everything below directly into `README.md`:

````markdown
# 🏠 PGForYou

PGForYou is a simple web-based PG (Paying Guest) accommodation management and discovery application.

The application allows users to browse available PGs, search PGs by location, view rooms and their availability, book rooms, view booking history, cancel bookings, and submit reviews.

The project was built to practice and demonstrate Java, Spring Boot, Spring MVC, Spring Data JPA, PostgreSQL, REST APIs, JSP, DTOs, exception handling, validation, and unit testing.

---

## 🎯 Project Objective

The main objective of PGForYou is to build a simple real-world application while applying backend development concepts.

The application focuses on:

- PG listing and searching
- Room management
- Room availability
- Booking management
- Booking cancellation
- Booking history
- Reviews
- REST APIs
- JSP-based frontend
- Exception handling
- DTO-based data transfer
- JPA entity relationships
- Unit testing

The application intentionally does not include authentication, authorization, or an admin module to keep the project simple and focused.

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
└── Triple Sharing
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

Users can view all bookings through the booking history page.

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

## 7. Image Support

PGs and rooms support images using image URLs.

```text
PG Image
    ↓
PG Details

Room Image
    ↓
Room Details
```

Images are displayed through JSP and CSS.

---

## 8. Search

Users can search PGs by location.

Example:

```text
Search:
Electronic City
```

The application returns PGs matching the location.

---

## 9. Validation

Request DTOs are used for receiving input data.

Validation is applied using Jakarta Validation.

Examples:

```java
@NotBlank
@Email
@Positive
@PositiveOrZero
```

Invalid input is handled through the application's exception-handling mechanism.

---

# 🏗️ Architecture

The project follows a layered architecture.

```text
                    PGForYou
                       │
              ┌────────┴────────┐
              │                 │
          REST API           JSP UI
              │                 │
              ↓                 ↓
        REST Controller    ViewController
              │                 │
              └────────┬────────┘
                       ↓
                    Service
                       ↓
                  Repository
                       ↓
                    JPA
                       ↓
                  PostgreSQL
```

### Layers

### Controller

Handles HTTP requests and responses.

```text
REST Controller
ViewController
```

### Service

Contains business logic.

```text
PGService
RoomService
BookingService
ReviewService
```

### Repository

Handles database operations using Spring Data JPA.

```text
PGRepository
RoomRepository
BookingRepository
ReviewRepository
```

### Model

Contains JPA entities.

```text
PG
Room
Booking
Review
```

### DTO

Used to transfer data between layers and avoid exposing entities directly.

```text
Request DTO
Response DTO
ErrorResponseDTO
```

---

# 🗄️ Database Design

PostgreSQL is used as the database.

The project contains four main tables:

```text
PG
Room
Booking
Review
```

---

## PG → Room

One PG can have many rooms.

```text
PG 1 ──────────── * Room
```

The `Room` entity contains the foreign key:

```java
@ManyToOne
@JoinColumn(name = "pg_id")
private PG pg;
```

The `Room` entity is the owning side of this relationship.

---

## Room → Booking

One room can have multiple booking records over time.

```text
Room 1 ──────────── * Booking
```

A booking references the room that was booked.

---

## PG → Review

One PG can have multiple reviews.

```text
PG 1 ──────────── * Review
```

Each review belongs to one PG.

---

# 📊 Database Relationship

```text
                 ┌──────────────┐
                 │      PG      │
                 │--------------│
                 │ id           │
                 │ name         │
                 │ location     │
                 │ rent         │
                 │ imageUrl     │
                 └──────┬───────┘
                        │
                     1  │
                        │
                     *  │
                 ┌──────▼───────┐
                 │     Room     │
                 │--------------│
                 │ id           │
                 │ type         │
                 │ rent         │
                 │ totalRooms   │
                 │ available... │
                 │ imageUrl     │
                 │ pg_id        │
                 └──────┬───────┘
                        │
                     1  │
                        │
                     *  │
                 ┌──────▼───────┐
                 │   Booking    │
                 │--------------│
                 │ id           │
                 │ name         │
                 │ phone        │
                 │ bookingDate  │
                 │ status       │
                 │ room_id      │
                 └──────────────┘


                 ┌──────────────┐
                 │    Review    │
                 │--------------│
                 │ id           │
                 │ name         │
                 │ rating       │
                 │ comment      │
                 │ pg_id        │
                 └──────────────┘

                       *
                       │
                       │
                       1
                       │
                      PG
```

---

# 🌐 REST API Endpoints

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
        └── error.jsp
```

### Home Page

Displays:

* PG cards
* PG images
* Location
* Rent
* Search
* View PG button

### PG Details

Displays:

* PG information
* PG image
* Rooms
* Room images
* Room availability
* Booking button
* Reviews

### Booking Page

Allows users to enter:

* Name
* Phone

and submit a booking.

### Booking History

Displays existing bookings and allows confirmed bookings to be cancelled.

### Review Page

Allows users to submit:

* Name
* Rating
* Comment

### Error Page

Displays user-friendly error messages instead of the default Whitelabel Error Page.

---

# 🎨 Frontend

The frontend uses:

* JSP
* HTML
* CSS
* JSTL

CSS is stored in:

```text
src/main/resources/static/css/style.css
```

The UI contains:

* PG cards
* Room cards
* Search form
* Booking forms
* Review cards
* Responsive layout

---

# ⚠️ Exception Handling

The project uses custom exceptions for application-specific errors.

Examples:

```text
PGNotFoundException
RoomNotFoundException
BookingNotFoundException
RoomUnavailableException
```

For REST APIs, a global exception handler returns structured JSON responses.

Example:

```json
{
    "status": 404,
    "message": "PG not found with id 99"
}
```

For JSP/MVC requests, a `@ControllerAdvice` handles exceptions and returns:

```text
error.jsp
```

This prevents users from seeing the default Whitelabel Error Page.

---

# 🧪 Unit Testing

JUnit 5 and Mockito are used for unit testing.

The project contains representative tests for the major services.

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
    → Add booking and decrease availability

ReviewService
    → Add review

GlobalExceptionHandler
    → Handle PG not found exception
```

Mockito is used to mock repositories and isolate service-layer business logic.

---

# 🛠️ Technologies Used

## Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Jakarta Validation

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
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       │   ├── request
│   │   │       │   └── response
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       └── service
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

Update the database configuration in:

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

## 4. Build the project

```bash
mvn clean package
```

## 5. Run the application

```bash
mvn spring-boot:run
```

Or run the Spring Boot application class from the IDE.

## 6. Open the application

```text
http://localhost:8080/
```

---

# 🔄 Application Flow

The complete user flow is:

```text
                    PGForYou
                       │
                       ↓
                     Home
                       │
             ┌─────────┴─────────┐
             ↓                   ↓
          Search              View PG
             │                   │
             └─────────┬─────────┘
                       ↓
                  PG Details
                       │
              ┌────────┴────────┐
              ↓                 ↓
            Rooms            Reviews
              │                 │
           Book Now        Write Review
              ↓
        Booking Form
              ↓
       Booking Confirmed
              │
              ↓
       Booking History
              │
              ↓
       Cancel Booking
              │
              ↓
       Room Availability
           Restored
```

---

# 💡 Key Concepts Demonstrated

This project demonstrates practical usage of:

* Object-Oriented Programming
* Spring IoC and Dependency Injection
* Constructor Injection
* Spring MVC
* REST APIs
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
* JUnit
* Mockito
* Maven
* Git/GitHub

---

# 📌 Future Improvements

The current project intentionally keeps the scope simple.

Possible future improvements include:

* User authentication and authorization
* Admin dashboard
* User-specific booking history
* PG owner management
* Online payments
* Image upload instead of image URLs
* Advanced search and filtering
* Pagination for large PG datasets
* Sorting by rent/location
* Email or SMS booking notifications

---

# 👨‍💻 Author

**Aryan Pujari**

Java Backend Developer

### Technologies

```text
Java | Spring Boot | Spring MVC | JPA | Hibernate |
PostgreSQL | REST API | JSP | JUnit | Mockito | Maven
```

---

# ⭐ Project Summary

PGForYou is a beginner-to-intermediate Spring Boot project designed around a real-world PG accommodation use case.

It demonstrates how a complete application can be developed using a layered architecture with:

```text
Spring Boot
     +
Spring MVC
     +
Spring Data JPA
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

The project focuses on clean separation of concerns, practical database relationships, transactional booking logic, and a simple JSP-based user interface.

```
```
