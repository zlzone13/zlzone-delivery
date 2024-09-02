# 빌드 단계
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew testClasses
RUN ./gradlew bootJar
#RUN ./gradlew build --no-daemon -x test
# 실행 단계
FROM openjdk:17-jdk-slim
VOLUME /tmp

COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
###
# 경량화
FROM amazoncorretto:17-alpine as corretto-jdk
# required for strip-debug to work
RUN apk add --no-cache binutils
# Build small JRE image
RUN jlink \
      --verbose \
      --add-modules java.base,java.compiler,java.desktop,java.instrument,java.management,java.naming,java.prefs,java.rmi,java.scripting,java.security.jgss,java.sql,jdk.httpserver,jdk.jfr,jdk.unsupported,jdk.crypto.ec,jdk.crypto.cryptoki \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2 \
      --output /jre

# ### 실행 단계
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=corretto-jdk /jre $JAVA_HOME
# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=local
# ENV로 실제 애플리케이션에서 사용할 환경변수 설정
ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
