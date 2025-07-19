# Design Patterns Usage

| Pattern   | Current Location                              | Responsibility / Rationale                              |
| --------- | --------------------------------------------- | ------------------------------------------------------- |
| Composite | `model/Category.java`                         | Allows hierarchical categories & uniform traversal      |
| Observer  | `observer/BalanceObserver` / `BalanceSubject` | Keep UI/CLI or summaries updated when balance changes   |
| Adapter   | _(planned)_ `adapter/CommandAdapter`          | Turn raw CLI commands into service-layer calls          |
| Decorator | _(planned)_ `dao/logging`                     | Wrap DAOs to add logging/metrics without modifying them |
| Strategy  | _(planned)_ `auth/strategy`                   | Plug-in authentication strategies (e.g., hash vs plain) |

> **Planned** entries indicate the scaffolding will be introduced in later iterations.
