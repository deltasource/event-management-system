`# Event Management Backend

This project is the backend part of the general Event Management System project.
It provides REST APIs for managing events, which you can access once the application
is up and running. The API endpoints will allow you to create, read, update, and delete events.

Before running the backend of the Event Management System,
you’ll need to have a few tools installed on your machine.
These tools are necessary to build, run, and test the application:
## Java (JDK 21 or Later)
## Maven
Maven is used to manage project dependencies and to build the project. 

The project uses some dependencies, so to install them, you should 
use maven to download them and build the project.
You can use the command: 'mvn clean install'.
This will clean up any previous builds, download all required dependencies,
and package the project into an executable .jar file.

To run the whole project:
```bash
cd Backend && mvn clean install && java -jar target/event-system-0.0.1-SNAPSHOT.jar
cd ../Frontend && npm install && node index.js
```
`