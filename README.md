# Splitwise_Clone
This is a Spring Boot-based application that mimics the basic functionality of [Splitwise](https://www.splitwise.com). Users can register, create groups, add expenses, and track how much each person owes or is owed.

## Features

- Create, update, delete users
- Create groups and assign users to them
- Add expenses paid by one user and shared among a group of users
- Automatically calculate and store equal expense shares
- View expense shares for a given user or expense

## Tech stack

- Java 21
- Spring Boot
- Spring Data JPA
- H2 (in-memory DB) or PostgreSQL
- Maven
- REST API with JSON
- cURL or Postman for testing

## API Endpoints

### Users

- `POST /api/users` – Create user
- `GET /api/users` – List all users
- `GET /api/users/{id}` – Get user by ID
- `PUT /api/users/{id}` – Update user
- `DELETE /api/users/{id}` – Delete user

### Groups

- `POST /api/groups` – Create group with member IDs
- `GET /api/groups` – List all groups
- `GET /api/groups/{id}` – Get group by ID

### Expenses

- `POST /api/expenses` – Add an expense with payer and shared users
- `GET /api/expenses` – List all expenses
- `GET /api/expenses/{id}` – Get expense by ID

### Expense Shares

- `GET /api/expense-shares` – List all shares
- `GET /api/expense-shares/{userId}` – Get shares for a user

## Example: Creating Users with cURL

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com"}'
```

## Example: Creating a Group

```bash
curl -X POST http://localhost:8080/api/groups \
  -H "Content-Type: application/json" \
  -d '{"name": "Friends", "memberIds": [1, 2, 3]}'
```

## Example: Creating an Expense

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
        "amount": 150.0,
        "description": "Dinner",
        "date": "2025-09-22",
        "paidByUserId": 1,
        "groupId": 1,
        "sharedWithUserIds": [1,2,3]
      }'
```

## Running the Application

```bash
./mvnw spring-boot:run
```

Access API at `http://localhost:8080/api`

---

## Future Enhancements

- Authentication and Login
- Debt settlement logic
- User balances summary endpoint
- Frontend UI (React or Android)

## Author

Maitri Dodiya ✨

---

Happy coding! 💻✨


## 🚀 Setup Instructions

### Prerequisites

- Java 17 or above
- Maven
- PostgreSQL (or any other relational DB supported by Spring JPA)
- IDE like IntelliJ IDEA or VS Code (optional)
- cURL or Postman for testing the APIs

### Steps to Run

1. **Clone the repository**

```bash
git clone https://github.com/your-username/splitwise-clone.git
cd splitwise-clone
```

2. **Configure the Database**

Make sure your database is running and accessible. Update the `application.properties` or `application.yml` file under `src/main/resources/` with your DB credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/splitwise
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and Run the Application**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Test the API**

Use cURL or Postman to interact with the endpoints (e.g., `localhost:8080/api/users`, etc.)

---

## 🧱 Project Architecture

```
+-------------+       +----------------+       +------------------+
|  Controller | <---> |   Service      | <---> | Repository Layer |
+-------------+       +----------------+       +------------------+
                                                    |
                                                    v
                                                +--------+
                                                |  DB    |
                                                +--------+
```

### Key Layers:

- **Controller**: Handles API requests and responses.
- **Service**: Contains business logic.
- **Repository**: Interface to the database using Spring Data JPA.
- **Entity/DTO**: Models and data transfer objects.
