# helloworld
Java Microprofile _helloworld_ Rest API 

## Docker
`docker build -t trendev/helloworld .`

`docker run -d -p 8080:8080 trendev/helloworld`

#### Edit the TEXT_MESSAGE property
`docker run -p 80:8080 -e TEXT_MESSAGE="Bonjour le monde" trendev/helloworld`

#### Test the service
if you don't know **jq** ... you should :thumbsup:

`curl -s localhost | jq`
```json
{
  "message": "'Hello Docker World'",
  "pod_name": "NO_POD_NAME",
  "namespace": "NO_POD_NAMESPACE",
  "pod_IP": "NO_POD_IP",
  "timestamp": 1582220506794
}
```
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