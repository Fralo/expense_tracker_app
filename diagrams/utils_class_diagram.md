---
config:
  layout: dagre
---
classDiagram
direction TB

    namespace com.expensetracker.utils {
        class InputReader {
            - instance: InputReader
            - scanner: Scanner
            - InputReader()
            + getInstance() : InputReader
            + readLine() : String
            + readInput(prompt: String) : String
            + readInput(prompt: String, defaultValue: String) : String
            + closeScanner() : void
        }

        class AppInstance {
            - instance: AppInstance
            - currentUser: User
            - currentAccount: Account
            - AppInstance()
            + getInstance() : AppInstance
            + getCurrentUser() : User
            + setCurrentUser(user: User) : void
            + clearCurrentUser() : void
            + getCurrentAccount() : Account
            + setCurrentAccount(account: Account) : void
        }
    }

    namespace com.expensetracker.utils.converters {
        class AmountConverter {
            <<interface>>
            + convertToCents(amount: String) : long
            + convertFromCents(cents: long) : String
        }

        class DecimalAmountConverter {
            + convertToCents(amount: String) : long
            + convertFromCents(cents: long) : String
        }

        class AmountConverterFactory {
            + getConverter(type: String) : AmountConverter
            + getDefaultConverter() : AmountConverter
        }
    }

    DecimalAmountConverter ..|> AmountConverter : implements
