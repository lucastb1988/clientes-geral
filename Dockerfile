FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir /opt/clientes-geral
WORKDIR /opt/clientes-geral
COPY . /opt/clientes-geral
RUN mvn clean package

FROM openjdk:11-jre-slim
COPY --from=build /opt/clientes-geral/target/clientes-geral*.jar clientes-geral.jar
EXPOSE 8080
ENTRYPOINT java -jar clientes-geral.jar