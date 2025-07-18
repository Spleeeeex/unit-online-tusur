package online_tusur.unit_online_tusur.testng;

import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTestListener implements ITestListener {
    private static FileWriter writer;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            File outputDir = new File("test-output");
            outputDir.mkdirs();
            writer = new FileWriter("test-output/testng-events.xml", false);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<testng-events>\n");
            System.out.println("TestNG Listener initialized. Events will be logged to test-output/testng-events.xml");
        } catch (IOException e) {
            System.err.println("Failed to initialize TestNG Listener:");
            e.printStackTrace();
        }
    }

    private synchronized void writeTestEvent(ITestResult result, String status) {
        try {
            String event = String.format(
                    "  <event name=\"%s\" status=\"%s\" thread=\"%d\" time=\"%s\"/>\n",
                    result.getName(),
                    status,
                    Thread.currentThread().getId(),
                    DATE_FORMAT.format(new Date())
            );
            writer.write(event);
            writer.flush();
            System.out.println("[LISTENER] " + event.trim());
        } catch (IOException e) {
            System.err.println("Failed to write test event:");
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        writeTestEvent(result, "STARTED");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        writeTestEvent(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        writeTestEvent(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeTestEvent(result, "SKIPPED");
    }

    public static void closeReport() {
        try {
            if (writer != null) {
                writer.write("</testng-events>");
                writer.close();
                System.out.println("TestNG Listener report finalized: test-output/testng-events.xml");
            }
        } catch (IOException e) {
            System.err.println("Failed to close TestNG Listener report:");
            e.printStackTrace();
        }
    }
}