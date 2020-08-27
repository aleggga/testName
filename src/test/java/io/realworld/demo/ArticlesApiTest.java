package io.realworld.demo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.realworld.demo.utils.TextUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ArticlesApiTest implements HasLogger
{
	@Description("Test article filter by tags")
	@Test
	public void articleTagsApiTest()
	{
		RestAssured.baseURI = Config.API_URL;
		RequestSpecification request = RestAssured.given();
		String tags = "/tags";
		String articles = "/articles";

		Response response = request.get(tags);
		response.then().assertThat().statusCode(HttpStatus.SC_OK);
		log().info("request to [ {} ] - success", tags);

		// convert string to ArrayList, so we can work with each line
		List<String> textTags = TextUtils.csvToList(response.asString());
		// remove first element, as not tag
		textTags.remove(0);


		for (String tagName : textTags)
		{
			response = request
				.queryParam("offset", "0")
				.queryParam("tag", tagName)
				.get(articles);
			response.then().assertThat().statusCode(HttpStatus.SC_OK);
			log().info("request to [ {} ] - success", articles);


			String responseTags = "";
			Pattern MY_PATTERN = Pattern.compile("tagList\":\\[([\"\\w,\u200C]+)\\]");

			// parse response, so we have multiline string, containing tags only
			Matcher m = MY_PATTERN.matcher(response.asString());
			while (m.find())
			{
				responseTags = (new StringBuilder()).append(m.group(1)).append("\n").toString();
			}

			Assert.assertEquals(response.asString().contains(tagName), true);
		}
	}
}
