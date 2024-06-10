# Backender
![alt text](https://github.com/lcristianiim/backender/blob/jigsaw-implementation/description.png?raw=true)

## Requirements
- java 21 

## How to run it
### Command line
- `./clean.sh ` This will remove all dependencies and modules already build.
- `./build.sh` This will build modules in the `modules` folder and deploy all project's dependencies in the folder `dependencies`
- `./run.sh` This will start the webserver

### Intellij
- first run the `build.sh` script to build the project. 
- Add a new configuration of type "Application"
- in the "-cp" input select "webserver"
- in the "vm options" input write "-p dependencies:modules"
- set the main class in the corresponding input "com.webserver.Application"
After the above are set the application can run in normal or debug mode

## Cool features
- using JPMS to ensure Clean Architecture
- each module can be easily transformed if/when needed into a dedicated microservice
- the server literally starts instantly, on the laptop used for testing it takes around 167ms.
- very small memory footprint. The whole project together with the webserver itself and with absolutely all the dependencies are using around 23.6MB.
- the maven 'package' phase builds the modules in one folder and all the project dependencies are in another folder. This permits very clean deployments.
- static content cached
- use virtual threads (based on Java Project Loom)
- at server startup it writes a file called pid that contains the process id of the java process.

## :scroll: License
Apache License, Version 2.0 © Cristian Szabo