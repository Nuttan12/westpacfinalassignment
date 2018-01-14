package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import properties.LoadFrameworkProp;

/**
 * 
 * Represents the functions which are used to handle screenshots captured during
 * failures
 */
public class ScreenShots {
	public RemoteWebDriver driver;

	/**
	 * 
	 * : Constructor declaration
	 */
	public ScreenShots(RemoteWebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * : Takes screenshot and places it in the specified folder
	 */
	public void takeScreenShots() {
		ITestResult result;
		LoadFrameworkProp frameProp = new LoadFrameworkProp();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// File file = new
		// File(frameProp.getScreenshot()+ft_foldername.format(dNow)+".png");
		result = Reporter.getCurrentTestResult();
		File file = new File(frameProp.getScreenshot() + result.getName() + ".png");
		try {
			FileUtils.copyFile(scrFile, file);
			Reporter.log(file.getAbsolutePath());
		} catch (IOException exception) {
			Reporter.log("Error occurred while saving the screenshot " + ExceptionUtils.getStackTrace(exception));
		}
	}

	/**
	 * 
	 * : Takes screenshot and places it in the specified folder using the failed
	 * method name
	 */
	public void takeScreenShots(String testname) {
		// Date dNow = new Date();
		// SimpleDateFormat ft_foldername = new SimpleDateFormat
		// ("yyyyMMddhhmmss");
		LoadFrameworkProp frameProp = new LoadFrameworkProp();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// File file = new
		// File(frameProp.getScreenshot()+testname+ft_foldername.format(dNow)+".png");
		File file = new File(frameProp.getScreenshot() + testname + ".png");
		try {
			FileUtils.copyFile(scrFile, file);
			Reporter.log(file.getAbsolutePath());
		} catch (IOException exception) {
			Reporter.log("Error occurred while saving the screenshot " + ExceptionUtils.getStackTrace(exception));
		}
	}

}
