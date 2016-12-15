package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This class builds a Text block Bean, the text blocks contained within a WebElement Set belonging to a Module
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 */

public class TextBlockBuilder {
	private static final String[] textTags = { "p", "h2", "h3", "h1", "span" }; 	// List of text tags


	public static List<TextBlock> getTextElements(WebDriver driver, WebElement component) {
		List<WebElement> elements = new ArrayList<WebElement>();
		List<TextBlock> textBlocks = new ArrayList<TextBlock>();

		for (String tag : textTags) {
			elements.addAll(WebPageHelpers.createWebElementsList(driver, component, By.tagName(tag)));
			// If the actual component is a text tag add it to the beginning of
			if (component.getTagName().equals(tag)) {
				elements.add(0, component);
			}
		}

		int cntText = 0;
		if (WebPageHelpers.testWebElementListNotEmpty(elements)) {
			for (WebElement element : elements) {
				TextBlock textBlock = new TextBlock();
				String text = element.getText();
				if (text.equals("")) {
					text = element.getAttribute("class");
				}
				if(!(text.equals(""))){
				textBlock.setText(text);
				textBlock.setTag(element.getTagName());
				textBlocks.add(textBlock);
				System.out.println(cntText++ + " Text Element:");
				System.out.println("Text [" + textBlock.getTag() + "]:=[" + textBlock.getText() + "]");
				}
			}

		}

		if (textBlocks.isEmpty()) {
			return null;
		} else {
			return textBlocks;
		}
	}
}
