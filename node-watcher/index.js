const chokidar = require('chokidar');
const { exec } = require("child_process");
const fs = require('node:fs');
const shell = require('shelljs');

var watcher  = chokidar.watch('../interactor', {persistent: true, ignoreInitial: false,ignored: 'target/**'})

watcher.on('change', (event, path) => {
  console.log(event, path);
  doChange();
});

function doChange() {

shell.echo('Building interactor');
shell.exec('/bin/bash ../mvnw -f ../ package -pl interactor');

//    fs.readFile('../pid', 'utf8', (err, data) => {
//      if (err) {
//        console.error(err);
//        return;
//      }
//      let command = "kill -9 " + data
//      console.log(command)
//    });
}



