@echo off

REM Wildebeest Migration Framework
REM Copyright © 2013, Zen Digital Co Inc
REM
REM This file is part of Wildebeest
REM
REM Wildebeest is free software: you can redistribute it and/or modify it under
REM the terms of the GNU General Public License v2 as published by the Free
REM Software Foundation.
REM
REM Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
REM WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
REM A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
REM
REM You should have received a copy of the GNU General Public License along with
REM Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

if "%WB_HOME%"=="" goto noWbHome

SET WB_CLASSPATH=%WB_HOME%\lib\ZD.Wildebeest.Core-@meta.project.version.full.dotted@.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\ZD.Wildebeest.Log4J-@meta.project.version.full.dotted@.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\ZD.Wildebeest.Slf4j-@meta.project.version.full.dotted@.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\mysql-connector-java-5.1.22.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\postgresql-9.3-1100-jdbc41.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\sqljdbc4.jar

java -classpath "%WB_CLASSPATH%" co.zd.wb.cli.WildebeestCommand %1 %2 %3 %4

goto exit

:noWbHome
echo Please set WB_HOME to the home directory of your Wildebeest installation
goto exit

:exit
