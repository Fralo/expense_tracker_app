classDiagram
direction TB

class MainController {
  - CommonView commonView
  - MainView mainView
  - UserController userController
  - AccountController accountController
  + MainController()
  + void start()
}

class UserController {
  - UserService userService
  - UserView userView
  - CommonView commonView
  + UserController(UserView, CommonView)
  + User handleUserSelection()
}

class AccountController {
  - AccountService accountService
  - AccountView accountView
  - CommonView commonView
  - TransactionController transactionController
  + AccountController(AccountView, CommonView, TransactionController, AccountService)
  + void handleAccountManagement()
  - void handleAccountMenu(Account)
  - long getInitialBalance()
  + void handleCreateAccount()
}

class TransactionController {
  - TransactionService transactionService
  - TransactionView transactionView
  - CommonView commonView
  - AmountConverter amountConverter
  + TransactionController(TransactionView, CommonView, TransactionService)
  + long getAmount()
  + void handleAddExpense(Account)
  + void handleAddIncome(Account)
  + void handleViewTransactions(Account)
}



MainController --> UserController : uses
MainController --> AccountController : uses
AccountController --> TransactionController : uses







MainController ..> TransactionController

