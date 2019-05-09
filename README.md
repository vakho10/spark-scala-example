# spark-scala-example
Simple Scala example for Spark using Maven as a build tool. 
## How to build
Firstly, you should have Maven, Scala and JDK 8 installed. 

After that, run `mvn clean package` and that's it. The build file will be in the **/target** folder. 

## How to run JAR file using "spark-submit"
The example JAR file expects input and output paths. The input path is the text file (from which the application will read words and compute their occurrences), the output path it a folder name where the output will reside.

The example code you might run is:

`spark-submit --class "com.example.App" --master local[4] scala-example-1.0-SNAPSHOT.jar "/tmp/test.txt" "/tmp/result.txt"`
