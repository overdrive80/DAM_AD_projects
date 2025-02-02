@echo off
chcp 65001 > nul

Title Compilar componente JavaBean
:: variables
set "curdir=%~dp0"
set "curdir=%curdir:~,-1%"
set "src=%curdir%\src\main\java"
set "lib=%curdir%\target\lib"
set "dist=%curdir%\dist"
set "jdk=C:\Program Files\Java\jdk-17\bin\"
set "namejar=aeropuerto"

java -version >nul 2>&1

IF %ERRORLEVEL% EQU 1 echo Java no está instalado o las variables de entorno no están bien configuradas. && PAUSE&EXIT

:: Establecemos la ruta de clases para que al compilar encuentre las librerias dependientes
SET "CLASSPATH=.;%dist%;%lib%"

xcopy "%src%" "%dist%" /E /I /Y >nul

:: Compilar recursivamente las clases java en el directorio dist
for /r "%dist%" %%f in (*.java) do (
    "%jdk%javac.exe" "%%f"
	del "%%f"
)

:: Empaquetamos el proyecto en un jar
"%jdk%jar.exe" cfm %namejar%.jar "%dist%"\META-INF/MANIFEST.MF -C "%dist%" .

:salir
pause&exit
