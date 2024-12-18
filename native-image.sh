#!/bin/bash

./gradlew clean assemble
GRAAL_BIN_PATH=/Users/dennis.vandervelde/Library/Java/JavaVirtualMachines/graalvm-ce-23.0.1/Contents/Home/bin/

$GRAAL_BIN_PATH/java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/aoc-1.0-SNAPSHOT.jar

$GRAAL_BIN_PATH/native-image -march=native --gc=epsilon -O3 -dsa --no-fallback -H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image -Djava.awt.headless=false -jar build/libs/aoc-1.0-SNAPSHOT.jar Runner

if [ "$1" == "run" ]; then
    ./Runner
fi
