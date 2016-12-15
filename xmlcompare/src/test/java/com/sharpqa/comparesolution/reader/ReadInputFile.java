package com.sharpqa.comparesolution.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sharpqa.comparesolution.core.ConfigConstants;

public class ReadInputFile {
	private List<String> contents = new ArrayList<String>();

	public ReadInputFile() {
		setContents();
	}

	

	public List<String> getContents() {
		return contents;
	}

	public void setContents() {
		try {
			FileInputStream fileInputStream = new FileInputStream(ConfigConstants.DATAINPUT);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			String str = bufferedReader.readLine().trim();;

			try {
				while (str != null) {
					contents.add(str);
					System.out.println("HAS VALUE [" + str + "]");
					str = bufferedReader.readLine().trim();
				}
			} catch (NullPointerException ex) {
			}

			bufferedReader.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
