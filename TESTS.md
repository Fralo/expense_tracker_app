# Testing Strategy

- **Framework**: JUnit 5 (Jupiter)
- **Scope**: Currently only connection test (`DBConnectionTest`) is provided. Further tests will cover:
  - DAO implementations (CRUD semantics)
  - Repository & Service methods (business rules)
  - Decorators / Adapters functionality
  - Category composite operations
  - Observer notifications

To execute all tests:

```
mvn test
```
