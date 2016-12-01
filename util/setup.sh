#!/usr/bin/env bash
#
# Deploys SmartAss
#

pushd ..
printf "[?] Current working directory: %s\n" `pwd`

printf "[+] Copying smartass properties file\n"
cp src/main/webapp/WEB-INF/smartass-zone.properties src/main/webapp/WEB-INF/smartass.properties

printf "[+] Copying data folder"
sudo cp ./resource/data /var/www/smartass

printf "[+] Downloading extra dependencies\n"
$(cd util && ./get_deps.sh)

printf "[+] Creating gradle wrapper\n"
gradle wrapper
sudo ln -s gradlew make
./make clean war

printf "[+] Starting deploy\n"
sudo svcadm disable tomcat
sudo rm -rf /var/www/smartass-dev
sudo rm -f /var/www/smartass-dev.war
sudo mv -f ./build/libs/smartass-dev.war /var/www/
sudo rm -f /opt/local/share/tomcat/logs/*
sudo svcadm enable tomcat

printf "[+] Performing clean restart\n"
sudo util/restart-clean.sh

printf "[+] Setup finished\n"
popd
