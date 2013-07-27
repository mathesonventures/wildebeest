@echo off

if "%WB_HOME%"=="" goto noWbHome

java -classpath "%WB_HOME%\lib\ZD.Wildebeest.Core-1.0.0.1.jar;%WB_HOME%\lib\mysql-connector-java-5.1.22.jar;" co.zd.wb.cli.WildebeestCommand %1 %2 %3 %4

goto exit

:noWbHome
echo Please set WB_HOME to the home directory of your Wildebeest installation
goto exit

:exit
