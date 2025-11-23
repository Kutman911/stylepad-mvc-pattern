@echo off
echo Cleaning old compiled files...
rmdir /s /q src\out 2>nul
mkdir src\out

echo Compiling project...
javac -encoding UTF-8 -d src\out ^
  src\stylepad\*.java ^
  src\stylepad\commands\*.java ^
  src\stylepad\ui\*.java

if errorlevel 1 (
    echo.
    echo Compilation failed.
    pause
    exit /b 1
)

echo Copying images...
xcopy /E /I /Y src\stylepad\Images src\out\stylepad\Images >nul

echo Running project...
java -cp src\out stylepad.Main

echo.
pause
