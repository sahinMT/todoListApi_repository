FROM adoptopenjdk/openjdk11
EXPOSE 9090
ARG JAR_FILE=target/todoListRestApi-0.0.1-SNAPSHOT.jar
RUN mkdir -p /usr/app/config
RUN mkdir -p /usr/app/lib
RUN mkdir -p /usr/app/logs
COPY ${JAR_FILE} /usr/app/app.jar
COPY src/main/resources/log4j2.xml /usr/app/log4j2.xml
COPY src/main/resources/start.sh /usr/app/start.sh
ENTRYPOINT ["sh","/usr/app/start.sh"]








