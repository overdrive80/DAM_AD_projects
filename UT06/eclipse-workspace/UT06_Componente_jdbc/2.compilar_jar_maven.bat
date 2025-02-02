@echo off
chcp 65001 > nul

Title Compilar componente JavaBean
:: variables
set "curdir=%~dp0"
set "curdir=%curdir:~,-1%"
set "src=%curdir%\src\main\java"
set "lib=%curdir%\target\lib"
set "dist=%curdir%\dist"
set "jdk=C:\Program Files\Java\jdk-1.8\bin\"
rem set "jdk=C:\Program Files\Java\jdk1.7.0_80\bin\"
rem set "jdk=C:\Program Files\Java\jdk-11.0.11\bin\"
set /p "namejar=Indica el nombre, sin extensión, del jar: "

java -version >nul 2>&1

IF %ERRORLEVEL% EQU 1 echo Java no está instalado o las variables de entorno no están bien configuradas. && PAUSE&EXIT

for /D %%i in (dist\*) do (
	if not "%%~ni"=="META-INF" rmdir /S /Q "%%i"
)

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
