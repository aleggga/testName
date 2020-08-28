package io.realworld.demo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase extends DriverManager
{
	@BeforeMethod
	public void setUp() {
		initialization();
	}


	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
