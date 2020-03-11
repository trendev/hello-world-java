# simdg
Smart In Memory Data Grid
Build with Docker, Kubernetes, Hazelcast, Payara and Eclipse Microprofile.

## Docker
`docker build -t trendev/simdg .`

`docker run -d -p 8080:8080 trendev/simdg`

or you can use the docker-compose file 

`docker-compose up`

## Kubernetes

> All config files are located in `k8s/` folder

### Create simdg namespace

`kubectl apply -f k8s/namespace.yml `
```
namespace/simdg created
```

### Create simdg service and simdg deployment

`kubectl apply -f k8s`
``` 
deployment.apps/simdg created 
namespace/simdg unchanged 
service/simdg created 
``` 

### Control if deployment is successful

`kubectl get pods -n simdg`
```
NAME                          READY   STATUS    RESTARTS   AGE
simdg-845b69789b-mvq5x   1/1     Running   0          88s
simdg-845b69789b-ntxkc   1/1     Running   0          88s
simdg-845b69789b-ps8kf   1/1     Running   0          88s
simdg-845b69789b-sjrmn   1/1     Running   0          88s
```

`kubectl get rs -n simdg`
```
NAME                    DESIRED   CURRENT   READY   AGE
simdg-845b69789b   4         4         4       95s
```

`kubectl get deployments.apps -n simdg`
```
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
simdg   4/4     4            4           1m47s
```
### Quick build
`mvn clean install && docker build -t trendev/simdg . && docker push trendev/simdg`
