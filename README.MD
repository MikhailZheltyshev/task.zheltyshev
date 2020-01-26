# Automation test task
Tools:
1. Selenium WebDriver
2. TestNg
3. Allure2
4. Retrofit2

## Required
* Java 1.8
* Maven 3.5
## To run tests
To run all tests with Allure report generation run in terminal:
```shell script
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/suites/allTests.xml allure:serve
```
## Project structure
[/src/main/java](/src/main/java) - tests base and utils;

[/src/main/java/resources](/src/main/resources) - location for test run properties (test.properties file is used by default);
It is also possible to set another properties file (should be located in src/main/resources) using "propsFile" environment variable, e.g.:
```shell script
mvn -DpropsFile=<fileName>.properties clean test
```

[/src/test/java/rest](/src/test/java/rest) - api test classes location;

[/src/test/java/web](/src/test/java/web) - ui test classes location;

[/src/test/resources](/src/test/resources) - TestNg xml suite files location.

