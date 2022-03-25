FROM openjdk:11
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./sote-bank-8070-1.0.jar ./bank.jar
EXPOSE 10010
CMD java -jar ./bank.jar