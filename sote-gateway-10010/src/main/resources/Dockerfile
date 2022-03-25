FROM openjdk:11
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./sote-gateway-10010-1.0.jar ./gateway.jar
EXPOSE 10010
CMD java -jar ./gateway.jar