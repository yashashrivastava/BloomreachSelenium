# BloomreachSeleniumBDD

BDD based Selenium project for Bloomreach

I have designed a test automation framework using cucumber framework, MAVEN project and Page Object design for http://cms.demo.onehippo.com/. 
And i have covered 1 scenario as per mentioned in the ‘Part 4 : Write functional tests using Selenium to test (a part) of the workflow:→
1) Verify that user with Author role is successfully able to create a document.

###To Run

Please run `TestRunner` class from `src/test/java/runner` package.

or you can use below command to run the test cases from command line.

```shell
mvn test
```
It will execute feature files (written in Gherkin format) from `src/test/resources/features` package using the step definitions present under `src/test/java/definitions` package.
