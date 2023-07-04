## Introduction

This project is a backend application of built for Rova java developer assessment.

It helps to manage customer wallet and transaction.

## Setup

Make sure to follow all these steps exactly as explained below. Do not miss any steps or you won't be able to run this application.

### Start the Server with Docker
Run the following Docker commands in the project root directory to run the application
- `docker build -t transaction-service . `
-
then run

- `docker run -p 9090:9090 transaction-service `
  NB: you can bind to any other free port in case 9090 is already in use on your machine

### CREDENTIALS
- DB username is empty
- DB password is empty
- Basic auth username is 'RVA_rova_test'
- Basic auth password is '123456'

### Documentation
- Swagger doc = http://localhost:9090/swagger-ui/index.html
- Database tables = http://localhost:9090/h2-console/


### DATABASE
- DB = H2 in memory
- migration = Liquibase

### NB
ALL APIS ON THIS SERVICE REQUIRES JWT AUTH GENERATED ON ACCOUNT SERVICE

Some resource access require basic auth
`username=RVA_rova_test`
`password=123456`



