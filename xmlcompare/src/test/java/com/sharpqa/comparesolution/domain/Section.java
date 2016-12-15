package com.sharpqa.comparesolution.domain;

import java.util.List;

public class Section {
	private String pageSectionName;
	private List<Area> areas;

	public Section(String pageSectionName, List<Area> areas) {
		this.pageSectionName = pageSectionName;
		this.areas = areas;

	}

	public String getPageSectionName() {
		return pageSectionName;
	}

	public void setPageSectionName(String pageSectionName) {
		this.pageSectionName = pageSectionName;
	}

	public List<Area> getSectionAreaBeans() {
		return areas;
	}

	public void setSectionAreaBean(List<Area> areas) {
		this.areas = areas;
	}

}
