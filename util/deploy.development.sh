#!/bin/bash

set -e

#+
#
#-

#-------------------------------------------------------------------------------
#	.
#-------------------------------------------------------------------------------

#
#	. Check running as root .
#
if [[ 0 -ne $(id -u) ]]; then
	printf '\n... RUNNING as root ...!\n\n'
	sudo $0 $*
	exit 1;
fi


#
#	. Check correct current working directory .
#
#@TODO:


#
#	. TOMCAT root application context .
#
ln -s $(pwd)/conf/ROOT.xml /opt/local/share/tomcat/conf/


#
#	. NGINX config file
#
mv /opt/local/etc/nginx/nginx.conf /opt/local/etc/nginx/nginx.conf.000
ln -s $(pwd)/conf/nginx.conf /opt/local/etc/nginx/nginx.conf


#
#
#
ln -s $(pwd)/src/main/webapp /var/www/smartass


#-------------------------------------------------------------------------------
#	. La Fin .
#-------------------------------------------------------------------------------

exit 0;

