---
config:
  layout: dagre
---
classDiagram
direction TB
	namespace com.expensetracker.model {
        class BaseModel {
	        - id: long
	        + BaseModel()
	        + BaseModel(id: long)
	        + getId() : long
	        + setId(id: long) : void
        }
        class Transaction {
	        - account_id: long
	        - amount: long
	        - date: LocalDate
	        - description: String
	        # Transaction(account_id: long, amount: long, date: LocalDate, description: String)
	        # Transaction(id: long, account_id: long, amount: long, date: LocalDate, description: String)
	        + getAccountId() : long
	        + getAmount() : long
	        + getDate() : LocalDate
	        + getDescription() : String
	        + toString() : String
        }
        class Income {
	        + Income(account_id: long, amount: long, date: LocalDate, description: String)
	        + Income(id: long, account_id: long, amount: long, date: LocalDate, description: String)
        }
        class Expense {
	        + Expense(account_id: long, amount: long, date: LocalDate, description: String)
	        + Expense(id: long, account_id: long, amount: long, date: LocalDate, description: String)
        }
        class Account {
	        - userId: long
	        - name: String
	        - balance: long
	        + Account(userId: long, name: String, balance: long)
	        + Account(id: long, userId: long, name: String, balance: long)
	        + getUserId() : long
	        + setUserId(userId: long) : void
	        + getName() : String
	        + getBalance() : long
	        + setBalance(balance: long) : void
	        + toString() : String
        }
        class User {
	        - username: String
	        + User(username: String)
	        + User(id: long, username: String)
	        + getUsername() : String
        }
	}

	<<abstract>> BaseModel
	<<abstract>> Transaction

    Transaction --|> BaseModel : extends
    Income --|> Transaction : extends
    Expense --|> Transaction : extends
    Account --|> BaseModel : extends
    User --|> BaseModel : extends