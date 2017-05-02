@echo off
set BASEDIR=%~sdp0
call %BASEDIR%\setEnv.bat

echo removing the old
rmdir /S /Q %TOMCAT_HOME%\webapps\crm >NUL 2>NUL
del /F /Q %TOMCAT_HOME%\webapps\crm.war >NUL
echo deploying the new
copy /Y %PROJECT_HOME%\src\crm\target\crm.war %TOMCAT_HOME%\webapps\crm.war
echo done
timeout /t 1 >NUL 