# Design Patterns

This document outlines the design patterns used in the Expense Tracker application.

## Implemented Design Patterns

### Data Access Object (DAO)

The DAO pattern is used to abstract and encapsulate all access to the data source. The DAO manages the connection with the data source to obtain and store data.

- **`AccountDao`**, **`CategoryDao`**, **`TransactionDao`**, **`UserDao`**: These interfaces define the standard operations to be performed on the model objects.
- **`JdbcAccountDao`**, **`JdbcCategoryDao`**, **`JdbcTransactionDao`**, **`JdbcUserDao`**: These classes are the concrete implementations of the DAO interfaces. They handle the specifics of interacting with the JDBC data source.

### Singleton

The Singleton pattern ensures that a class has only one instance and provides a global point of access to it.

- **`DBConnection`**: This class is implemented as a Singleton to ensure that there is only one database connection throughout the application, which is a common practice to manage database resources efficiently.
- **`InputReader`**: This class is implemented as a Singleton to ensure all the parts of the app that need to read an input from the command line will use the same `Scanner` object.

### Factory Method

The Factory Method pattern provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.

- **`AmountConverterFactory`**: This class provides a factory method to create different types of amount converters (`DecimalAmountConverter`). This allows the application to easily support different number formats in the future.

### Model-View-Controller (MVC)

The MVC pattern is an architectural pattern that separates an application into three main logical components: the model, the view, and the controller.

- **Model**: Manages the application's data and business logic. In our application, the `model` package contains the model classes (`User`, `Account`, `Transaction`, etc.).
- **View**: Displays the data to the user. The `view` package contains the `AppView` interface and the `CliView` implementation. The `CliView` is responsible for all command-line input and output.
- **Controller**: Handles user input and updates the model. The `controllers` package contains the `MainController`, `UserController`, `AccountController`, and `TransactionController`. The `MainController` is the main controller of the application, and it coordinates the other controllers.

## Potential Design Patterns to Implement

### Observer

The Observer pattern is a behavioral pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they’re observing.

- **Use Case**: This could be used to update the UI whenever the data in the model changes. For example, when a new expense is added, the account balance should be updated in the view. The `Account` model would be the "subject" and the UI components would be the "observers".

### Strategy

The Strategy pattern is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

- **Use Case**: You could use this pattern to implement different ways of calculating financial reports. For instance, you could have different strategies for generating weekly, monthly, or yearly reports. Each strategy would be a separate class, and you could switch between them at runtime.

### Command

The Command pattern is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. This transformation lets you pass requests as a method arguments, delay or queue a request’s execution, and support undoable operations.

- **Use Case**: This could be used to encapsulate actions like "add expense", "create account", etc. Each action would be a command object. This would make it easier to implement features like an undo/redo stack. It would also help to decouple the user interface from the business logic.
