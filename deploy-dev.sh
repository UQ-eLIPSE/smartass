#./make war

#project_dir=~/Projects/smartass
./make clean war
svcadm disable tomcat
rm -rf /var/www/smartass
rm -f /var/www/smartass.war
mv -f ./build/libs/smartass.war /var/www/
rm -f /opt/local/share/tomcat/logs/*
svcadm enable tomcat

sleep 3

svcs | grep tomcat
