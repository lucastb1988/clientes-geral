FROM openjdk

VOLUME tmp

EXPOSE 8081

COPY target/clientes-geral-0.0.1-SNAPSHOT.jar clientes-geral.jar

ENTRYPOINT [ "java", "-jar", "/clientes-geral.jar" ]