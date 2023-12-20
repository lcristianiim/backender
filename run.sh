#!/bin/bash
#java -Dfile.encoding=UTF-8 -classpath "dependencies/jakarta.transaction-api-2.0.1.jar:dependencies/jboss-logging-3.5.0.Final.jar:dependencies/hibernate-commons-annotations-6.0.6.Final.jar:dependencies/classmate-1.5.1.jar:dependencies/byte-buddy-1.14.7.jar:dependencies/jakarta.xml.bind-api-4.0.0.jar" --module-path modules:dependencies -m com.backender.webservermodule/com.webserver.WebserverApplication
#java -Dfile.encoding=UTF-8 -classpath "dependencies/*" -p modules -m com.backender.webservermodule/com.webserver.WebserverApplication
java -Dfile.encoding=UTF-8 -p dependencies:modules -m com.backender.webservermodule/com.webserver.WebserverApplication
