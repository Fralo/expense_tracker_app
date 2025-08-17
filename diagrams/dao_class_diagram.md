---
config:
  layout: elk
---
classDiagram
direction BT
	namespace com.expensetracker.dao {
        class UserDao {
	        <<interface>>
	        +save(user: User) : void
	        +findByUsername(username: String) : Optional
	        +findById(id: long) : Optional
	        +findAll() : List
        }
        class TransactionDao {
            <<interface>>
	        +save(transaction: Transaction) : void
	        +findAll() : List
	        +findAll(account: Account, types: ArrayList) : List
	        +findById(id: long) : Transaction
        }
        class AccountDao {
            <<interface>>
	        +save(user: Account) : void
	        +update(account: Account) : void
	        +findById(id: long) : Optional
	        +findAll(user: User) : List
        }
	}
	namespace com.expensetracker.dao.jdbc {
        class JdbcBaseDao {
	        <<abstract>>
	        ~ensureTable() : void
	        #ensureTableExists(ddl: String) : void
        }
        class JdbcUserDao {
	        ~ensureTable() : void
	        +save(user: User) : void
	        +findByUsername(username: String) : Optional
	        +findById(id: long) : Optional
	        +findAll() : List
        }
        class JdbcTransactionDao {
	        ~ensureTable() : void
	        +save(transaction: Transaction) : void
	        +findAll() : List
	        +findAll(account: Account, types: ArrayList) : List
	        +findById(id: long) : Transaction
        }
        class JdbcAccountDao {
	        ~ensureTable() : void
	        +save(account: Account) : void
	        +findAll(user: User) : List
	        +update(account: Account) : void
	        +findById(id: long) : Optional
        }
	}
    JdbcUserDao --|> JdbcBaseDao : extends
    JdbcTransactionDao --|> JdbcBaseDao : extends
    JdbcAccountDao --|> JdbcBaseDao : extends
    JdbcTransactionDao ..|> TransactionDao : implements
    JdbcAccountDao ..|> AccountDao : implements
    JdbcUserDao ..|> UserDao : implements
