package com.sharpqa.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GeneralHelper {

	public static String getEnvironmentFromURL(String URL) {

		Pattern p = Pattern.compile("\\/\\/(.*?)\\.");
		Matcher m = p.matcher(URL);
		String environment = "";
		if (m.find()) {
			environment = m.group(1);
		}else{
			throw new IllegalArgumentException("URL["+URL+"] is invalid - contains no environment");
		}

		return environment;
	}

}
