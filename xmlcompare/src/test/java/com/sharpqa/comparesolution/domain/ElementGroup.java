package com.sharpqa.comparesolution.domain;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ElementGroup {
	private String type;
	private List<Link> links;
	private List<Image> images;
	private List<ImageLink> imageLinks;
	private List<TextBlock> textBlocks;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<Image> getPictures() {
		return images;
	}

	public void setPictures(List<Image> images) {
		this.images = images;
	}

	public List<ImageLink> getPictureLinks() {
		return imageLinks;
	}

	public void setPictureLinks(List<ImageLink> imageLinks) {
		this.imageLinks = imageLinks;
	}

	public List<TextBlock> getTextBLocks() {
		return textBlocks;
	}

	public void setTextBLocks(List<TextBlock> textBLocks) {
		this.textBlocks = textBLocks;
	}

	public boolean isValid() {
		
		return !(CollectionUtils.isEmpty(links) 
				&& CollectionUtils.isEmpty(images) 
				&& CollectionUtils.isEmpty(imageLinks) 
				&& CollectionUtils.isEmpty(textBlocks));
	}

}
