# Applitools Hackathon

## Setup

1. Download the version that matches your chrome browser from [here](https://chromedriver.chromium.org/getting-started)
then install it on your `PATH`
2. You will need Java 8 or greater installed. Download it from [here](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
3. Put your Applitools API key into the `applitools.api.key=` field found in `src/test/resources/test.properties`
4. Uncomment one of the `site.url` properties in the `test.properties` file to point the tests to the desired version of
of the site

## Running Tests
Use one of the following oommands depending on what operating system you are running
* For Mac or Linux: `./gradlew clean test`
* For Windows: `gradlew.bat clean test`

At this point, the tests should be running. The output in the console log will describe the test results, and where you
can find the reports for the tests
