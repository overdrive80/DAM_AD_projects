@echo off
chcp 65001 >nul
Title Creación de archivo manifest.mf en proyecto Maven de Eclipse

setlocal enabledelayedexpansion

set "curdir=%~dp0"
set "curdir=%curdir:~,-1%"
set "metafolder=META-INF"
set "metafile=MANIFEST.MF"
set "src=%curdir%\src\main\java\"
set "lib=%curdir%\target\lib"
set "dist=%curdir%\dist"
set "CR="
(set \n=^
%=This is Mandatory Space=%
)

:: Creamos las carpetas
if not exist "%dist%" md "%dist%"
if not exist "%dist%\%metafolder%" md "%dist%\%metafolder%"

:: Si existe el archivo preguntamos si sobreescribir
if exist "%dist%\%metafolder%\%metafile%" (
    set /p "crear=¿Desea sobreescribir el archivo (s/n)?: "
)

if /i "%crear%"=="n" exit

for %%j in ("%lib%\*.jar") do (
    
    set "librerias=!librerias! %%~nxj"
)

(
echo Manifest-Version: 1.0 
echo Class-Path:!librerias!!\n!) >"%dist%\%metafolder%\%metafile%"

set "p="
for /r "%src%" %%i in (*.java) do (
    set "classfile=%%~fi"
    set "classfile=!classfile:%src%=!"
    set "classfile=!classfile:\=/!"
    set "classfile=!classfile:.java=.class!"
	
    echo Name: !classfile!>>"%dist%\%metafolder%\%metafile%"
    echo Java-Bean: False!\n!>>"%dist%\%metafolder%\%metafile%"
)

cls 
echo El proceso ha finalizado, sigue estas instrucciones:
echo ----------------------------------------------------
echo 1. Ahora abre el manifest.
echo 2. Modifica las clases que son beans con:
echo    Java-Bean: True
echo 3. Verifica que están las entradas de las librerías en Class-Path: xxx.jar yyy.jar
echo.
PAUSE & EXIT