FROM eclipse-temurin:latest
ADD . /app
WORKDIR /app
CMD javac LuigiPoker/*; java src/main.java