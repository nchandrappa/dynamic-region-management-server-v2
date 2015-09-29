start locator --name=locatorA --port=10334 --properties-file=config/locator.properties --initial-heap=250m --max-heap=250m

start server --name=serverVoya1 --server-port=0 --properties-file=config/gemfire-server.properties --classpath=$CLASSPATH:../../target/classes/:../../target/dynamic-region-management-server-1.0.0-SNAPSHOT.jar --initial-heap=250m --max-heap=250m