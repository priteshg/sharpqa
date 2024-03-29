package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This class builds a list of Image Links and "Normal text links" based on a webElementSet
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class LinkBuilder {

	private List<ImageLink> imageLinks = new ArrayList<ImageLink>();
	private List<Link> links = new ArrayList<Link>();

	public LinkBuilder(WebDriver driver, WebElement component, int componentNo, String moduleType) throws InterruptedException {

		List<WebElement> elements = WebPageHelpers.createWebElementsList(driver, component, By.tagName("a"));

		// This is to cover the situation when the component is an a link tag
		// itself
		if (component.getTagName().equals("a")) {
			elements.add(0, component);
		}
		int cntImageLink = 0;
		int cntLink = 0;

		if (WebPageHelpers.testWebElementListNotEmpty(elements)) {
			for (WebElement element : elements) {
				// Test to see if there is a image
				List<WebElement> imageElements = WebPageHelpers.createWebElementsList(driver, element, By.tagName("img"));
				if (WebPageHelpers.testWebElementListNotEmpty(imageElements)) {
					ImageLink imageLink = new ImageLink(element.getAttribute("href"), imageElements.get(0).getAttribute("src"), imageElements.get(0).getAttribute("width"), imageElements.get(0)
							.getAttribute("height"));
					imageLinks.add(imageLink);
					System.out.println(cntImageLink++ + " IMAGE LINK:");
					System.out.println("Image Src:=" + imageLink.getSrc());
					System.out.println("Image href:=" + imageLink.getHref());
				} else {

					//
					String linkText = element.getText();
					String linkHref = element.getAttribute("href");
					String linkColor = element.getCssValue("color");
					// This is for those hidden links that are not displayed! -
					// getting class is sufficient
					if (linkText.trim().equals("")) {
						linkText = element.getAttribute("class");
					}

					// if still both href and link text have no value then skip
					try {
						if (!(linkText.trim().equals("") && linkHref.trim().equals(""))) {
							Link link = new Link(linkText, linkHref, linkColor);
							System.out.println(cntLink++ + " LINK:");
							System.out.println("Link Text:=[" + linkText + "],Link href:=[" + linkHref + "], ,Link color:=[" + linkColor + "]");
							// Normal text Links
							links.add(link);
						}
					} catch (Exception e) {
					}

				}
			}
		}
	}

	public List<ImageLink> getImageLinks() {
		if (imageLinks.isEmpty()) {
			return null;
		} else {
			return imageLinks;
		}
	}

	public List<Link> getLinks() {
		if (links.isEmpty()) {
			return null;
		} else {
			return links;
		}
	}

}
