package io.realworld.demo.pages;

import static org.awaitility.Awaitility.await;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.realworld.demo.HasLogger;
import io.realworld.demo.DriverManager;
import ru.yandex.qatools.allure.annotations.Step;

public class HomePage extends DriverManager implements HasLogger
{
	WebElement getNavLinkE()
	{
		return driver.findElement(By.cssSelector(".nav-link.active.ng-binding"));
	}

	List<WebElement> filterTagsE()
	{
		return driver.findElements(By.xpath("//div[@class='tag-list']/a[contains(@class, 'tag-default') and contains(@class, ng-bind)]"));
	}

	private List<WebElement> articlesE()
	{
		return driver.findElements(By.xpath("//div[@class ='article-preview']"));
	}

	@Step("Collecting tags available for filter")
	public List<FilterTag> getTagsAvailableForFilter()
	{
		await().atMost(20, TimeUnit.SECONDS).until(() -> !filterTagsE().isEmpty());
		return filterTagsE().stream().map(FilterTag::new).collect(Collectors.toList());
	}

	@Step("Mapping article elements to Article objects")
	public List<Article> getArticles()
	{
		return articlesE().stream().map(Article::new).collect(Collectors.toList());
	}

	public boolean isFilteredByTag()
	{
		return getNavLinkE().isDisplayed();
	}

	public String getTagFilteredBy()
	{
		return isFilteredByTag() ? getNavLinkE().getText() : "";
	}

	public void filterByTag(FilterTag tag)
	{
		tag.filterBy();
		await().until(() -> tag.getName().equals(getTagFilteredBy().trim()));
		log().info("Filtered articles by tag [ {} ]", tag);
	}
}
