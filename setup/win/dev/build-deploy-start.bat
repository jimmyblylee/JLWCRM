@echo off
set BASEDIR=%~sdp0
call %BASEDIR%\setEnv.bat

title Compiling
call msg info "[INFO] Compiling" & echo.
pushd %PROJECT_HOME%\src\crm
    call mvn clean package
popd


title Redeploying
call msg info "[INFO] Redeploying" & echo.
call msg info "[INFO] removing the old" & echo.
rmdir /S /Q %TOMCAT_HOME%\webapps\crm >NUL 2>NUL
del /F /Q %TOMCAT_HOME%\webapps\crm.war >NUL 2>NUL
call msg info "[INFO] deploying the new" & echo.
copy /Y %PROJECT_HOME%\src\crm\target\crm.war %TOMCAT_HOME%\webapps\crm.war
call msg info "[INFO] done" & echo.

title Tomcat
call startJrebelDebugTomcat.bat