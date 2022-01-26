FROM openjdk

VOLUME /tmp

COPY target/clientes-geral-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]