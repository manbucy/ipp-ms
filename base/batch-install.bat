@echo off
set base_path=%cd%
echo [current base path is]%base_path%

for /d %%i in (ipp*) do (
    cd %%i
    mvn clean
    mvn install
    mvn source:jar install
    cd %base_path%
)