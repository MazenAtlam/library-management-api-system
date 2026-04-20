# Library Management API System

## Overview

This is a RESTful API for managing a library system built with Spring Boot. The application allows managing authors, books, members, and borrowing records using an in-memory H2 database.

## Features

- **Author Management**: Create, read, update, and delete authors.
- **Book Management**: Manage books with details like title, ISBN, genre, and publication year. Each book is associated with an author.
- **Member Management**: Handle library members with search functionality.
- **Borrowing Records**: Track borrowing and returning of books by members.

## Technologies Used

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA** for data persistence
- **H2 Database** (in-memory for development)
- **Lombok** for reducing boilerplate code
- **MapStruct** for object mapping
- **Maven** for build management

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Installation and Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/MazenAtlam/library-management-api-system.git
   cd library-management-api-system
   ```

2. Build the project:

   ```bash
   ./mvnw clean package
   ```

3. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`.

## Database

The application uses an in-memory H2 database. Data is not persisted between restarts.

- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Username**: `sa`
- **Password**: (empty)

## N+1 Analysis

During the implementation of the `GET /api/books` and `GET /api/borrow-records/active` endpoints, we encountered the N+1 fetching problem due to the default `FetchType.LAZY` on our entity relationships. Retrieving a list of records triggered one initial SQL query, followed by an additional SQL `SELECT` query for every nested entity (like Author or Book) during MapStruct DTO mapping. To resolve this, we implemented custom JPQL queries in our repositories using `JOIN FETCH` clauses and `@EntityGraph` annotations, allowing Hibernate to retrieve the primary entities and their associated relationships in a single, highly efficient database query.

## API Endpoints

### Authors

- `GET /api/authors` - Get all authors (with pagination and sorting)
- `GET /api/authors/{id}` - Get a single author by ID
- `POST /api/authors` - Create a new author
- `PUT /api/authors/{id}` - Update an existing author
- `DELETE /api/authors/{id}` - Delete an author
- `GET /api/authors/{id}/books` - Get all books by a specific author

### Books

- `GET /api/books` - Get all books (with pagination and sorting)
- `GET /api/books/{id}` - Get a single book by ID (includes author details)
- `POST /api/books` - Create a new book (author must exist)
- `PUT /api/books/{id}` - Update a book
- `DELETE /api/books/{id}` - Delete a book
- `GET /api/books/search` - Search books by title, genre, and/or publishedYear (query params)

### Members

- `GET /api/members` - Get all members (with pagination)
- `GET /api/members/{id}` - Get a member by ID
- `GET /api/members/search` - Search for members by name (query param: name)
- `POST /api/members` - Register a new member (membershipDate auto-set)
- `PUT /api/members/{id}` - Update member information
- `DELETE /api/members/{id}` - Delete a member

### Borrowing Records

- `POST /api/borrow-records` - Borrow a book (creates record; error if already borrowed)
- `PUT /api/borrow-records/{id}/return` - Return a book (sets returnDate to today)
- `GET /api/borrow-records/member/{memberId}` - Get all borrow records for a specific member
- `GET /api/borrow-records/active` - Get all currently borrowed books (returnDate is null)

## Testing via Postman

This repository includes a comprehensive Postman collection to easily test all API endpoints, including happy paths, edge cases, and N+1 assessments.

**To use the collection:**

1. Open Postman.
2. Click on **Import** in the top left corner.
3. Select the `Postman_Test_Collection.json` file located in the root of this repository.
4. Run the endpoints sequentially to observe the API's behavior.

You can also run the automated tests via Maven:

```bash
./mvnw test
```

## Project Structure

```text
src/
├── main/
│   ├── java/com/example/library/
│   │   ├── controller/     # REST controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── entity/        # JPA entities
│   │   ├── exception/     # Custom exceptions and handlers
│   │   ├── mapper/        # MapStruct mappers
│   │   ├── repository/    # JPA repositories
│   │   └── service/       # Business logic services
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/library/
        └── LibraryApplicationTests.java
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.
