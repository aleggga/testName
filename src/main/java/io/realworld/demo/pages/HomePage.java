package io.realworld.demo.pages;

import static org.awaitility.Awaitility.await;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import io.realworld.demo.TestBase;

public class HomePage extends TestBase
{
	// Initializing the Page Objects:
	public HomePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	WebElement getNavLink()
	{
		return driver.findElement(By.cssSelector(".nav-link.active.ng-binding"));
	}

	List<WebElement> filterTagsE()
	{
		return driver.findElements(By.xpath("//div[@class='tag-list']/a[contains(@class, 'tag-default') and contains(@class, ng-bind)]"));
	}

	@Step("Collecting tags available for filter")
	public List<FilterTag> getTagsAvailableForFilter()
	{
		await().atMost(20, TimeUnit.SECONDS).until(() -> !filterTagsE().isEmpty());
		return filterTagsE().stream().map(FilterTag::new).collect(Collectors.toList());
	}

	private List<WebElement> articlesE()
	{
		return driver.findElements(By.xpath("//div[@class ='article-preview']"));
	}

	@Step("Mapping article elements to Article objects")
	public List<Article> getArticles()
	{
		return articlesE().stream().map(Article::new).collect(Collectors.toList());
	}

	public boolean isFilteredByTag()
	{
		return getNavLink().isDisplayed();
	}

	public String getTagFilteredBy()
	{
		return isFilteredByTag() ? getNavLink().getText() : "";
	}

	@Step("Filter Articles by [ {tag} ]")
	public void filterByTag(FilterTag tag)
	{
		tag.filterBy();
		await().until(() -> tag.getName().equals(getTagFilteredBy().trim()));
	}
}
