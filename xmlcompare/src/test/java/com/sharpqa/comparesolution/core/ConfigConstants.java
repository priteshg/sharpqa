package com.sharpqa.comparesolution.core;

public interface ConfigConstants {

	public static final String HUB_URL = "http://localhost:4444/wd/hub";
	public static final String DATAINPUT = "data/comparesolution/input.txt";
	public static final String[] SECTIONS = {"content-area>.primary-area", "content-area>.secondary-area","footer-links"};
	public static final String[] AREASOUTSIDESECTION = {"header-area"};
	public static final String[] SECTIONAREAS = {"primary-content.areaA","primary-content.areaB","primary-content.areaC","secondary-content","m0.grid.site-body"};
	public static final String[] COMPONENTLOCATORS = {".componentRow>.component>ul>li",".componentRow>.pinned>.component",".coponentRow>.posts>.component",".componentRow>ul>.component",".componentRow>.component",".componentRow",".component"};

}
