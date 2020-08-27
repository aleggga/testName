package io.realworld.demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase
{
	public static WebDriver driver;
	private static final String CHROME_PROPERTY_NAME = "webdriver.chrome.driver";

	public static void initialization()
	{
		setWebDriverSystemProperty();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Config.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Config.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(Config.URL);
	}

	private static void setWebDriverSystemProperty()
	{
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win"))
		{
			System.setProperty(CHROME_PROPERTY_NAME, "drivers/win_chromedriver.exe");
		}
		else if (os.contains("linux"))
		{
			System.setProperty(CHROME_PROPERTY_NAME, "drivers/linux_chromedriver");
		}
		else if (os.contains("mac"))
		{
			System.setProperty(CHROME_PROPERTY_NAME, "drivers/mac_chromedriver");
		}
	}
}
