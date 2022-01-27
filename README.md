API de Clientes
====================
Sistema Api de Clientes - nome do projeto = clientes-geral.


Introdução
------------
API dedicada a atender as necessidades dos processos de cliente.


Documentação API
---------------------------------
SWAGGER


Rodando o sistema com docker (utilizando docker-compose)
-------------------
Em um sistema operacional Windows/Linux/Mac certificar de ter Docker instalado.
Digitar comando docker-compose up --build -d.
Esse comando irá criar a imagem (dentro de um container) da aplicação e também a imagem do Redis para que possa ser usada enquanto a aplicação estiver no ar.

          
### Rodando o sistema com docker (não utilizando docker-compose, realizando os comandos manualmente)
--------------------
Em um sistema operacional Windows/Linux/Mac certificar de ter Docker instalado.
run Maven Build -> clean compile install (para certificar que o Jar da aplicação está devidamente configurado).
docker build -t ${nome-aplicacao} .
docker run -p 8080:8080 ${nome-aplicacao}

docker pull redis
docker run -d -p 6379:6379 --name redis1 redis

(caso queira deletar imagem do container -> docker images (recupera id do container) -> docker rmi -f ${id container}).