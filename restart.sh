#!/bin/bash
sh clean.sh
sh mvnw package
sh mvnw generate-sources
java -p dependencies:modules -m webserver/com.webserver.Application
