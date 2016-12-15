package com.sharpqa.comparesolution.domain;

import java.util.ArrayList;
import java.util.List;

public class Module {
	private String name;
	private String header;
	private Link headerLink;
	private List<ElementGroup> elementGroups = new ArrayList<ElementGroup>();
	private List<ElementGroup> webElementSetComponentBeans = new ArrayList<ElementGroup>();
	//added to cater for Elements outside of a component
	
	
	public Link getHeaderLink() {
		return headerLink;
	}

	public void setHeaderLink(Link headerLink) {
		this.headerLink = headerLink;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Module(String name) {
		this.name = name;
	}

	public List<ElementGroup> getWebElementSetComponentBeans() {
		return webElementSetComponentBeans;
	}

	public void setWebElementSetComponentBeans(List<ElementGroup> webElementSetComponentBeans) {
		this.webElementSetComponentBeans = webElementSetComponentBeans;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ElementGroup> getWebElementSetBeans() {
		return elementGroups;
	}

	public void setWebElementSetBeans(List<ElementGroup> elementGroups) {
		this.elementGroups = elementGroups;
	}

	
	
	public Image getPicture(int indexOfComp, int indexOfPicture) {
		Image image = null;
		List<Image> images = null;
		if (elementGroups.size() > indexOfComp) {
			images = elementGroups.get(indexOfComp).getPictures();
			if (images.size() > indexOfPicture) {
				image = images.get(indexOfPicture);
			}
		}
		return image;
	}

	public ImageLink getPictureLink(int indexOfComp, int indexOfPictureLink) {
		ImageLink imageLink = null;
		List<ImageLink> imageLinks = null;
		if (elementGroups.size() > indexOfComp) {
			imageLinks = elementGroups.get(indexOfComp).getPictureLinks();
			if (imageLinks.size() > indexOfPictureLink) {
				imageLink = imageLinks.get(indexOfPictureLink);
			}
		}
		return imageLink;
	}

	public Link getLink(int indexOfComp, int indexOfLink) {
		Link link = null;
		List<Link> links = null;
		if (elementGroups.size() > indexOfComp) {
			links = elementGroups.get(indexOfComp).getLinks();
			if (links.size() > indexOfLink) {
				link = links.get(indexOfLink);
			}
		}
		return link;
	}

	public TextBlock getTextBlock(int indexOfComp, int indexOfTextBlock) {
		TextBlock textBlock = null;
		List<TextBlock> textBlocks = null;
		if (elementGroups.size() > indexOfComp) {
			textBlocks = elementGroups.get(indexOfComp).getTextBLocks();
			if (textBlocks.size() > indexOfTextBlock) {
				textBlock = textBlocks.get(indexOfTextBlock);
			}
		}
		return textBlock;
	}
}
