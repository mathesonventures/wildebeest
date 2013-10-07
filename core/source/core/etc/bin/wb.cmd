REM Wildebeest Migration Framework
REM Copyright 2013, Zen Digital Co Inc
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

@echo off

if "%WB_HOME%"=="" goto noWbHome

java -classpath "%WB_HOME%\lib\ZD.Wildebeest.Core-1.0.0.3.jar;%WB_HOME%\lib\mysql-connector-java-5.1.22.jar;" co.zd.wb.cli.WildebeestCommand %1 %2 %3 %4

goto exit

:noWbHome
echo Please set WB_HOME to the home directory of your Wildebeest installation
goto exit

:exit
