@echo off

:: Start Backend (Spring Boot)
echo Starting Spring Boot Backend...
cd server
start gradlew bootRun

:: Start Frontend (Angular)
echo Starting Angular Frontend...
cd ..\client
start ng serve
