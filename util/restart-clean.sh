#!/bin/bash

set -e

#+
#
#-

#
#	. Services to restart .
#	-----------------------
#	* nginx is 'enabled' before tomcat. 
#	* tomcat has bind errors if the other way around(?)
#
services='nginx tomcat'

#
#	. Check running as root .
#
if [[ 0 -ne $(id -u) ]]; then
	printf '\n... RUNNING as root ...!\n\n'
	sudo $0 $*
	exit 1;
fi


#
#	. Do `clean` restart .
#
svcs | grep -E "${services// /|}"
printf '\n'

printf '\n... disabling servers ...'
for svc in ${services}; do svcadm disable -t ${svc}; done
for (( i=0; i<=4; ++i )); do printf '...'; sleep 1;  done; printf '...done...\n'
svcs | grep -E "${services// /|}"
printf '\n'

rm -f /opt/local/share/tomcat/logs/*.*
rm -f /var/log/nginx/*.log

printf '\n... enabling servers ...'
for svc in ${services}; do svcadm enable ${svc}; done
for (( i=0; i<=4; ++i )); do printf '...'; sleep 1;  done; printf '...done...\n'
svcs | grep -E "${services// /|}"
printf '\n'


#
#	. La Fin .
#
exit 0;
