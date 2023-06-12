FROM amazoncorretto:17-alpine-jdk
COPY . /app
WORKDIR /app
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} prison-break-game.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar prison-break-game.jar ${0} ${@}"]
