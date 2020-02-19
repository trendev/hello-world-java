# hello-world
Java Microprofile hello-world Rest API 

## Docker context
`docker build -t trendev/hello-world-java .`
`docker run -d -p 8080:8080 trendev/hello-world-java`
#### Edit the TEXT_MESSAGE property
`docker run -p 8080:8080 -e TEXT_MESSAGE="Bonjour le monde" trendev/hello-world-java`

### Test
`curl -s -vvv http://localhost:8080/api/hello-world`
