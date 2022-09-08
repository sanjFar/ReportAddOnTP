package main.Runners;

import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import main.TestCases.GenerateReport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddonRunner {
    private final static String DevToken = "_BmW54RiUfOgIHGM2xUl3-4c6CGLcVX8Qe-thEpBqVY1";
    public static void main(String[] args)throws Exception {
            DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);
            try(Runner runner = new Runner(DevToken,driverSettings)){
                GenerateReport generateReport = new GenerateReport();
                WebDriver driver = runner.getDriver();
                runner.run(generateReport);
        }
    }
}
