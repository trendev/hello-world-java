# simdg
Smart In Memory Data Grid
Build with Docker, Kubernetes, Hazelcast, Payara and Eclipse Microprofile.

## Docker
`docker build -t trendev/helloworld .`

`docker run -d -p 8080:8080 trendev/helloworld`

## Kubernetes

*All config files are located in `k8s/` folder*

### Create helloworld namespace

`kubectl apply -f k8s/namespace.yml `
```
namespace/helloworld created
```

### Create helloword service and helloworld deployment

`kubectl apply -f k8s`
``` 
deployment.apps/helloworld created 
namespace/helloworld unchanged 
service/helloworld created 
``` 

### Control if deployment is successful

`kubectl get pods -n helloworld`
```
NAME                          READY   STATUS    RESTARTS   AGE
helloworld-845b69789b-mvq5x   1/1     Running   0          88s
helloworld-845b69789b-ntxkc   1/1     Running   0          88s
helloworld-845b69789b-ps8kf   1/1     Running   0          88s
helloworld-845b69789b-sjrmn   1/1     Running   0          88s
```

`kubectl get rs -n helloworld`
```
NAME                    DESIRED   CURRENT   READY   AGE
helloworld-845b69789b   4         4         4       95s
```

`kubectl get deployments.apps -n helloworld`
```
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
helloworld   4/4     4            4           1m47s
```
### Quick build
`mvn clean install && docker build -t trendev/helloworld . && docker push trendev/helloworld`
