package com.sharpqa.unittests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class UnitTests {

	@Test
	public void testRegEx(){
		String url = "http://test.sharpqa.com/news/refresh-gallery-1,gallery";
		
		
		Pattern p = Pattern.compile("\\/\\/(.*?)\\.");
		Matcher m = p.matcher(url);
		
		if (m.find()) {
		    System.out.println(m.group(1));
		}
		
	
	}
	
	
}
