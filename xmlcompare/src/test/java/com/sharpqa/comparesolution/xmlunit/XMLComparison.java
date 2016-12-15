package com.sharpqa.comparesolution.xmlunit;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.Node;

public class XMLComparison {

	private boolean testFailed;
	private StringBuilder message;
	private String outputMessage;
	public static final String[] EXCEPTION_NODES = { "date", "URL" };
	private String urlPattern = "(?i)(http://.*?)(.+?)(.channel4.com)";

	public XMLComparison(FileReader xmlExpected, FileReader xmlActual) throws Exception {

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalize(true);
		message = new StringBuilder();
		testFailed = false;

		Diff diff = new Diff(xmlExpected, xmlActual);
		DetailedDiff detDiff = new DetailedDiff(diff);
		@SuppressWarnings("unchecked")
		List<Difference> differences = detDiff.getAllDifferences();

		for (Object object : differences) {
			Difference difference = (Difference) object;

			try{
				String nodeName = getParentNodeName(difference.getControlNodeDetail().getNode());
				if (!Arrays.asList(EXCEPTION_NODES).contains(nodeName)) {

					String controlValue = difference.getControlNodeDetail().getValue();
					String testValue = difference.getTestNodeDetail().getValue();

					if (!skipURLEnvironmentDifferences(controlValue, testValue)) {

						String moduleName = getModuleNodeName(difference.getControlNodeDetail().getNode());
						System.out.println("Module:[" + moduleName + "]***********************");
						System.out.println(difference.toString());
						System.out.println("***********************");
						message.append("\nModule:[" + moduleName + "]***********************\n" + difference.toString() + "\n***********************\n");

						testFailed = true;
					}
				}
			
			}
			catch(Exception e){
				System.out.println("Module:[unknown]***********************");
				System.out.println(difference.toString());
				System.out.println("***********************");
				message.append("\nModule:[unknown]***********************\n" + difference.toString() + "\n***********************\n");
			}
		}

		outputMessage = message.toString().replace("at /page-map", "\nat /page-map");
		outputMessage = outputMessage.replace("' but was '", "' \nbut was '");
	}

	private boolean skipURLEnvironmentDifferences(String controlValue,String testValue){
		testValue = testValue.replaceAll(urlPattern, "ENVIRONMENT");
		controlValue = controlValue.replaceAll(urlPattern, "ENVIRONMENT");
		if(testValue.equals(controlValue)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isTestFailed() {
		return testFailed;
	}

	public String getOutputMessage() {
		return outputMessage;
	}

	private String getModuleNodeName(Node node) {
		int i;
		String moduleName = "";
		for (i = 0; i < 10; i++) {
			node = node.getParentNode();
			// This is the module node name
			if (node.getNodeName().equals("module")) {
				moduleName = node.getFirstChild().getTextContent();
				break;
				// this is the root node name
			} else if (node.getNodeName().equals("page-map")) {
				break;
			}
		}
		return moduleName;
	}

	private String getParentNodeName(Node node) {
		if (!node.getNodeName().equals("page-map")) {
			node = node.getParentNode();
		}
		return node.getNodeName();
	}

}