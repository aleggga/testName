package io.realworld.demo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.realworld.demo.utils.TextUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.yandex.qatools.allure.annotations.Description;

public class ArticlesApiTest implements HasLogger
{
	@Description("Test article filter by tags")
	@Test(description = "Pretty often one of the get calls respond with 502, that's why there are while(s)")
	public void articleTagsApiTest()
	{
		RestAssured.baseURI = Config.API_URL;
		RequestSpecification request = RestAssured.given();
		String tags = "/tags";
		String articles = "/articles";

		Response response = null;
		int responseStatusCode = 0;
		while (responseStatusCode != 200)
		{
			response = request.get(tags);
			responseStatusCode = response.statusCode();
		}
		log().info("request to [ {} ] - success", tags);

		// convert tags to ArrayList, so we can work with each tag separately
		List<String> textTags = TextUtils.csvToList(response.asString());
		// remove first element, as not tag
		textTags.remove(0);

		for (String tagName : textTags)
		{
			responseStatusCode = 0;

			while (responseStatusCode != 200)
			{
				response = request
					.queryParam("offset", "0")
					.queryParam("tag", tagName)
					.get(articles);
				responseStatusCode = response.statusCode();
				log().info("request to [ {} ] with tag [ {} ] resulted with [ {} ]", articles, tagName, responseStatusCode);
			}

			StringBuilder bld = new StringBuilder();
			Pattern MY_PATTERN = Pattern.compile("tagList\":\\[([\"\\w,\u200C]+)\\]");

			// parse response, so we have multiline string, containing tags only
			Matcher m = MY_PATTERN.matcher(response.asString());
			while (m.find())
			{
				bld.append(m.group(1).replace(",", " ") + ",");
			}
			String responseTags = responseTags = bld.toString();

			//convert string to List<String>
			List<String> articleTags = TextUtils.csvToList(responseTags);

			Assert.assertTrue(articleTags
				.stream().allMatch(tagsString -> tagsString.contains(tagName)),
				"The article doesn't contain tag [ " + tagName);
			log().info("Test for tag [ {} ] PASS", tagName);
		}
	}
}