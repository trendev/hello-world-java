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

### Control if 4 helloworld Pods are running

`kubectl get pods -n helloworld`
```
NAME                          READY   STATUS    RESTARTS   AGE
helloworld-845b69789b-89kz5   1/1     Running   0          9m46s
helloworld-845b69789b-b4shn   1/1     Running   0          9m46s
helloworld-845b69789b-p74hw   1/1     Running   0          9m46s
helloworld-845b69789b-zcnpj   1/1     Running   0          9m46s
```

