# FerretDB Get-Started Java

This is a fork of https://github.com/mongodb-developer/get-started-java, simplified to run with FerretDB.

### Execution Steps

1. `cd java`
2. create the JAR file with dependencies: `mvn clean compile assembly:single`
3. run with strict Stable API: `java -jar target/java-example-1.0-SNAPSHOT-jar-with-dependencies.jar Connection -strict`
