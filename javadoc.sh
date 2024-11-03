#!/bin/bash
javadoc -protected -splitindex -d doc/interactor --module-path modules:dependencies -sourcepath interactor/src/main/java -subpackages org.interactor
javadoc -protected -splitindex -d doc/webserver --module-path modules:dependencies -sourcepath webserver/src/main/java -subpackages com.webserver
javadoc -protected -splitindex -d doc/eclipselinkdatacenter --module-path modules:dependencies -sourcepath eclipselinkdatacenter/src/main/java -subpackages org.eclipselinkdatacenter
javadoc -protected -splitindex -d doc/prometheusmetrics --module-path modules:dependencies -sourcepath prometheusmetrics/src/main/java -subpackages org.backender.metrics