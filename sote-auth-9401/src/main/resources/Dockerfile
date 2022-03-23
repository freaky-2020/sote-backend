FROM openjdk:11
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./sote-auth-9401-1.0.jar ./auth.jar
EXPOSE 9401
CMD java -jar ./auth.jar