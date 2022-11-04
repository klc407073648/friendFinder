#!/bin/bash

curPath=`pwd`
config_file=${curPath}/conf/config
application_file=${curPath}/conf/application-prod.yml
nginx_file=${curPath}/conf/nginx.conf

function preDeal()
{
    echo "preDeal begin"
    cd ./conf
    dos2unix *
    echo "preDeal begin"
}

function parseConfigFile()
{
   echo "parseConfigFile begin" 

   MYSQL_IP=`cat $config_file |grep "MYSQL_IP=" |cut -f2 -d'='`
   MYSQL_PORT=`cat $config_file |grep "MYSQL_PORT=" |cut -f2 -d'='`
   MYSQL_DB_NAME=`cat $config_file |grep "MYSQL_DB_NAME=" |cut -f2 -d'='`
   MYSQL_DB_USER=`cat $config_file |grep "MYSQL_DB_USER=" |cut -f2 -d'='`
   MYSQL_DB_PWD=`cat $config_file |grep "MYSQL_DB_PWD=" |cut -f2 -d'='`

   REDIS_IP=`cat $config_file |grep "REDIS_IP=" |cut -f2 -d'='`
   REDIS_PORT=`cat $config_file |grep "REDIS_PORT=" |cut -f2 -d'='`
   REDIS_DB_NUM=`cat $config_file |grep "REDIS_DB_NUM=" |cut -f2 -d'='`
   REDIS_DB_PWD=`cat $config_file |grep "REDIS_DB_PWD=" |cut -f2 -d'='`

   BACKEND_IP=`cat $config_file |grep "BACKEND_IP=" |cut -f2 -d'='`
   BACKEND_PORT=`cat $config_file |grep "BACKEND_PORT=" |cut -f2 -d'='`

   FRONTEND_IP=`cat $config_file |grep "BACKEND_IP=" |cut -f2 -d'='`
   FRONTEND_PORT=`cat $config_file |grep "BACKEND_PORT=" |cut -f2 -d'='`

   echo "MySQL Config"
   echo "MYSQL_IP=$MYSQL_IP"
   echo "MYSQL_PORT=$MYSQL_PORT"
   echo "MYSQL_DB_NAME=$MYSQL_DB_NAME"
   echo "MYSQL_DB_USER=$MYSQL_DB_USER"
   echo "MYSQL_DB_PWD=$MYSQL_DB_PWD"

   echo "Redis Config"
   echo "REDIS_IP=$REDIS_IP"
   echo "REDIS_PORT=$REDIS_PORT"
   echo "REDIS_DB_NUM=$REDIS_DB_NUM"
   echo "REDIS_DB_PWD=$REDIS_DB_PWD"

   echo "Backend Config"
   echo "BACKEND_IP=$BACKEND_IP"
   echo "BACKEND_PORT=$BACKEND_PORT"

   echo "Frontend Config"
   echo "FRONTEND_IP=$FRONTEND_IP"
   echo "FRONTEND_PORT=$FRONTEND_PORT"

   echo "parseConfigFile end" 
}


function modifyFile() 
{
    echo "begin to modifyFile"

    sed -i "s/\$MYSQL_IP/$MYSQL_IP/g"               ${application_file}
    sed -i "s/\$MYSQL_PORT/$MYSQL_PORT/g"           ${application_file}
    sed -i "s/\$MYSQL_DB_NAME/$MYSQL_DB_NAME/g"     ${application_file}
    sed -i "s/\$MYSQL_DB_USER/$MYSQL_DB_USER/g"     ${application_file}
    sed -i "s/\$MYSQL_DB_PWD/$MYSQL_DB_PWD/g"       ${application_file}
    
    sed -i "s/\$REDIS_IP/$REDIS_IP/g"               ${application_file}
    sed -i "s/\$REDIS_PORT/$REDIS_PORT/g"           ${application_file}
    sed -i "s/\$REDIS_DB_NUM/$REDIS_DB_NUM/g"       ${application_file}
    sed -i "s/\$REDIS_DB_PWD/$REDIS_DB_PWD/g"       ${application_file}

    sed -i "s/\$BACKEND_PORT/$BACKEND_PORT/g"       ${application_file}

    sed -i "s/\$FRONTEND_PORT/$FRONTEND_PORT/g"      ${nginx_file}
    sed -i "s/\$BACKEND_IP/$BACKEND_IP/g"            ${nginx_file}
    sed -i "s/\$BACKEND_PORT/$BACKEND_PORT/g"        ${nginx_file}

    echo "end to modifyFile"
}

function replaceFile() 
{
    echo "begin to replaceFile"

    echo "end to replaceFile"
}


function MAIN()
{
    preDeal
    parseConfigFile
    modifyFile
    replaceFile
}

MAIN