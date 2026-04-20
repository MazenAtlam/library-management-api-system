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



   ```

   git clone <repository-url>

   cd library_management

   ```



2. Build the project:



   ```

   ./mvnw clean package

   ```



3. Run the application:

   ```

   ./mvnw spring-boot:run

   ```



The application will start on `http://localhost:8080`.



## API Endpoints



### Authors



- `GET /api/authors` - Get all authors

- `GET /api/authors/{id}` - Get author by ID

- `POST /api/authors` - Create a new author

- `PUT /api/authors/{id}` - Update an author

- `DELETE /api/authors/{id}` - Delete an author



### Books



- `GET /api/books` - Get all books (paginated)

- `GET /api/books/{id}` - Get book by ID

- `POST /api/books` - Create a new book

- `PUT /api/books/{id}` - Update a book

- `DELETE /api/books/{id}` - Delete a book



### Members



- `GET /api/members` - Get all members (paginated)

- `GET /api/members/{id}` - Get member by ID

- `GET /api/members/search?name={query}` - Search members by name

- `POST /api/members` - Create a new member

- `PUT /api/members/{id}` - Update a member

- `DELETE /api/members/{id}` - Delete a member



### Borrowing Records



(Note: Specific endpoints for borrowing may be implemented in services or future updates.)



## Database



The application uses an in-memory H2 database. Data is not persisted between restarts.



- H2 Console: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:librarydb`

- Username: `sa`

- Password: (empty)



## Testing



Run tests with:



```

./mvnw test

```



## Project Structure



```

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



   ```

   git clone <repository-url>

   cd library_management

   ```



2. Build the project:



   ```

   ./mvnw clean package

   ```



3. Run the application:

   ```

   ./mvnw spring-boot:run

   ```



The application will start on `http://localhost:8080`.



## API Endpoints



### Authors



- `GET /api/authors` - Get all authors

- `GET /api/authors/{id}` - Get author by ID

- `POST /api/authors` - Create a new author

- `PUT /api/authors/{id}` - Update an author

- `DELETE /api/authors/{id}` - Delete an author



### Books



- `GET /api/books` - Get all books (paginated)

- `GET /api/books/{id}` - Get book by ID

- `POST /api/books` - Create a new book

- `PUT /api/books/{id}` - Update a book

- `DELETE /api/books/{id}` - Delete a book



### Members



- `GET /api/members` - Get all members (paginated)

- `GET /api/members/{id}` - Get member by ID

- `GET /api/members/search?name={query}` - Search members by name

- `POST /api/members` - Create a new member

- `PUT /api/members/{id}` - Update a member

- `DELETE /api/members/{id}` - Delete a member



### Borrowing Records



(Note: Specific endpoints for borrowing may be implemented in services or future updates.)



## Database



The application uses an in-memory H2 database. Data is not persisted between restarts.



- H2 Console: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:librarydb`

- Username: `sa`

- Password: (empty)



## Testing



Run tests with:



```

./mvnw test

```



## Project Structure



```

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
