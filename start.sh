#!/bin/bash

echo "Using Configuration:"
echo "SERVICE_PORT=$SERVICE_PORT"
echo "SPRING_DATASOURCE_DB_NAME=$SPRING_DATASOURCE_DB_NAME"
echo "SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME"
echo "JWT_EXPIRATION_MS=$JWT_EXPIRATION_MS"
echo "JWT_SECRET_KEY=$JWT_SECRET_KEY"

# PostgreSQL başlat
echo "Starting PostgreSQL..."
pg_ctlcluster 15 main start

# Zipkin başlat
echo "Starting Zipkin..."
nohup java -jar /opt/zipkin.jar > /dev/null 2>&1 &

# Spring Boot uygulaması başlat
echo "Starting Auth Service..."
exec java -jar /opt/app.jar
