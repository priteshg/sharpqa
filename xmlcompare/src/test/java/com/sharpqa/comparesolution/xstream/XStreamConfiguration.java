package com.sharpqa.comparesolution.xstream;

import java.io.Writer;

import com.sharpqa.comparesolution.domain.ElementGroup;
import com.sharpqa.comparesolution.domain.Image;
import com.sharpqa.comparesolution.domain.ImageLink;
import com.sharpqa.comparesolution.domain.Link;
import com.sharpqa.comparesolution.domain.Module;
import com.sharpqa.comparesolution.domain.Page;
import com.sharpqa.comparesolution.domain.Section;
import com.sharpqa.comparesolution.domain.Area;
import com.sharpqa.comparesolution.domain.TextBlock;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XStreamConfiguration {
	private XStream xstream;

	public XStreamConfiguration() {
		setXStream();
	}

	public XStream getXStream() {
		return xstream;
	}

	public void setXStream() {

		// Wrap in CDATA TAGS
		// -----------------------------------------------------
		XStream xstream = new XStream(new XppDriver() {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;

					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
						cdata = (name.equals("text") || name.equals("header") || name.equals("linkText") || name.equals("href") || name.equals("src"));
					}

					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		// ------------------------------------------------------------------------------
		xstream.alias("page-section", Section.class);
		xstream.alias("module", Module.class);
		xstream.alias("area", Area.class);
		xstream.alias("element-group", ElementGroup.class);
		xstream.alias("link", Link.class);
		xstream.alias("picture", Image.class);
		xstream.alias("picture-link", ImageLink.class);
		xstream.alias("text-block", TextBlock.class);
		xstream.alias("page-map", Page.class);
		this.xstream = xstream;
	}

}
