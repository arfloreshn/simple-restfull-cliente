@echo off
call mvn clean package
call docker build -t com.arflores.hn/abmpais .
call docker rm -f abmpais
call docker run -d -p 9080:9080 -p 9443:9443 --name abmpais com.arflores.hn/abmpais