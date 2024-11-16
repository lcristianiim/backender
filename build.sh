#!/bin/bash
sh mvnw package -DskipTests
sh mvnw generate-sources -DskipTests

#Notes
# The -DskipTests is absolutely necessary because by default it runs also the junit tests.
# The tests are handled by the maven-surefire-plugin which is configured to depend of
# the outputs of these commands
# so the tests can be executed from commandline only after these two commands generated
# their output