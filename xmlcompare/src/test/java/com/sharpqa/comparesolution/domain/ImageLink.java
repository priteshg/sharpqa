package com.sharpqa.comparesolution.domain;

public class ImageLink extends Image{
	private String href;

	public ImageLink(String href, String src, String width, String height) {
		super(src, width, height);
		this.href = href;
	}

	public String getHref() {
		return href;
	}

}
