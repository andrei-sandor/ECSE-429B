ECSE 429 Software Validation Project Part B

Andrew Chirita (261051967) Andrei Sandor (260977451)

To run the feature files in random order
  1. First go inside src/test/java/StoryTest and run once the Run.java file
  2. Run the following command in the terminal while being in PartB directory
 
     ``` mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass=io.cucumber.core.cli.Main -Dexec.args="src/test/resources --glue StoryTest --order random:12" ```

  4. Change the random number (i.e seed) to try different orders.


Feature files can be found here: https://github.com/andrei-sandor/ECSE-429B/tree/main/PartB/src/test/resources/Features

Step definitions related files: https://github.com/andrei-sandor/ECSE-429B/tree/main/PartB/src/test/java/StoryTest
