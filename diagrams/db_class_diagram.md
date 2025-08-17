classDiagram
direction BT

namespace com.expensetracker.db {
    class DBConnection {
        -instance: Connection
        -databasePath: String
        - DBConnection()
         +initialize(path: String)$ : void
         +getInstance()$ : Connection
         -createConnection()$ : Connection
    }
}