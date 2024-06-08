#!/bin/bash
sh mvnw package
sh mvnw generate-sources
java -p dependencies:modules -m com.backender.webservermodule/com.webserver.WebserverApplication
