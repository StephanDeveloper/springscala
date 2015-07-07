FROM ubuntu:latest

ENV TZ Europe/Berlin

RUN apt-get update \
    && apt-get -y install --no-install-recommends openjdk-7-jdk \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

ADD build/libs/springscala-1.0.jar /springscala.jar

CMD ["java","-jar","springscala.jar"]

EXPOSE 9990