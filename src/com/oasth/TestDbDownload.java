package com.oasth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestDbDownload {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<BusLine> lineList = new ArrayList<BusLine>();
		String response;

		File downloadDirectory = new File("downloadOasth");		
		if(!downloadDirectory.exists()){
			downloadDirectory.mkdir();
		}
		
		log("Downloading Greek line names..");

		response = OasthHttp.getLineNamesGreek();

		writeStringToFile(OasthWebPageParser.parseLineNames(response),
				new File(downloadDirectory,"greekLineNames.csv"));

		lineList = OasthWebPageParser.parseLineNamesToArrayList(response);

		log("parsing line names.." + lineList.size());

		log("Downloading English line names..");

		response = OasthHttp.getLineNamesEnglish();

		writeStringToFile(OasthWebPageParser.parseLineNames(response),
				new File(downloadDirectory,"englishLineNames.csv"));

		log("written English line names to file..");

//		log("Downloading stop Greek Stop names ..");
//		for (BusLine i : lineList) {
//			log("Downloading Stop names for: " + i.getNumber() + " "
//					+ i.getName());
//			response = OasthHttp.getStopNamesGreek(i.getId(), 1);
//
//			
//		}

	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static String readFile(File f) throws IOException {
		StringBuilder inpLine = new StringBuilder();

		BufferedReader bf = new BufferedReader(new FileReader(f));
		String curLine;

		while ((curLine = bf.readLine()) != null) {
			inpLine.append(curLine);
		}
		bf.close();
		return inpLine.toString();
	}

	public static void writeStringToFile(String stout, File f)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write(stout);

		writer.close();

	}
}
