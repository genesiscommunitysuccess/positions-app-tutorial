FROM centos:centos7.9.2009 as base

RUN ["/bin/bash", "-c", "rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY*"]
RUN ["/bin/bash", "-c", "echo 'y' | yum install unzip"]
RUN ["/bin/bash", "-c", "echo 'y' | yum install java-11-openjdk-devel"]
RUN ["/bin/bash", "-c", "echo 'y' | yum install openssh-server"]
RUN if [ $(uname -m) = "x86_64" ] ; then (echo 'y' | yum install https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm) \
    ; else (echo 'y' | yum install https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-aarch64/pgdg-redhat-repo-latest.noarch.rpm) ; fi
RUN ["/bin/bash", "-c", "echo 'y' | yum install -y --nogpgcheck postgresql14-server"]
RUN if [ $(uname -m) = "aarch64" ] ; then yum -y install epel-release ; fi
RUN if [ $(uname -m) = "aarch64" ] ; then yum -y install lmdb ; fi
RUN if [ $(uname -m) = "aarch64" ] ; then ln -s /usr/lib64/liblmdb.so.0.0.0 /usr/lib64/liblmdb.so ; fi
RUN ["/bin/bash", "-c", "useradd positions-app-tutorial"]
RUN ["/bin/bash", "-c", "echo positions-app-tutorial:positions-app-tutorial | chpasswd"]
RUN ["/bin/bash", "-c", "usermod -aG wheel positions-app-tutorial"]
RUN ["/bin/bash", "-c", "mkdir /home/positions-app-tutorial/run"]
RUN ["/bin/bash", "-c", "mv /usr/bin/systemctl /usr/bin/systemctl.old"]
RUN ["/bin/bash", "-c", "curl https://raw.githubusercontent.com/gdraheim/docker-systemctl-replacement/master/files/docker/systemctl.py > /usr/bin/systemctl"]
RUN ["/bin/bash", "-c", "chmod +x /usr/bin/systemctl"]
RUN ["/bin/bash", "-c", "/usr/bin/ssh-keygen -A"]

FROM base as copy-distributions

WORKDIR /home/positions-app-tutorial/run/
COPY ./build/dependencies/genesisproduct*.zip .
COPY ./build/dependencies/genesis-distribution*.zip .
COPY ./build/dependencies/positions-app-tutorial-site-specific*.zip .
COPY ./build/dependencies/auth-distribution-*.zip .

RUN unzip \*.zip
RUN rm *.zip
RUN ["/bin/bash", "-c", "chown -hR positions-app-tutorial:positions-app-tutorial ../run/"]

FROM copy-distributions as setup-genesis

RUN echo "GENESIS_HOME=/home/positions-app-tutorial/run/" >> /home/positions-app-tutorial/.bashrc
RUN echo "export GENESIS_HOME" >> /home/positions-app-tutorial/.bashrc
RUN echo "source \$GENESIS_HOME/genesis/util/setup.sh" >> /home/positions-app-tutorial/.bashrc

FROM setup-genesis as configure-db
COPY docker-scripts/configureDB.sh /scripts/configureDB.sh
RUN ["chmod", "+x", "/scripts/configureDB.sh"]
RUN ["/scripts/configureDB.sh"]

FROM configure-db as genesis-install
COPY docker-scripts/genesisInstall.sh /scripts/genesisInstall.sh
RUN ["chmod", "+x", "/scripts/genesisInstall.sh"]
RUN ["/scripts/genesisInstall.sh"]

FROM genesis-install as remap
COPY docker-scripts/remap.sh /scripts/remap.sh
RUN ["chmod", "+x", "/scripts/remap.sh"]
RUN ["/scripts/remap.sh"]

FROM remap as entryPoint
COPY docker-scripts/docker-entrypoint.sh /scripts/docker-entrypoint.sh
RUN chmod +x /scripts/docker-entrypoint.sh
ENTRYPOINT ["/scripts/docker-entrypoint.sh"]