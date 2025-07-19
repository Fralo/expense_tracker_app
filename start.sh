#!/bin/bash

mvn package
java -cp "target/expense-tracker-app-1.0-SNAPSHOT.jar:lib/*" com.expensetracker.Main