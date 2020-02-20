# helloworld
Java Microprofile helloworld Rest API 

## Docker context
`docker build -t trendev/helloworld .`

`docker run -d -p 8080:8080 trendev/helloworld`

#### Edit the TEXT_MESSAGE property
`docker run -p 80:8080 -e TEXT_MESSAGE="Bonjour le monde" trendev/helloworld`
#### Test
`curl -s -vvv localhost`

## Kubernetes context
All confif files are located in `k8s/` folder

### Create helloworld namespace
`kubectl apply -f k8s/`
