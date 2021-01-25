## The Assignment: Elo Ranking Algorithm

Implement a ranking program using the Elo ranking algorithm.

Given two files:

1. `names`, where each line has an ID number and a name for said ID `src/main/resources/static/names.txt`
2. `matches`, where each line contains the ID of the two players of a match and the first one is the winner of said match. `src/main/resources/static/matches.txt`
 

Implement a program that can read both files and then:

1. Score each player based on the games played
2. Generate a list of players sorted by score, their ranking (position in the list) and their number of wins and losses.
3. Generate a report for each person, showing with whom they played and how they fared.
4. Generate a list of suggested next matches.

`Note :The algorithm uses default Elo rating value of 1000 as a starting (average) value and the algorithm uses constant value of 30 for K-factor.`

## My Implementation

* It's built for **SpringBoot 2.4.2**

## Requirement
* Java 11
* Spring Boot
* Maven
* H2 Database
* Swagger API Documentation


## How to Run

* Build the project by running `mvn clean package` inside EloRanking-parent module
* Once successfully built, run the service by using the following command:
```
java -jar  EloRanking/target/ranking-0.0.1-SNAPSHOT.jar

```

## You can run the application using batch file as well on Windows machine
```
Double click on "runApp.bat" file.

```



## To test API by running it locally you can use attached postman collection.
```
https://github.com/vickhny/EloRankingAlgorithmService/blob/main/EloRanking.postman_collection.json

```



## REST APIs Endpoints
### Upload player and match data in text file format
```
POST /elo/upload
Accept: application/json
Content-Type: application/json
```
![Alt text](src/main/resources/static/upload.png?raw=true "Optional Title")

### Get ALL Player Info
```
GET /elo/getAllPlayerInfo
Accept: application/json
Content-Type: application/json
```
![Alt text](src/main/resources/static/allplayerinfo.png?raw=true "Optional Title")

### Get Individual Player Info
```
GET /elo/playerInfo
Accept: application/json
Content-Type: application/json
```
![Alt text](src/main/resources/static/individualinfo.png?raw=true "Optional Title")

### Suggested Next Matches
```
GET /elo/suggestedMatches
Accept: application/json
Content-Type: application/json
```
![Alt text](src/main/resources/static/suggestedmatches.png?raw=true "Optional Title")

### Get information about system health
```
http://localhost:8090/actuator/health

```
### To view Swagger 2 API docs
```
Run the server and browse to - http://localhost:8090/swagger-ui.html#/elo-ranking-controller
```
![Alt text](src/main/resources/static/swagger.png?raw=true "Optional Title")
