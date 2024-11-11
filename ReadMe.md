# Analytical report on employees based on a CSV file with employee data


## General description

Company would like to analyze its organizational
structure and identify potential improvements. Board wants to make sure that every manager earns
at least 20% more than the average salary of its direct subordinates, but no more than 50% more
than that average. Company wants to avoid too long reporting lines, therefore we would like to
identify all employees which have more than 4 managers between them and the CEO.

Application reads the CSV file and reports:
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much

## Requirements for the contents of a CSV file

* The file must comply with the rules for filling in CSV files
* The data delimiter character is a comma
* The first line contains the field headers, the data is placed starting from the second line
* The data structure in the file must exactly match the following:
```
Id,firstName,lastName,salary,ManagerID
```
* Empty lines in the file are not allowed
* The ManagerID value of the CEO record should be empty, and there should be only one such record in the file
* A sample of a correctly filled file:
```
Id,firstName,lastName,salary,managerId 
123,Joe,Doe,60000, 
124,Martin,Chekov,45000,123 
125,Bob,Ronstad,47000,123 
300,Alice,Hasacat,50000,124 
305,Brett,Hardleaf,34000,300 
```


## Building the application from the source directory

```
mvn clean package
```

## Local launch of the report

```
java -jar EmployeesAnalyzerApp-1.0.0.jar <path to the CSV file>
```
* Path to the CSV file is required


## Application settings in the application.properties file

Using application.properties file the following settings can be changed:


The coefficient applied to the average salary of the manager's subordinates, 
which determines the minimum amount of his salary
```
app.minimal-salary-rate-limit = 1.2
```
The coefficient applied to the average salary of the manager's subordinates, 
which determines the maximum amount of his salary
```
app.maximal-salary-rate-limit = 1.5
```
The maximum number of managers between a subordinate and a CEO
```
app.maximal-reporting-line-length = 4
```


## Versions

* Java SE version 21
* JUnit version 5.11.3
