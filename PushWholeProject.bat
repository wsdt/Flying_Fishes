@echo off
:retry
cls
color c
echo.
echo *********************** Push everything ***********************
echo.
echo git add .
echo git commit -m [your message]
echo git pull
echo git push
echo.
set files=.
set /p "files=File to be added [. for whole project]: "

if "%files%" equ "." echo WARNING: ALL FILES ARE GOING TO BE MERGED/PUSHED!
echo.
set message=%date%_%username%
set /p "message=Your Commit-Message: "

git add %files%
git commit -m "%message%"
git pull
git push
echo.
set "works=Y"
echo Did it work? [Y/N]
set /p works=[--- 
REM Update/Merge local repository, then push your own changes. 
if /i "%works%"=="N" timeout 5&goto retry
exit