# Micronaut app using Micronaut Data and GraalVM (written in Kotlin) 
Sample of a simple [Micronaut](https://micronaut.io/) CRUD application using 
[Micronaut Data](https://micronaut-projects.github.io/micronaut-data/latest/guide/) 
and supporting [Oracle's GraalVM/SubstrateVM](https://www.graalvm.org/docs/reference-manual/native-image/).

+ [Scenario](#scenario)
+ [Setup](#setup)
+ [Building and executing the application](#building-and-executing-the-application)
+ [Further hints](#further-hints)
  - [Install GraalVM](#install-graalvm)
  - [Setup PostgreSQL with Docker](#setup-postgresql-with-docker)

### Scenario 
The example contains a _Footballmanager_ microservice which accesses a PostgreSQL database and provides the data via REST/JSON.

<img src="micronaut-data-kotlin-graalvm.png" alt="Scenario" width="600"/>


To create a new _Footballer_ a _POST_ request needs to be submitted to the REST endpoint `http://localhost:8080/footballers`:
```
{
  "firstName": "Toni",
  "lastName": "Kroos",
  "position": "Midfield"
}
```

All existing Footballers can be retrieved be sending a _GET_ request to `http://localhost:8080/footballers`. 

With the optional request parameter `position` all Footballers of a certain position are determined 
(`e.g. http://localhost:8080/footballers?position=Midfield`).

### Setup
The [PostgreSQL](https://www.postgresql.org/) should be running on `jdbc:postgresql://localhost:5432/footballerdb`.
See [below](#setup-postgresql-with-docker) how to setup a dockerized PostgreSQL.

### Building and executing the application

Building the Fat-/Uber-Jar with Gradle:
```
./gradlew clean shadowJar
```

Executing the JAR via:
```
java -jar build/libs/footballmanager-0.1-all.jar
```

(Or you just start the `Application.kt` in your IDE).


Build the native image with GraalVM/SubstrateVM:
```
native-image --no-server -cp build/libs/footballmanager-0.1-all.jar
```

Executing the native image via: 
```
./footballmanager 
```

### Further hints

#### Install GraalVM 
To manage SDK versions on your local machine [SDKMAN!](https://sdkman.io/jdks#Oracle) is a very helpful tool.
Oracle's GraalVM can be installed with `sdk install java 20.1.0.r11-grl` (or appropriate version).

Furthermore [GraalVM’s `native-image` tool](https://www.graalvm.org/docs/reference-manual/native-image/) must 
be installed with `gu install native-image`. 

#### Setup PostgreSQL with Docker
Execute the following steps:
```
docker pull postgres  

docker run -p 5432:5432 --name postgres-db -e POSTGRES_PASSWORD=mysecretpassword -d postgres

docker exec -it postgres-db bash
psql -U postgres
CREATE DATABASE footballerdb;
```
