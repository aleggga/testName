package io.realworld.demo;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.realworld.demo.pages.Article;
import io.realworld.demo.pages.FilterTag;
import io.realworld.demo.pages.HomePage;
import ru.yandex.qatools.allure.annotations.Description;

public class ArticleTagsTest extends TestBase
{
	HomePage homePage = new HomePage();

	public ArticleTagsTest() {
		super();
	}

	@Description("Test article filter by tags")
	@Test(priority = 1)
	public void verifyHomePageTitleTest(){
		List<Article> articles;
		List<FilterTag> tags = homePage.getTagsAvailableForFilter();

		// Remove empty tags and tags with specialChars from the list
		tags.removeIf(tag -> "".equals(tag.getName().replaceAll("\u200C+", "")));

		for (FilterTag tag : tags)
		{
			String tagName = tag.getName();
			homePage.filterByTag(tag);
			articles = homePage.getArticles();

			for (Article article : articles)
			{
				Assert.assertTrue(article.getTags().contains(tagName));
			}
		}
	}
}
