#!/bin/bash

./gradlew clean assemble

/Users/dennis.vandervelde/Library/Java/JavaVirtualMachines/graalvm-ce-21.0.2/Contents/Home/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/aoc-1.0-SNAPSHOT.jar

native-image --no-fallback -H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image -Djava.awt.headless=false -jar build/libs/aoc-1.0-SNAPSHOT.jar Runner

if [ "$1" == "run" ]; then
    ./Runner
fi
