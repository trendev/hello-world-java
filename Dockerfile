FROM payara/micro
LABEL maintainer="jsie@trendev.fr"
COPY ./target/*.war /opt/payara/deployments