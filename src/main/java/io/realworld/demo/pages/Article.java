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

	private List<WebElement> tagsE()
	{
		return element.findElements(By.xpath(".//li[contains(@class,'tag-outline')]"));
	}

	private WebElement getTitleE()
	{
		return element.findElement(By.cssSelector("//a[@class='preview-link']/h1[@class='ng-binding']"));
	}

	private WebElement getAuthorE()
	{
		return element.findElement(By.xpath("//a[@class= 'author ng-binding']"));
	}

	public List<String> getTags()
	{
		return tagsE().stream().map(el -> el.getText().trim()).collect(Collectors.toList());
	}

	public String getTitle()
	{
		return getTitleE().getText();
	}

	public String getAuthor()
	{
		return getAuthorE().getText();
	}
}
