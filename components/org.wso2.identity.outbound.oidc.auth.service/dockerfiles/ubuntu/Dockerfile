# -------------------------------------------------------------------------------------
#
# Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
#
# WSO2 LLC. licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file except
# in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
# -------------------------------------------------------------------------------------

# remote image tag: docker.io/adoptopenjdk/openjdk11:jdk-11.0.14.1_1-ubuntu image digest: "sha256:3b97327612035c25d3899d7b1e82dd8b6758c7564afbdc5faa89a8b11a905a02"
# ACR image tag: adoptopenjdk/openjdk11:jdk-11.0.14.1_1-ubuntu_2022-06-23_06-19-50
FROM docker.io/adoptopenjdk/openjdk11:jdk-11.0.14.1_1-ubuntu@sha256:3b97327612035c25d3899d7b1e82dd8b6758c7564afbdc5faa89a8b11a905a02
LABEL maintainer="IAM Team"

# Set Docker image build arguments.
# Build arguments for user/group configurations.
ARG USER=wso2carbon
ARG USER_ID=10001
ARG USER_GROUP=wso2
ARG USER_GROUP_ID=10001
ARG USER_HOME=/home/${USER}

# build argument for MOTD
ARG MOTD="\n\
Welcome to WSO2 Docker resources.\n\
------------------------------------ \n\
This Docker container comprises of a WSO2 product, running with its latest GA release \n\
which is under the Apache License, Version 2.0. \n\
Read more about Apache License, Version 2.0 here @ http://www.apache.org/licenses/LICENSE-2.0.\n"

# Create the non-root user and group and set MOTD login message.
RUN \
    groupadd --system -g ${USER_GROUP_ID} ${USER_GROUP} \
    && useradd --system --create-home --home-dir ${USER_HOME} --no-log-init -g ${USER_GROUP_ID} -u ${USER_ID} ${USER} \
    && echo '[ ! -z "${TERM}" -a -r /etc/motd ] && cat /etc/motd' >> /etc/bash.bashrc; echo "${MOTD}" > /etc/motd

COPY --chown=wso2carbon:wso2 org.wso2.identity.outbound.oidc.auth.service.jar ${USER_HOME}/org.wso2.identity.outbound.oidc.auth.service.jar

RUN GRPC_HEALTH_PROBE_VERSION=v0.4.4 && \
    curl -o /bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 -L && \
    chmod +x /bin/grpc_health_probe

# Set the user and work directory.
USER ${USER_ID}
WORKDIR ${USER_HOME}

# Expose ports.
EXPOSE 8443

# Initiate container and start Outbound OIDC service.
ENTRYPOINT java $JVM_MEM_OPTS -Dlog4j.configurationFile=log4j2.properties -jar org.wso2.identity.outbound.oidc.auth.service.jar
