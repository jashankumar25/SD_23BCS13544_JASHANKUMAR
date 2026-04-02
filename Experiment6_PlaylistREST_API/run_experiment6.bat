@echo off
echo ==============================================
echo  EXPERIMENT 6 NGINX AUTOMATION SCRIPT
echo ==============================================

echo [1] Putting the correct Nginx Load Balancer Config into D:\LAPTOP -APP\nginx-1.29.7\conf\
copy /Y "c:\Users\Lenovo\Downloads\system design\NginxExperiment\nginx.conf" "D:\LAPTOP -APP\nginx-1.29.7\conf\nginx.conf"

echo [2] Stopping any previously running Nginx and Java Backends...
taskkill /F /IM nginx.exe >nul 2>&1
taskkill /F /IM java.exe >nul 2>&1

echo [3] Starting Nginx...
pushd "D:\LAPTOP -APP\nginx-1.29.7"
start nginx.exe
popd

echo [4] Compiling Java classes...
pushd "c:\Users\Lenovo\Downloads\system design\Experiment6_PlaylistREST_API\src"
javac -cp . *.java controller\*.java model\*.java service\*.java strategy\*.java

echo [5] Starting Experiment 6 API on Port 3000
start java -cp . HttpServerMain 3000

echo [6] Starting Experiment 6 API on Port 3001
start java -cp . HttpServerMain 3001

echo [7] Waiting for servers to boot up...
ping 127.0.0.1 -n 4 >nul

echo [8] Running Load Balancer Efficiency Report!
java -cp . NginxLoadTestExperiment6
popd

echo [9] Opening Dashboard in browser...
start "" "%~dp0index.html"

echo ==============================================
echo  ALL DONE!
echo  To stop everything later, close the two java windows 
echo  and run: taskkill /F /IM nginx.exe
echo ==============================================
