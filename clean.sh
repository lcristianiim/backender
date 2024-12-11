#!/bin/bash
#rm dependencies/*
#rm modules/*
#sh mvnw clean

rm dependencies/* &
pid1=$!

# Start another command in the background and capture its PID
rm modules/* &
pid2=$!

/bin/bash mvnw clean -U &
pid3=$!

wait $pid1
wait $pid2
wait $pid3
