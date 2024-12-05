# Advent of Code

### Build GraalVM image
Build the jar file first:
```
./gradlew clean assemble
```

Next run the jar file to obtain all reflection data:
```
 /Users/dennis.vandervelde/Library/Java/JavaVirtualMachines/graalvm-ce-21.0.2/Contents/Home/bin/java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/aoc-1.0-SNAPSHOT.jar
```

Finally, build the Runner executable:
```
native-image --no-fallback -H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image -Djava.awt.headless=false -jar build/libs/aoc-1.0-SNAPSHOT.jar Runner
```
