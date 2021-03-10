# gjtradestore
1. Prerequisites

- Java Runtime Environment 1.8 or higher
- Windows 8 or higher

Logs can be found under : C:\Projects\logs
Please change this location under resources\logback.xml file if needed 

Temporary database is created in csv file . which will be get generated in C:\Projects\ folder
Please change this location in src\main\java\com\demo\dao\data\Database.java

2. Build

Right Click on project and chose Run As 
Maven build with goal “clean package”  .
It will generate gjtradestore-0.0.1-SNAPSHOT.jar under target folder. 
 
3. Usage
-You can find trade.bat file in root folder of project 
- Copy trde.bat and gjtradestore-0.0.1-SNAPSHOT.jar in one folder .
- Go to the folder where gjtradestore-0.0.1-SNAPSHOT.jar and trade.bat is present
- Click on trade.bat file .

You can perform 3 Tasks 
-**Show** : This will show all the trades 
-**add T4 2 CP-1 B2 11/04/2021** : This will add Trade 4 into system 
-**show T2** : This will show you Trade 2 details 
