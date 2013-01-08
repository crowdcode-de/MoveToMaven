@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

rem if "%MOVMVN_HOME%" == "" goto env_undefined
if "%MOVMVN_HOME%" == "" set MOVMVN_HOME="..\movmvn-cli"

:logo
echo MoveToMaven Version 1.0.0-SNAPSHOT 
echo.

rem Java search
if not "%JRE_HOME%" == "" goto gotJreHome
if not "%JAVA_HOME%" == "" goto gotJavaHome
echo No JAVA_HOME or JRE_HOME available.
goto exit

:gotJavaHome
set "JRE_HOME=%JAVA_HOME%"

:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJreHome
if not exist "%JRE_HOME%\bin\javaw.exe" goto noJreHome
goto okJava

:env_undefined
echo Please define environment variable of MOVMVN_HOME.
exit /b 32

:noJreHome
rem Needed at least a JRE
echo Needed at least a JRE.
goto exit

:okJava
rem Starter
set PROGRAM_JAR=movmvn-transformer-cli-1.0.0-SNAPSHOT.jar
set JAR_FILE=%MOVMVN_HOME%\lib\%PROGRAM_JAR%
set _JAVA="%JRE_HOME%\bin\java"
set HEAP_MS=-Xms512m
set HEAP_MX=-Xmx1024m
if "%JAVA_OPTS%" == "" set JAVA_OPTS=%HEAP_MS% %HEAP_MX%
if "%LOGFILEPATH%" == "" set LOGFILEPATH=..\logs

rem call Java
%_JAVA% %JAVA_OPTS% -classpath %MOVMVN_HOME%\lib\* de.crowdcode.movmvn.cli.TransformerExecutor %1
goto end

:exit
exit /b 32

:end
if not "%ERRORLEVEL%"=="" echo exit mit code %ERRORLEVEL% 
exit /b %ERRORLEVEL%

:exit
