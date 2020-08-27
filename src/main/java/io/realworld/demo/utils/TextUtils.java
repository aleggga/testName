package io.realworld.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUtils
{
	/**
	 * separates string by coma and places under List
	 * @param line - String of csv
	 * @return List of strings
	 */
	public static List<String> csvToList(String line)
	{
		List<String> values = new ArrayList<>();
		try (Scanner rowScanner = new Scanner(line))
		{
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext())
			{
				values.add(rowScanner.next());
			}
		}
		return values;
	}
}