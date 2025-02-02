@echo off


set "curdir=%~dp0"
set "curdir=%curdir:~,-1%"
set "src=%curdir%\aeropuerto.jar"
set "dst=..\lucas_torrijos_israel_MVC\src\main\webapp\WEB-INF\lib"

xcopy "%src%" "%dst%"

pause&exit