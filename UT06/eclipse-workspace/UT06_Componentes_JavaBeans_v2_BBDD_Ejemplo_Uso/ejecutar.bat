@echo off

set "java=C:\Program Files\Java\jdk-17\bin\java.exe"
set "curdir=%~dp0"
set "modulo=ALL-UNNAMED"

"%java%" --add-opens java.desktop/java.beans=%modulo% -jar "%curdir%\dist\UT06_JavaBeans_BBDD_PROD_PED_VEN.jar"


rem "%java%" -p --add-opens java.desktop/java.beans=neodatis.odb "%curdir%\dist\UT06_JavaBeans_BBDD_PROD_PED_VEN.jar;%curdir%\lib\neodatis-odb-1.9.30.689.jar;%curdir%\..\UT06_JavaBeans_Modular\dist\org.clasesbean.jar" -m usocomponente

pause