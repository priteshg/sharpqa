package com.sharpqa.comparesolution.domain;

public class Image {
	private String src;
	private String width;
	private String height;

	public Image(String src, String width, String height) {
		this.src = src;
		this.width = width;
		this.height = height;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
