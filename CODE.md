# Code Guidelines & Conventions

- **Java Version**: 21 (records, switch expressions, sealed classes if needed)
- **Packaging**: `com.expensetracker.*`
- **Error Handling**: Prefer checked exceptions for recoverable DB errors; use `RuntimeException` subclasses for truly unexpected failures.
- **Immutability**: Domain models are immutable where possible (only getters, no setters).
- **Design Patterns**: One class per concept to avoid bloat; keep interfaces small and focused.
- **Testing**: Always accompany new logic with unit tests (JUnit 5) – mocking to be done with manual stubs (no extra libs).
