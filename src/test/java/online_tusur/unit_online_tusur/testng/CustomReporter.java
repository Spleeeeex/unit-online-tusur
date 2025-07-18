package online_tusur.unit_online_tusur.testng;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomReporter implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        File reportFile = new File("test-output/testng-report.xml");

        try {
            new File("test-output").mkdirs();
            FileWriter writer = new FileWriter(reportFile, false);

            System.out.println("\nGenerating TestNG report: " + reportFile.getAbsolutePath());

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<testng-report>\n");

            // Suite results
            writer.write("  <suites>\n");
            for (ISuite suite : suites) {
                writer.write(String.format("    <suite name=\"%s\">\n", suite.getName()));

                Map<String, ISuiteResult> suiteResults = suite.getResults();
                for (ISuiteResult sr : suiteResults.values()) {
                    ITestContext tc = sr.getTestContext();

                    writer.write(String.format("      <test name=\"%s\">\n", tc.getName()));
                    writer.write(String.format("        <passed>%d</passed>\n", tc.getPassedTests().size()));
                    writer.write(String.format("        <failed>%d</failed>\n", tc.getFailedTests().size()));
                    writer.write(String.format("        <skipped>%d</skipped>\n", tc.getSkippedTests().size()));
                    writer.write("      </test>\n");
                }

                writer.write("    </suite>\n");
            }
            writer.write("  </suites>\n");

            // Summary
            int totalPassed = suites.stream()
                    .flatMap(s -> s.getResults().values().stream())
                    .mapToInt(sr -> sr.getTestContext().getPassedTests().size())
                    .sum();

            int totalFailed = suites.stream()
                    .flatMap(s -> s.getResults().values().stream())
                    .mapToInt(sr -> sr.getTestContext().getFailedTests().size())
                    .sum();

            int totalSkipped = suites.stream()
                    .flatMap(s -> s.getResults().values().stream())
                    .mapToInt(sr -> sr.getTestContext().getSkippedTests().size())
                    .sum();

            writer.write("  <summary>\n");
            writer.write(String.format("    <total-tests>%d</total-tests>\n", totalPassed + totalFailed + totalSkipped));
            writer.write(String.format("    <passed>%d</passed>\n", totalPassed));
            writer.write(String.format("    <failed>%d</failed>\n", totalFailed));
            writer.write(String.format("    <skipped>%d</skipped>\n", totalSkipped));
            writer.write("  </summary>\n");

            writer.write("</testng-report>");
            writer.close();

            System.out.println("Report generated successfully!");
            System.out.println(String.format("Summary: %d passed, %d failed, %d skipped",
                    totalPassed, totalFailed, totalSkipped));

        } catch (IOException e) {
            System.err.println("Failed to generate TestNG report:");
            e.printStackTrace();
        } finally {
            CustomTestListener.closeReport();
        }
    }
}