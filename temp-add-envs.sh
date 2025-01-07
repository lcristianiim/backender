#!/bin/bash

# Define the environment variables to add
VAR1="Value1"
VAR2="Value2"
VAR3="Value3"

# Check if the variables are already in .bashrc
if ! grep -q "export VAR1=" ~/.bashrc; then
    echo "export VAR1=\"$VAR1\"" >> ~/.bashrc
fi

if ! grep -q "export VAR2=" ~/.bashrc; then
    echo "export VAR2=\"$VAR2\"" >> ~/.bashrc
fi

if ! grep -q "export VAR3=" ~/.bashrc; then
    echo "export VAR3=\"$VAR3\"" >> ~/.bashrc
fi

# Source the .bashrc to apply changes
source ~/.bashrc

echo "Environment variables added and .bashrc sourced."