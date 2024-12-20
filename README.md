# Book Management System

This project is a backend service for managing books, authors, genres, librarians, and library members. It supports robust search and filtering functionalities along with user authentication and authorization.


## Environment Variables:

- Sensitive data like database credentials, JWT keys, and other security configurations are stored in a `.env` file. Make sure to create a `.env` file in the root directory with the structure like `.env.example`


## Attention:
- Bearer token is required in the Authorization header for all requests.
  - Example: `Authorization: Bearer <token>` in IntelliJ IDEA `.http` file


## Features

- **User Authentication**: Login and refresh tokens for secure access.
- **Book Management**: Add, update, search, filter, and delete books.
- **Author Management**: Manage authors with CRUD operations.
- **Genre Management**: Add, update, retrieve, and delete genres.
- **Librarian Management**: Manage librarian accounts, including password changes and deletion.
- **Member Management**: Add new members to the system.
- **Loan Management**: Issue loans to members.
- **Dynamic Filtering**: Search and filter books by multiple criteria such as title, author, genre, and availability status.

## Technologies Used

- **Java 21**: The latest version of Java for modern features.
- **Spring Boot**: Framework for building the backend service.
- **Spring Data JPA**: Simplifies database interaction using JPA.
- **Hibernate**: ORM framework for mapping Java objects to database tables.
- **JWT (JSON Web Token)**: Ensures secure access through token-based authentication.

## API Endpoints

### Authentication

- **Login**: `POST /api/v1/auth/login`

    - Authenticates a user and returns an access token.

  Example request:

  ```json
  {
    "login": "admin",
    "password": "123123"
  }
  ```

- **Refresh Token**: `POST /api/v1/auth/refresh`

    - Generates a new access token using a refresh token.

  Example request:

  ```json
  {
    "refreshToken": "<your_refresh_token>"
  }
  ```

### Author Management

- **Create a New Author**: `POST /api/v1/authors`

    - Adds a new author to the system.

  Example request:

  ```json
  {
    "firstName": "James",
    "lastName": "Clear"
  }
  ```

- **Update an Author**: `PATCH /api/v1/authors/{id}`

    - Updates the details of an existing author.

  Example request:

  ```json
  {
    "firstName": "Victor",
    "lastName": "Hugo"
  }
  ```

- **Retrieve All Authors**: `GET /api/v1/authors`

    - Fetches a list of all authors.

- **Retrieve an Author by ID**: `GET /api/v1/authors/{id}`

    - Fetches the details of a specific author.

- **Delete an Author**: `DELETE /api/v1/authors/{id}`

    - Deletes an author from the system.

### Book Management

- **Add a New Book**: `POST /api/v1/books`

    - Adds a new book to the system.

  Example request:

  ```json
  {
    "title": "War and Peace",
    "authorId": 1,
    "genreIdList": [1, 2],
    "count": 10
  }
  ```

- **Update a Book**: `PUT /api/v1/books/{id}`

    - Updates the details of an existing book.

  Example request:

  ```json
  {
    "title": "Harry Potter",
    "authorId": 2,
    "genreIdList": [3],
    "count": 50
  }
  ```

- **Retrieve All Books**: `GET /api/v1/books`

    - Fetches a list of all books.

- **Retrieve a Book by ID**: `GET /api/v1/books/{id}`

    - Fetches the details of a specific book.

- **Search Books**: `GET /api/v1/books/search`

    - Allows searching books by title and/or author name.

  Example query:

  ```
  ?title=War&author=Tolstoy
  ```

- **Filter Books**: `GET /api/v1/books/filter`

    - Filters books by criteria like genre, availability, and author name.

  Example request:

  ```json
  {
    "genreIds": [1],
    "availability": "IN_STOCK",
    "authorName": "Tolstoy"
  }
  ```

- **Delete a Book**: `DELETE /api/v1/books/{id}`

    - Removes a book from the system.

### Genre Management

- **Add a New Genre**: `POST /api/v1/genres`

    - Adds a new genre to the system.

  Example request:

  ```json
  {
    "title": "Fiction"
  }
  ```

- **Update a Genre**: `PATCH /api/v1/genres/{id}`

    - Updates an existing genre.

- **Retrieve All Genres**: `GET /api/v1/genres`

    - Fetches a list of all genres.

- **Retrieve a Genre by ID**: `GET /api/v1/genres/{id}`

    - Fetches the details of a specific genre.

- **Delete a Genre**: `DELETE /api/v1/genres/{id}`

    - Removes a genre from the system.

### Librarian Management

- **Create a New Librarian**: `POST /api/v1/librarians`

    - Adds a new librarian account.

  Example request:

  ```json
  {
    "username": "librarian1",
    "password": "password123",
    "workTime": "FULL_DAY"
  }
  ```

- **Retrieve All Librarians**: `GET /api/v1/librarians`

    - Fetches all librarians.

- **Retrieve a Librarian by ID**: `GET /api/v1/librarians/{id}`

    - Fetches details of a specific librarian.

- **Update Password**: `PATCH /api/v1/librarians/me/password`

    - Allows a librarian to update their password.
    - If librarian ID is provided we check for admin

  Example request:
    ```json
    {
      
  "oldPassword": "2",
  "newPassword": "3",
  "confirmPassword": "3"

    }
    ```

- **Delete a Librarian by ID**: `DELETE /api/v1/librarians/{librarian_ID}`
  - Deletes a specific library member by ID.
  - Only ADMIN can do that
  

### Member Management

- **Add a New Member**: `POST /api/v1/members`
    - Registers a new library member.

  Example request:
    ```json
    {
      "name": "<name>",
      "phone": "<p_n>",
      "email": "<email>",
      "membershipDate": "yyyy-mm-dd"
    }
    ```

- **Update Member by ID**: `PUT /api/v1/members/{member_id}`
    - Updates the details of an existing library member.

  Example request:
    ```json
    {
      "name": "<name>",
      "phone": "<p_n>",
      "email": "<email>",
      "membershipDate": "yyyy-mm-dd"
    }
    ```

- **Get All Members**: `GET /api/v1/members`
    - Retrieves a list of all library members.

- **Get a Member by ID**: `GET /api/v1/members/{member_ID}`
    - Retrieves a specific library member's details by ID.

- **Delete a Member by ID**: `DELETE /api/v1/members/{member_ID}`
    - Deletes a specific library member by ID.

### Loan Management

- **Issue a Loan**: `POST /api/v1/loans/issue`
    - Issues a book loan to a library member.

  Example request:
    ```json
    {
      "memberId": 4,
      "bookId": 1,
      "issueDate": "yyyy-mm-dd",
      "dueDate": "yyyy-mm-dd"
    }
    ```

- **Return a Book**: `POST /api/v1/loans/{loan_ID}/return`
    - Marks a book as returned for a specific loan.

- **Get All Loans**: `GET /api/v1/loans`
    - Retrieves a list of all book loans.

- **Get a Loan by ID**: `GET /api/v1/loans/{loan_ID}`
    - Retrieves the details of a specific loan by ID.



## How to Run

1. Clone the repository.
2. Set up the database connection in `.env`.
3. Run the application using your preferred IDE or build tool.

## License

This project is licensed under the MIT License.

