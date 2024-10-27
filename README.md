# jpa-mappping

A project to give worked examples for different Hibernate Relationships 
built in Quarkus and postgreSQL. Intended as a reference for people like myself
who can never remember the details... 

The domain models chosen here are kept as simple as possible
to focus on demonstrating the entity relationships.

Beyond a few minimalistic HTTP endpoints and bit of lombok and jackson, no supporting software is implemented. No hex architecture,
no automatic object mapping, no DB schema migration, no error handling, no fancy logging, no OpenAPI spec, etc.

## Mental Framework
Unfortunately, the 'parent' and 'child' roles, or 'owning' and 'owned' roles, respectively
sort of change sides depending on whether you are thinking in terms of the domain model
or in the realm of hibernate annotations and the resulting RDBMS tables, and which 
particular mapping stratgey/ annotations you choose.  

This gets messy very fast. Fortunately, you don't need to care too much unless diving 
deep into performance optimizations, and can get away with a finite set of recipes

> **Thus my personal workflow is like so:**
> - model the **domain** entities and their relationships. 
`Parent` and `Child` and other relational concepts are defined here
and will be consistently used downstream
> - decide on cardinality and directionality and implement the appropriate Hibernate annotations according
to this catalog
> - check the database tables resulting in quarkus dev mode and the primary/foreign key system
> - for production, define the DB schema migration accordingly

A good expert reference are [Vlad Mihalceas](https://vladmihalcea.com/) exquisite blogs.

## @OneToMany 
### Domain Model
In the conceptual design, i.e. the mind of the implementer,
a domain model should be present in the form shown here or 
in any other sufficient form.

![Domain Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/domain-model.class.puml)

This essentially expresses, that 
- a Post may have none, one or many
Comments,
- each Comment must be owned by a single post,
- users can identify Posts and Comments by their respective id fields

### Unidirectional
When interpreting above domain model as *unidirectional*, we
decide that a Comment (the child) does not need to know anything about the
Post (its parent). This may be sufficient, depending on our business domain.

#### Hibernate Implementation
The necessary annotations should be made according to this model:  

![Implementation Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/unidirect/hibernate.class.puml)

Sources: [`Post`](src/main/java/onosoft/onetomany/unidirectional/model/Post.java)
and [`Comment`](src/main/java/onosoft/onetomany/unidirectional/model/Comment.java)  

Special Points:
- The `@JoinColumn(name = "post_id"` is used to prevent the 
framework from generating a separate table for the relationship.
Instead, a field called `post_id` is generated as a foreign key in 
the table for the `CommentUnidirect` entity
- Notice the `@JoinColumn` must be annotated in the parent but results
in changes to the child tables (i.e. obfuscates the domain meaning of parent and child)
- The `@Entity` annotations have been given names to avoid
name collisions with the various `Post` and `Comment` classes
in other packages.

#### Database Schema
The Hibernate annotations in the [`Post`](src/main/java/onosoft/onetomany/unidirectional/model/Post.java)
and [`Comment`](src/main/java/onosoft/onetomany/unidirectional/model/Comment.java)
model classes will be processed by the hibernate implementation at runtime
and database tables will be automatically generated similar to this schema  

![DB Model](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/onouv/jpa-mappings/doc_unidirect/doc/onetomany/unidirect/database.puml)


## Get started

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

or even better just start   
```$ quarkus dev```

> **_NOTE:_** The dev services will automatically create a brand new postgres database server in a docker container.

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

#### Inspecting Database tables
With a local installation of `psql`, you can use it to inspect table schemas
and data contents:   
```$ psql -h localhost -p 5435 -U quarkus -W quarkus```

> **_Which Port Does Postgres use ?_**  
> in the quarkus cli, enter `c` for "Show dev services containers". You can
     take the postgres server port from there. However, a default for this project is defined
     [here](src/main/resources/application.properties)  

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
