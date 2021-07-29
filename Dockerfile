FROM maven:3.6.0-jdk-8 as builder
WORKDIR /apps
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:8-jdk
WORKDIR /apps
EXPOSE 8081
EXPOSE 8076
COPY --from=builder apps/run_script.sh run_script.sh
COPY --from=builder apps/jobnetwork/target/*.jar jobnetwork.jar
COPY --from=builder apps/cvgenerator/target/*.jar cvgenerator.jar
CMD ["bash", "run_script.sh"]