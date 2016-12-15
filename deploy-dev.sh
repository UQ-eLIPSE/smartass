#./make war

#project_dir=~/Projects/smartass
./make clean war
svcadm disable tomcat
rm -rf /var/www/smartass-dev
rm -f /var/www/smartass-dev.war
mv -f ./build/libs/smartass-dev.war /var/www/
rm -f /opt/local/share/tomcat/logs/*
svcadm enable tomcat

sleep 3

svcs | grep tomcat
