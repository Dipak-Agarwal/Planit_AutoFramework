# Planit_AutoFramework
Automation framework for Planit

Automation Framework for Planit - Integrated with DriverFactory (for parallel execution using ThreadLocal), WebDriverManager (for automatically taking care of driver and browser binaries), Maven (managing dependencies), TestNG (testing framework for multi-browser test support), ExtentReports (for creating customizing html reports), Log4j (for logging purposes) and POM (Page Object Model) Design pattern.

1. Open command prompt
2. Clone the project using the git clone command -> git clone https://github.com/Dipak-Agarwal/Planit_AutoFramework.git
3. Navigate to the parent folder containing the pom.xml file -> cd Planit_AutoFramework
4. Type "mvn verify" and click enter

The tests will run on edge, chrome and firefox browsers parallelly. Customized HTML Reports will be created at the end of test run. If any of the test fails, then screenshots will also be attached to the test report.

The HTML report can be found in the location path -> /extent-reports/extent-report.html