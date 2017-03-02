<h3>How to Build and Run the Program & Tests</h3>
<h4>Using IntelliJ IDEA:</h4>
<ol>
<li>Clone from Git repository.</li>
  <ol>
  <li>Checkout from Version Control > Git
  <li>Git repository URL: https://github.com/sirsanderp/CampsiteGapRule.git
  <li>Clone
  </ol>
<li>Build the project.
  <ol>
  <li>Build > Build Project
  <li>May need to “Add the Maven pom” in the pop-up on the bottom right.
  </ol>
<li>Run the program.
  <ol>
  <li>In the IDE:
    <ol>
    <li>Open the project view and run src/main/java/AvailableCampsites.java
    <li>Change the JSON input file by changing the pathname in the code.
    </ol>
  <li>In the terminal:
    <ol><li><code>java -jar ${PROJECT_PATH}/out/artifacts/campsitesgaprule_jar/campsitesgaprule.jar ${JSON_FILE_PATH}</code></ol>
  </ol>
<li>Run the tests.
  <ol>
  <li>jUnit tests:
    <ol><li>Open the project view and run src/test/java/QueryTest.java</ol>
  <li>JSON files tests:
    <ol><li>Run the program by replacing the ${JSON_FILE_PATH} above with the JSON files under ~/src/main/resources</ol>
  </ol>
</ol>

<h4>Using the Terminal:</h4>
<ol>
<li>Clone from Git repository.
  <ol><li>https://github.com/sirsanderp/CampsiteGapRule.git</ol>
<li>Change to the source code directory (~/src/main/java).
<li>Download the Jackson libraries.
  <ol><li>https://search.maven.org/#search%7Cga%7C1%7Ccom.fasterxml.jackson.core</ol>
<li>Compile the code.
  <ol><li><code>javac -cp ".;jackson-core-2.8.7.jar;jackson-annotations-2.8.7.jar;jackson-databind-2.8.7.jar" *.java</code></ol>
<li>Run the program.
  <ol><li><code>java -cp ".;jackson-core-2.8.7.jar;jackson-annotations-2.8.7.jar;jackson-databind-2.8.7.jar" AvailableCampsites ${JSON_FILE_PATH}</code></ol>
<li>Run the tests.
  <ol><li>Run the program by replacing the ${JSON_FILE_PATH} above with the JSON files under ~/src/main/resources</ol>
</ol>
<br>
<h3>Approach to the Problem</h3>
<ol>
<li>Create a parser for the JSON input file to build the required objects from the data.
<li>Build a “database” object for the campsites based on the JSON input file.
  <ol>
  <li>A campsites table from the JSON “campsites” array.
    <ol><li>Maps the “id” to the “name”.</ol>
  <li>A campsite reservations table from the JSON “reservations” array.
    <ol>
    <li>Maps the “id” to a boolean array of length 367, the number of days in a year.
    <li>Converts the given reservation “startDate” and “endDate” to their respective nth days in the year and sets the corresponding day indices to true.
    </ol>
  <li>A gap rules set from the JSON “gapRules” array.
  </ol>
<li>Build a “query” object based on the JSON input file.
  <ol><li>The “startDate” and “endDate” are converted to their respective nth days of the year.</ol>
<li>Run the "query" against the "database".
  <ol>
  <li>Check if the requested dates are already reserved.
    <ol><li>If yes, then the campsite is not available.</ol>
  <li>Check the number of available days before the requested start day.
    <ol><li>If equal to a value in the set of gap rules then the campsite is not available.</ol>
  <li>Check the number of days available after the requested end day.
    <ol><li>If equal to a value in the set of gap rules then the campsite is not available.</ol>
  <li>In all other cases the campsite is available.
  </ol>
<li>Allow the query dates to be changed to check different dates against the same database.
<li>Allow the database to be changed to add new campsites, reservations, and gap rules.
</ol>
<br>
<h3>Assumptions and Special Considerations</h3>
<ol>
<li>All JSON fields are checked for validity.
  <ol><li>i.e. start date &lt;= end date</ol>
<li>Reservations are within a single calendar year.
  <ol><li>i.e. Fails if startDate = 2015-12-30 & endDate = 2016-01-02</ol>
<li>Campsite ID’s don’t have to be in order and could be any positive integer.
<li>Ability to run multiple queries against the database.
</ol>
<br>
<h3>Resources</h3>
<ol>
<li>https://www.epochconverter.com/days/2016
  <ol><li>Find the nth day of the year for a given date.</ol>
</ol>
