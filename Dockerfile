FROM openjdk

VOLUME tmp

EXPOSE 8080

COPY target/clientes-geral-0.0.1-SNAPSHOT.jar clientes-geral.jar

ENTRYPOINT [ "java", "-jar", "/clientes-geral.jar" ]