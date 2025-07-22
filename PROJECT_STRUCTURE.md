# Project Structure

```
expense tracker app/
├── pom.xml                  # Maven build file
├── README.md                # How to build & run
├── PROJECT_STRUCTURE.md     # <— this file
├── TESTS.md                 # Testing approach
├── CODE.md                  # Coding guidelines & decisions
└── src/
    ├── main/
    │   └── java/
    │       └── com/expensetracker/
    │           ├── Main.java               # CLI entry point
    │           ├── db/DBConnection.java    # JDBC singleton
    │           ├── model/…                # Domain classes
    │           ├── dao/…                  # DAO layer
    └── test/
        └── java/
            └── com/expensetracker/…       # JUnit tests
```

The project is intentionally modular so that each design pattern can be incorporated in isolation without cross-coupling concerns.
