call mvn clean
call mvn install -DskipTests
java -jar  target/ranking-0.0.1-SNAPSHOT.jar