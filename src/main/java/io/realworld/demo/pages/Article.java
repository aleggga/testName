package io.realworld.demo.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Article
{
	WebElement element;

	Article(WebElement element)
	{
		this.element = element;
	}

	List<WebElement> tags()
	{
		return element.findElements(By.xpath(".//li[contains(@class,'tag-outline')]"));
	}

	public List<String> getTags()
	{
		return tags().stream().map(el -> el.getText().trim()).collect(Collectors.toList());
	}
}
