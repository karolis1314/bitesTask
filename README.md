# bitesTask

To run this backend application you need to run "docker compose up" on root project directory.
This should start all of the needed components for back-end.
You can open swagger ui documentation via this link ::::
      http://localhost:8080/swagger-ui/index.html

Then you can see the end-points provided for the back-end application.
Some logs are sent to Kafka topics, dependent on the class.


There are postman collections added to the project, which can be used to test the application.
bite/src/main/resources/postman

To create a customer via front end, you must click LOGIN, it will create custumer.

There are test classes added as well.


There is git-lab ci/cd file too which was tested and fully working.

If you dont want front end on the docker-compose, please comment out front part in the docker compose file.

If you dont want to run docker-compose, then you will need to pull the repository if this back end and run maven clean install, and then start the project..

It requires java-17 and maven 3.8.1.

If you dont want to run docker-compose on this project, but want front end, please pull this repository down, and run npm install and npm start on the front end project.
https://github.com/karolis1314/biteFrontEnd

THIS IS A TEST PROJECT ONLY, NOT TO BE USED ANYHOW ELSE.

Any questions, please feel free to contact.


NOTE::
BACKEND APPLICATION IS EXPOSED ON :: localhost:8080
FRONTEND APPLICATION IS EXPOSED ON :: localhost:3000
KAFKA BROKER IS EXPOSED ON :: localhost:9092
ZOOKEEPER IS EXPOSED ON :: localhost:2181
