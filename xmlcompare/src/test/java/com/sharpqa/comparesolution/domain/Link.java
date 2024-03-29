package com.sharpqa.comparesolution.domain;

public class Link {
	private String linkText;
	private String href;
	private String color;

	public Link(String linkText, String href, String color) {
		this.linkText = linkText;
		this.href = href;
		this.color = color;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

}
