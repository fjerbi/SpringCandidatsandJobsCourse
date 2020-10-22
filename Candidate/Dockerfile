FROM java:8
EXPOSE 8089
ADD /target/Candidate-0.0.1-SNAPSHOT.jar docker-service-candidat.jar
ENTRYPOINT ["java",".jar","docker-service-candidat.jar"]