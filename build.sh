#!/bin/bash
sh mvnw package -DskipTests
sh mvnw generate-sources -DskipTests

# Start a command in the background and capture its PID
#/bin/bash mvnw package -DskipTests -U &
#pid1=$!

# Start another command in the background and capture its PID
#/bin/bash mvnw generate-sources -DskipTests -U &
#pid2=$!

#wait $pid1
#wait $pid2

#Notes
# The -DskipTests is absolutely necessary because by default it runs also the junit tests.
# The tests are handled by the maven-surefire-plugin which is configured to depend of
# the outputs of these commands
# so the tests can be executed from commandline only after these two commands generated
# their output
