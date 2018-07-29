@echo off

REM Wildebeest Migration Framework
REM Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

REM Wildebeest
SET WB_CLASSPATH=%WB_HOME%\lib\MV.Wildebeest.Api-@meta.project.version.full.dotted@.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\MV.Wildebeest.Core-@meta.project.version.full.dotted@.jar

REM Logging
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\slf4j-api-1.7.7.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\slf4j-log4j12-1.7.7.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\log4j-1.2.17.jar

REM Plugin Support
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\mysql-connector-java-8.0.11.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\postgresql-9.3-1100-jdbc41.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\sqljdbc4.jar

REM Other Libraries
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\guava-20.0.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\javassist-3.21.0-GA.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\reflections-0.9.11.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\joda-time-2.10.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\java-cup-10k.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\org.eclipse.wst.xml.xpath2.processor-1.1.5-738bb7b85d.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\picocli-3.3.0.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\protobuf-java-2.6.0.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\xercesImpl-xsd11-2.12-beta-r1667115.jar
SET WB_CLASSPATH=%WB_CLASSPATH%;%WB_HOME%\lib\xml-apis-1.4.01.jar


if defined JAVA_HOME (
  java -classpath %WB_CLASSPATH% co.mv.wb.cli.WildebeestCommand %*
  goto exit
)

if not defined JAVA_HOME (
  if exist "%WB_HOME%\jdk-10.0.2\bin\java.exe" (
    set JAVA_HOME="%WB_HOME%\jdk-10.0.2\"
    %WB_HOME%\jdk-10.0.2\bin\java.exe -classpath %WB_CLASSPATH% co.mv.wb.cli.WildebeestCommand %*
    goto exit
  )
  if not exist %WB_HOME%\openjdk-10.0.2\bin\java.exe (
  echo Please install Java before using this tool
  goto exit
  )
)


goto exit

:noWbHome
echo Please set WB_HOME to the home directory of your Wildebeest installation
goto exit

:exit
