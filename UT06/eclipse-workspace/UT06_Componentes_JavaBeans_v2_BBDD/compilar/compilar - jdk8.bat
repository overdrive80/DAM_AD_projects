@echo off
chcp 65001 > nul

Title Compilar componente JavaBean
:: variables
set "rootfolder=clasesbean"
set "namejar=misbeans"
set "curdir=%~dp0"
set "curdir=%curdir:~,-1%"
set "jdk=C:\Program Files\Java\jdk1.8.0_202\bin\"

java -version >nul 2>&1

IF %ERRORLEVEL% EQU 1 echo Java no está instalado o las variables de entorno no están bien configuradas. && PAUSE&EXIT

:: Establecemos la ruta de las clases java desde la posición actual

set "clasesjava=..\src\main\java\%rootfolder%"

:: Establecemos la ruta de clases para que al compilar encuentre las librerias dependientes
SET "CLASSPATH=.;%curdir%\%clasesjava%;%curdir%\lib\*"

:: Borramos las clases compiladas
rmdir "%curdir%\%rootfolder%" /Q /S 2>nul

xcopy "%clasesjava%" "%curdir%\%rootfolder%" /E /I /Y >nul

:: Compilamos las clases java
"%jdk%javac.exe" %rootfolder%\*.java

:: Empaquetamos el proyecto en un jar
"%jdk%jar.exe" cfm %namejar%.jar META-INF/MANIFEST.MF %rootfolder%/*.class

IF ERRORLEVEL 1 Echo Hubo un error en la generación del jar. & goto salir

echo Se ha generado el archivo %namejar%.jar

:salir
pause&exit
