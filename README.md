# jpa-mappping

A project to give worked examples for different Hibernate Relationships 
built in Quarkus and postgreSQL. Intended as a reference for people like myself
who can never remember the details... 

The domain models chosen here are kept as simple as possible 
to focus on demonstrating the entity relationships.

Beyond a few minimalistic HTTP endpoints, only a minimum of supporting software is implemented. No hex architecture,
no automatic object mapping, no error handling, a rudimentary API, no OpenAPI spec, etc.

## @OneToMany 
### Domain Model
In the conceptual design, i.e. the mind of the implementer,
a domain model should be present in the form shown here or 
in any other sufficient form.

![Domain Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/domain-model.class.puml)

This essentially expresses, that a Post may have none, one or many
Comments, and that each Comment must be owned by a single post.

### Unidirectional

When interpreting above domain model as unidirectional, we
decide that a Comment (the child) does not need to know anything about the
Post (its parent). This may be sufficient, depending on our business domain.

#### Hibernate Implementation
The necessary annotations should be made according to this model:
![Implementation Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/unidirect/hibernate.class.puml)

#### Database Schema
The Hibernate annotations will be processed by 
the jakarta runtime and database tables will be automatically 
generated similar to this schema
![DB Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/unidirect/database.puml)


## Get started

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/jpa-mapppings-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

### Provided Code

#### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
