ARG packageName=ezalor
ARG buildDir=/opt/build
ARG runDir=/opt/ezalor

FROM maven:3.8-eclipse-temurin-17-alpine
ARG packageName
ARG buildDir
WORKDIR ${buildDir}
RUN mkdir -p ${packageName}
ADD .  ${packageName}
WORKDIR ${buildDir}/${packageName}
RUN ["mvn", "clean","package"]

FROM openjdk:17-jdk-alpine
ARG runDir
ARG buildDir
ARG packageName
WORKDIR ${runDir}

COPY --from=0 ${buildDir}/${packageName}/target/ezalor-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "ezalor-0.0.1-SNAPSHOT.jar"]
