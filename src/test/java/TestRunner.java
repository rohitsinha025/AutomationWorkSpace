

import java.io.File;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentCucumberFormatter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		strict = false, features = "src/test/resources/features/feature", glue = {"StepDefinitionClass"},
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"}, tags = {"@CH"}, monochrome = false)


public class TestRunner {

	@BeforeClass
    public static void setup() {
          
          ExtentCucumberFormatter.initiateExtentCucumberFormatter();
          ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extent-config.xml"));
          
    }


}
