@echo off

SET mysql_container=mysqlserver
SET sqlserver_container=sqlserver
SET postgresserver_container=postgresserver

IF NOT "%1"=="" (
    IF "%1"=="--help" (	
			echo Script to help use manage docker containers
			echo Usage script_name --[Argument] ... 
			echo 
			echo --clean   Removes docker containers and images 
			echo --help    Prints help message and exits script
			echo --list    lists all running docker containers
			echo --setup   pulls and creates all predefined docker containers to be ready for use
			echo default password will be used for all databases: Password123!
			echo --start   starts docker containers predefined in script
			echo --stop    stops all docker containers predefined in script
			EXIT /B 0
		)
	IF "%1"=="--start" (	
		IF "%2"=="" (
			echo Starting docker containers ...
			docker start %mysql_container%
			docker start %sqlserver_container%
			docker start %postgresserver_container%	
			EXIT /B 0
		)
			docker start %2
			EXIT /B 0
		)
	IF "%1"=="--stop" (	
		IF "%2"=="" (
			echo Stoping docker containers ...
			docker stop %mysql_container%
			docker stop %sqlserver_container%
			docker stop %postgresserver_container%	
			EXIT /B 0
		)
			docker stop %2
			EXIT /B 0
		)	
	IF "%1"=="--setup" (	
		 echo "Setting up docker containers ..."
			docker pull mysql
			docker pull microsoft/mssql-server-linux:2017-latest
			docker pull postgres
			
			docker run -it -p 127.0.0.1:13306:3306 --name %mysql_container% -e MYSQL_ROOT_PASSWORD=Password123! -d mysql:latest
			docker run -it -p 127.0.0.1:11433:1433 -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Password123!" --name %sqlserver_container% -d microsoft/mssql-server-linux:2017-latest
			docker run -it -p 127.0.0.1:15432:5432 --name %postgresserver_container% -e POSTGRES_PASSWORD=Password123! -d postgres
			EXIT /B 0
		)	
	IF "%1"=="--clean" (	
		echo Stoping containers :
			docker stop %mysql_container%
			docker stop %sqlserver_container%
			docker stop %postgresserver_container%
			echo Removing containers :
			docker rm %mysql_container%
			docker rm %sqlserver_container%
			docker rm %postgresserver_container%
			echo Removing images :
			docker rmi mysql:latest
			docker rmi microsoft/mssql-server-linux:2017-latest
			docker rmi postgres
			EXIT /B 0
		)	
		
	IF "%1"=="--list" (	
			docker ps 
			EXIT /B 0
		)		
	)
echo Script to help use manage docker containers
echo Usage script_name --[Argument] ... 
echo 
echo --clean   Removes docker containers and images 
echo --help    Prints help message and exits script
echo --list    lists all running docker containers
echo --setup   pulls and creates all predefined docker containers to be ready for use
echo default password will be used for all databases: Password123!
echo --start   starts docker containers predefined in script
echo --stop    stops all docker containers predefined in script
EXIT /B 0		


	
