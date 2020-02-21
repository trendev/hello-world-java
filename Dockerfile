###############
# Build the war
###############
FROM maven:3.6 as maven
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn install

#########################################
# Configure the server and deploy the war
#########################################
FROM payara/micro
LABEL maintainer="jsie@trendev.fr"

# Autodeploy the project
COPY --from=maven ./target/*.war /opt/payara/deployments

# Payara instances are standalone, Hazelcast clustering feature is disabled
# CMD ["--nocluster", "--deploymentDir", "/opt/payara/deployments"]

# k8s RBAC (ClusterRoleBinding) must be applied, otherwise instances can't connect to the Kubernetes master
# Enable Kubernetes cluster mode and autodiscovery in helloworld namespace through helloworld service
# MY_POD_NAMESPACE value is replaced during Kubernetes deployment
ENV MY_POD_NAMESPACE="helloworld"
ENV SRV="helloworld"
ENTRYPOINT java -XX:+UseContainerSupport -XX:MaxRAMPercentage=25.0 -jar payara-micro.jar --clustermode kubernetes:$MY_POD_NAMESPACE,$SRV --deploymentDir /opt/payara/deployments