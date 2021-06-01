# spring-boot-data-dynamo
An example implementation for Spring Boot, Spring Data and DynamoDB running locally (in-memory) with a REST endpoint to interact with the database

## Setup Requirements

### DynamoDB binaries
As part of the application and tests a local in-memory instance of DynamoDB is initialised. If you want to run the application or tests in your IDE, there are binaries of the library `SQLite4Java` that must exist before tests are executed.

For these binaries to exist you must execute the maven command `mvn compile` which will place these binaries to the top-level folder `native-libs`. Further information and clarity on this can be found in this [article](https://www.baeldung.com/dynamodb-local-integration-tests).