Bus Payment Service

How to run the application
- This application is run from "Main" using the terminal
- First it must be compiled: javac -d out src/main/model/*.java src/main/*.java
- Then it can be run: java -cp out main.Main
- You will know it has run successfully as the "trips.csv" file will be created

Assumptions
- The data provided in "taps.csv" is well formatted and is not missing data
- There is no "Tap Off" only. The first tap will always be considered a "Tap On".


Notes
- In the provided input file there was a "Tap Off" without an accompanying "Tap On". As per the above assumptions
  I have corrected this data to be a "Tap On" only.


- Provide a test harness to validate your solution
- Provide the output file for the example input file