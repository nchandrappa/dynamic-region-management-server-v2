#!/bin/bash

. gf.config

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
start locator --name=locatorA --port=10334 --properties-file=config/locator.properties --initial-heap=256m --max-heap=256m

start server --name=serverVoya1 --server-port=0 --properties-file=config/gemfire-server.properties --classpath=$CLASSPATH:../../target/classes/:../../target/dynamic-region-management-server-2.0.0-SNAPSHOT.jar --initial-heap=1g --max-heap=1g

list members;
list regions;
exit;
!
