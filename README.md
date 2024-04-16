# FerretDB Get-Started Java

This is a fork of https://github.com/mongodb-developer/get-started-java, simplified to run with FerretDB.

### Execution Steps

1. `cd java`
2. Create the JAR file with dependencies: `mvn clean compile assembly:single`
3. To run with strict Stable API: `java -jar target/java-example-1.0-SNAPSHOT-jar-with-dependencies.jar Connection -uri "mongodb://localhost:27017/" -strict`
4. To run with PLAIN authentication pass PLAIN to the `authMechanism` URI option: `java -jar target/java-example-1.0-SNAPSHOT-jar-with-dependencies.jar Connection -uri "mongodb://username:password@localhost:27017/?authMechanism=PLAIN"`
