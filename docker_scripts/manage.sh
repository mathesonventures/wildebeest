#!/bin/bash 

# name of docker containers
mysql_container=mysqlserver
sqlserver_container=sqlserver
postgresserver_container=postgresserver

# functions 

help_message(){
    echo "Script to help use manage docker containers"
    echo "Usage script_name -/--[Argument] ... "
    echo ""
    echo "  -c  --clean   Removes docker containers and images" 
    echo "  -h, --help    Prints help message and exits script"
    echo "  -l, --list    lists all running docker containers"
    echo "      --setup   pulls and creates all predefined docker containers to be ready for use"
    echo "                default password will be used for all databases: Password123!"
    echo "  -s, --start   starts docker containers predefined in script"
    echo "  -x, --stop    stops all docker containers predefined in script"
}

clean(){
    echo "Stoping containers :"
    docker stop $mysql_container
    docker stop $sqlserver_container
    docker stop $postgresserver_container
    echo "Removing containers :"
    docker rm $mysql_container
    docker rm $sqlserver_container
    docker rm $postgresserver_container
    echo "Removing images :"
    docker rmi mysql:latest
    docker rmi microsoft/mssql-server-linux:2017-latest
    docker rmi postgres
}

start(){
    if [ -z $1 ] 
    then
        echo "Starting docker containers ..."
        docker start $mysql_container
        docker start $sqlserver_container
        docker start $postgresserver_container
    else 
        docker start $1
    fi 
}

stop(){
    if [ -z $1 ] 
    then
        echo "Stoping  docker containers ..."
        docker stop $mysql_container
        docker stop $sqlserver_container
        docker stop $postgresserver_container
    else 
        docker stop $1
    fi
}

setup(){
    echo "Setting up docker containers ..."
    docker pull mysql
    docker pull microsoft/mssql-server-linux:2017-latest
    docker pull postgres
    
    docker run -it -p 127.0.0.1:13306:3306 --name $mysql_container -e MYSQL_ROOT_PASSWORD=Password123! -d mysql:latest
    docker run -it -p 127.0.0.1:11433:1433 -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=Password123!' --name $sqlserver_container -d microsoft/mssql-server-linux:2017-latest
    docker run -it -p 127.0.0.1:15432:5432 --name $postgresserver_container -e POSTGRES_PASSWORD=Password123! -d postgres
}

# logic

if [ $EUID -ne 0 ] 
then
  echo $EUID
  help_message
  echo ""
  echo ""	
  echo "Script requires root privilages to run properly"
  echo "Please run again as root"
  exit
fi

while [[ $# -gt 0 ]]

do
key=$1

case $key in
    -h | --help)
    help_message
    shift #shift argument
    ;;
    -c|--clean)
    clean
    shift
    ;;
    -s|--start)
    start $2
    shift
    shift
    ;;
    -x|--stop)
    stop $2
    shift
    shift
    ;;
    -l|--list)
    docker ps
    shift
    ;;
    --setup) 
    setup
    shift
    ;;	    
esac
done 

