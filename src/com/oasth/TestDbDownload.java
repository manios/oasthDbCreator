package com.oasth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestDbDownload {
	private final static File downloadDirectory = new File("downloadOasth");
	private final static File downloadPosDirectory = new File(
			"downloadOasth/lspos");

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<BusLine> lineList = new ArrayList<BusLine>();
		String response;

		if (!downloadDirectory.exists()) {
			downloadDirectory.mkdir();
		}
		if (!downloadPosDirectory.exists()) {
			downloadPosDirectory.mkdir();
		}

		log("Downloading Greek line names..");

		response = OasthHttp.getLineNamesGreek();

		writeStringToFile(OasthWebPageParser.parseLineNames(response),
				new File(downloadDirectory, "greekLineNames.csv"));

		lineList = OasthWebPageParser.parseLineNamesToArrayList(response);

		log("parsing line names.." + lineList.size());

		log("Downloading English line names..");

		response = OasthHttp.getLineNamesEnglish();

		writeStringToFile(OasthWebPageParser.parseLineNames(response),
				new File(downloadDirectory, "englishLineNames.csv"));

		log("written English line names to file..");

		// log("Downloading stop Stop names ..");
		//
		// for (BusLine i : lineList) {
		// downloadStopNameGreek(i, 1);
		// downloadStopNameGreek(i, 2);
		// downloadStopNameEn(i, 1);
		// downloadStopNameEn(i, 2);
		// }

		long starti = System.currentTimeMillis();
		for (BusLine i : lineList) {
			downloadLineAndStopPositions(i, 1);
			downloadLineAndStopPositions(i, 2);
		}
		long endi = System.currentTimeMillis();
		log("Downloaded in: " + getDuration(starti, endi));
	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static void downloadStopNameGreek(BusLine bl, int direction)
			throws IOException {
		String fileName = String.format("stops_%d_%d_%s.txt", bl.getId(),
				direction, "gr");

		log("Downloading Stop names for: " + bl.getNumber() + " "
				+ bl.getName() + " direction:" + direction);
		String response = OasthHttp.getStopNamesGreek(bl.getId(), direction);

		log("Writing Stop names for: " + bl.getNumber() + " " + bl.getName()
				+ " direction:" + direction);

		writeStringToFile(response, new File(downloadDirectory, fileName));
	}

	public static void downloadStopNameEn(BusLine bl, int direction)
			throws IOException {
		String fileName = String.format("stops_%d_%d_%s.txt", bl.getId(),
				direction, "en");

		log("Downloading en Stop names for: " + bl.getNumber() + " "
				+ bl.getName() + " direction:" + direction);
		String response = OasthHttp.getStopNamesEnglish(bl.getId(), direction);

		log("Writing en Stop names for: " + bl.getNumber() + " " + bl.getName()
				+ " direction:" + direction);

		writeStringToFile(response, new File(downloadDirectory, fileName));
	}

	public static void downloadLineAndStopPositions(BusLine bl, int direction)
			throws IOException {
		String fileName = String.format("linestoppos_%d_%d.txt", bl.getId(),
				direction, "gr");

		log(String.format(
				"Downloading line/stop positions for: %s %s direction %d ",
				bl.getNumber(), bl.getName(), direction));

		String response = OasthHttp.getLineStopPositions(bl.getId(), direction);

		log(String.format(
				"Writing line/stop positions for: %s %s direction %d to %s",
				bl.getNumber(), bl.getName(), direction, fileName));

		writeStringToFile(response, new File(downloadPosDirectory, fileName));
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

	public static String getDuration(long startTime, long endTime) {
		long milliseconds = endTime - startTime;
		int hours = (int) milliseconds / (1000 * 60 * 60);
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
