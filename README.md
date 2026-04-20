Markdown
# Library Management API System

## Overview
This is a RESTful API for managing a library system built with Spring Boot. The application allows managing authors, books, members, and borrowing records using an in-memory H2 database. It features a clean layered architecture, dedicated DTOs mapped via MapStruct, and structured exception handling.

## Technologies Used
- **Java 21**
- **Spring Boot 3.x/4.x**
- **Spring Data JPA** for data persistence
- **H2 Database** (in-memory for development)
- **Lombok** for reducing boilerplate code
- **MapStruct** for compile-time object mapping
- **Maven** for build management

## Prerequisites
- Java 21 or higher
- Maven 3.6+

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd library_management
Build the project:

Bash
./mvnw clean package
Run the application:

Bash
./mvnw spring-boot:run
The application will start on http://localhost:8080.

N+1 Analysis
[PLACEHOLDER: Briefly explain your N+1 issue here. Example: In this project, the GET /api/books endpoint initially suffered from the N+1 select problem because fetching all books lazily triggered an additional query to fetch the associated Author for each book. To resolve this, I implemented a @EntityGraph (or JOIN FETCH) in the BookRepository to eagerly fetch the author details in a single query when retrieving the paginated list of books.]

Database Access
The application uses an in-memory H2 database. Data is not persisted between restarts.

H2 Console URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:librarydb (or check your application.properties)

Username: sa

Password: (leave empty)

API Endpoints
Authors
GET /api/authors - Get all authors (paginated)

GET /api/authors/{id} - Get author by ID

POST /api/authors - Create a new author

PUT /api/authors/{id} - Update an author

DELETE /api/authors/{id} - Delete an author

GET /api/authors/{id}/books - Get all books by a specific author

Books
GET /api/books - Get all books (paginated)

GET /api/books/{id} - Get book by ID (includes author details)

POST /api/books - Create a new book

PUT /api/books/{id} - Update a book

DELETE /api/books/{id} - Delete a book

GET /api/books/search?title={t}&genre={g}&publishedYear={y} - Search books

Members
GET /api/members - Get all members (paginated)

GET /api/members/{id} - Get member by ID

GET /api/members/search?name={query} - Search members by name

POST /api/members - Create a new member

PUT /api/members/{id} - Update a member

DELETE /api/members/{id} - Delete a member

Borrowing Records
POST /api/borrow-records - Borrow a book

PUT /api/borrow-records/{id}/return - Return a book

GET /api/borrow-records/member/{memberId} - Get all borrow records for a specific member

GET /api/borrow-records/active - Get all currently borrowed books

API Testing (Sample cURL Commands)
Here are sample commands to test the core functionality of the API:

Create an Author:

Bash
curl -X POST http://localhost:8080/api/authors \
-H "Content-Type: application/json" \
-d '{"firstName":"George","lastName":"Orwell"}'
Create a Book:

Bash
curl -X POST http://localhost:8080/api/books \
-H "Content-Type: application/json" \
-d '{"title":"1984","isbn":"9780451524935","genre":"Dystopian","publishedYear":1949,"authorId":1}'
Register a Member:

Bash
curl -X POST http://localhost:8080/api/members \
-H "Content-Type: application/json" \
-d '{"firstName":"Jane","lastName":"Doe","email":"jane.doe@example.com"}'
Borrow a Book:

Bash
curl -X POST http://localhost:8080/api/borrow-records \
-H "Content-Type: application/json" \
-d '{"bookId":1,"memberId":1}'
Project Structure
Plaintext
src/
├── main/
│   ├── java/com/example/library/
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Request/Response DTOs
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Custom exceptions & global handler
│   │   ├── mapper/          # MapStruct interfaces
│   │   ├── repository/      # Spring Data JPA repositories
│   │   └── service/         # Business logic
│   └── resources/
│       └── application.properties
└── test/

*(Don't forget to create that `groups` file in your repository with your group number and member names before you submit!)*
