FROM sapmachine:latest
LABEL authors="Ale"
ADD target/question-service.jar question-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "question-service.jar"]