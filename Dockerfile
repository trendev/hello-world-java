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
COPY --from=maven ./target/*.war $DEPLOY_DIR/
CMD ["--nocluster", "--deploymentDir", "/opt/payara/deployments"]
