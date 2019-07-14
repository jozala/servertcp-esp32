# ESP32 + W5500 TCP test server

Quick and dirty TCP server implementation. 
Its purpose is to present connection problem in ESP32 with Wiznet W5500.

Please see complementary project for ESP32: https://github.com/maniekq/ESP32_W5500_TCP

## Building

```bash
./gradlew clean build
```

Built application can be found in `build/libs/servertcp-0.0.1-SNAPSHOT.jar`

## Running as JAR

After building (as described above) it can be run with:
```bash
java -jar servertcp-0.0.1-SNAPSHOT.jar
```

Server will start on port 6789.

## Running as Docker container

Docker image has been prepared and pushed to DockerHub.
It can be run by:
```bash
docker run -p 6789:6789 --name servertcp -d maniekq/servertcp-esp32 
```


## What to expect

When everything is working well, server logs received messages on DEBUG log level.
When message is not received as expected, you should see ERROR log message.