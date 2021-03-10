@echo off

echo This Demo is for Trade Store by Gaurav Jain  .

echo For initial setup of this project Please run 'start'  operation

echo Ther are 3 operations you can perform :

echo show
echo add T4 2 CP-1 B2 11/04/2021
echo show T2

set /p operation=Enter Operation?:

java -jar gjtradestore-0.0.1-SNAPSHOT.jar %operation%

pause
