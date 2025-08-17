#!/bin/bash

mvn clean package
java -Ddb.file="expense_tracker.db" -cp "target/expense-tracker-app-1.0-SNAPSHOT.jar:target/lib/*" com.expensetracker.Main