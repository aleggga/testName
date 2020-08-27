package io.realworld.demo.pages;

import org.openqa.selenium.WebElement;

public class FilterTag
{
	private final WebElement element;

	FilterTag(WebElement element)
	{
		this.element = element;
	}

	public String getName()
	{
		return element.getText();
	}

	void filterBy()
	{
		element.click();
	}
}