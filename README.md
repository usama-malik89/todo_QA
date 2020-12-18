Test Coverage: 93.8%
# ToDO Web-app Project

The goal of this project is to create both front end and back end for a ToDo web app using:
* Java
* Maven
* Spring Boot
* JUnit
* MySQL

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The Software you will need to install

```
IDE - Spring Tools 4 (https://spring.io/tools)
JAVA - Java SE Development Kit (https://www.oracle.com/uk/java/technologies/javase/javase-jdk8-downloads.html)
Database - MySQL (https://dev.mysql.com/downloads/mysql/5.7.html)
Maven - Apache Maven (https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache/)
```

### Installing

A step by step series of examples that tell you how to get a development env running

1. To run the web app simply navigate to the directory where the pom.xml file is located and run the command:
```
mvn clean package
```
This will create a directory 'target' which will contain a jar file, run the jar file by navigating to target file and running the following command:
```
java -jar ToDoAPI-1.0-SNAPSHOT.jar
```

2. This will start a local server, to access the web-app go to link:
```
http://localhost:9092/
```
## TESTS
The unit testing and integration testing are present in this spring boot project
Selenium Testing (Website Testing) are present in a seperate maven project found here:
```
https://github.com/usama-malik89/ToDoSeleniumTest
```

## Running the tests

Tests automatically run when "mvn clean package" in run. However more detailed output tests can be run using the Sprint Tools IDE:
1. In eclipse open the folder containing the source code as a Maven project
2. Right click on the project and click Coverage as -> JUnit Test, this will give a more broken down test result

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot) - Open source Java-based framework used to create a micro Service

## Version

ToDoAPI 1.0-SNAPSHOT

## Authors

* **Usama Malik** - [usama-malik89](https://github.com/usama-malik89)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* **COLOR PICKER HTML RADIO BUTTONS by Tobias Bogliolo** - [https://codepen.io/tobiasdev](https://codepen.io/tobiasdev/pen/YWpBxX) used and modified his radio color picker
