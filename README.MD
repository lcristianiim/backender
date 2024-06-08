# Backender
![alt text](https://github.com/lcristianiim/backender/blob/jigsaw-implementation/description.png?raw=true)

## Requirements
- java 17

## How to run it
### Command line
- `./clean.sh ` This will remove all dependencies and modules already build.
- `./build.sh` This will build modules in the `modules` folder and deploy all project's dependencies in the folder `dependencies`
- `./run.sh` This will start the webserver

### Intellij
- Add a new configuration of type "Application"
- in the "-cp" input select "webserver"
- in the "vm options" input write "-p dependencies:modules"
- set the main class in the corresponding input "com.webserver.Application"
After the above are set the application can run in normal or debug mode


## :scroll: License
Apache License, Version 2.0 © Cristian Szabo