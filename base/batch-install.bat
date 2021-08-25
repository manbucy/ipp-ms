@echo off
set base_path=%cd%
echo [current base path is]%base_path%

if "%1" == "" (
    for /d %%i in (ipp*) do (
        cd %%i
        mvn clean
        mvn install
        cd %base_path%
    )
) else (
    cd %1
    mvn clean
    mvn install
    cd %base_path%
)