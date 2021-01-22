@echo off
set base_path=%cd%
echo [current base path is]%base_path%

for /d %%i in (*) do (
    cd %%i
    mvn clean
    mvn install
    cd %base_path%
)