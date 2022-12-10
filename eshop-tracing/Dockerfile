FROM openjdk:alpine

MAINTAINER Huabing Zhao <zhaohuabing@gmail.com>

ENV APP_HOME /app/
# for MacOS
ENV JAEGER_ENDPOINT="http://host.docker.internal:14268/api/traces"
# for Linux
# ENV JAEGER_ENDPOINT="http://127.0.0.1:14268/api/traces"
ENV JAEGER_SAMPLER_TYPE=const
ENV JAEGER_SAMPLER_PARAM=1
ENV JAEGER_REPORTER_LOG_SPANS=true

COPY tracing-demo-0.0.1-SNAPSHOT.jar $APP_HOME

EXPOSE 8080

WORKDIR $APP_HOME

# set env variables when starting the container
CMD java -jar tracing-demo-0.0.1-SNAPSHOT.jar 
