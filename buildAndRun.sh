#!/bin/sh
mvn clean package && docker build -t com.arflores.hn/abmpais .
docker rm -f abmpais || true && docker run -d -p 9080:9080 -p 9443:9443 --name abmpais com.arflores.hn/abmpais