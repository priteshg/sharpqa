package com.sharpqa.comparesolution.domain;

import java.util.Date;
import java.util.List;


public class Page {
	String URL;
	Date date;
	List<Area> areasOusideSection;
	List<Section> sections;



	public Page(List<Section> sections,List<Area> areasOusideSection, String URL) {
		this.areasOusideSection = areasOusideSection;
		this.sections = sections;
		this.URL = URL;
		this.date = new Date();
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setPages(List<Section> sections) {
		this.sections = sections;
	}

}

