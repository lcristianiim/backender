#!/bin/bash
rm modules/*
./mvnw clean &&
./mvnw -Dmaven.multiModuleProjectDirectory=. package
