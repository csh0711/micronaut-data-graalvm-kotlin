# Micronaut app using Micronaut Data and GraalVM (written in Kotlin) 
Example of a simple [Micronaut](https://micronaut.io/) CRUD application using [Micronaut Data](https://micronaut-projects.github.io/micronaut-data/latest/guide/) and supporting [Oracle's GraalVM/SubstrateVM](https://www.graalvm.org/docs/reference-manual/native-image/)

### Building and executing the application:

Building the Fat-/Uber-Jar with Gradle:
```
./gradlew clean shadowJar
```

Executing the JAR via:
```
java -jar build/libs/footballmanager-0.1-all.jar
```

(Or you just start the `Application.kt` in your IDE).


Build native image with GraalVM/SubstrateVM
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
Oracle's GraalVM can be installed with `sdk install java 20.1.0.r11-grl`.

Furthermore the [GraalVM’s `native-image` tool](https://www.graalvm.org/docs/reference-manual/native-image/) must be installed with `gu install native-image`. 

#### Setup PostgreSQL in Docker
Execute the following steps:
```
docker pull postgres  

docker run -p 5432:5432 --name postgres-db -e POSTGRES_PASSWORD=mysecretpassword -d postgres

docker exec -it postgres-db bash
psql -U postgres
CREATE DATABASE footballerdb;
```