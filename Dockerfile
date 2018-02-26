FROM openjdk:8-jdk-alpine
ADD . /NoteRepositoryServices
WORKDIR /NoteRepositoryServices
CMD ["java", "-jar", "target/NoteRepositoryService-1.jar"]
