FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD ./build/libs/Spring-security-oauth2-jwt-auth-0.0.1-SNAPSHOT.jar /app
RUN ln -sf Spring-security-oauth2-jwt-auth-0.0.1-SNAPSHOT.jar Spring-security-oauth2-jwt-auth-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

CMD /app/start /app/Spring-security-oauth2-jwt-auth-latest.jar
