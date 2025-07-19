# Expense Tracker CLI (Java 21)

This is a minimal command-line application written in Java 21 that connects to a PostgreSQL database and serves as the starting point for an expense-tracker project.

## Prerequisites

- Java 21 (OpenJDK)
- Maven 3.9+
- A running PostgreSQL instance with the following default credentials (override via environment variables):

```
POSTGRES_DB=buddybudget
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
HOST=localhost
PORT=5432
```

> **Note** Only **JUnit 5** is declared as a build dependency. You must make the PostgreSQL JDBC driver (`postgresql-jdbc.jar`) available on the runtime classpath yourself (e.g., copy it into `lib/` and use `java ‑cp`).

## Running the application

```
# From the project root
mvn package
java -cp "target/expense-tracker-app-1.0-SNAPSHOT.jar:lib/*" com.expensetracker.Main
```

The app will attempt to connect to Postgres and then prompt you for an operation (e.g., add expense, add income).

## Running tests

```
mvn test
```

## Environment Variables

| Variable            | Default     | Description   |
| ------------------- | ----------- | ------------- |
| `POSTGRES_DB`       | buddybudget | Database name |
| `POSTGRES_USER`     | postgres    | Database user |
| `POSTGRES_PASSWORD` | password    | User password |
| `HOST`              | localhost   | Database host |
| `PORT`              | 5432        | Database port |

## Project Structure

The directory layout follows the standard Maven convention:

```
src/
  main/java/          → application source
  test/java/          → JUnit tests
```

Additional Markdown documents:

- `PROJECT_STRUCTURE.md` – overview of the packages and layers
- `DESIGN_PATTERNS.md` – how/where each design pattern is used
- `TESTS.md` – testing strategy and TODOs
- `CODE.md` – code-level documentation / guidelines

---

Happy hacking 🚀
