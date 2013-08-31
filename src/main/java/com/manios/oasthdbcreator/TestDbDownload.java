package com.manios.oasthdbcreator;

import com.manios.oasthdbcreator.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TestDbDownload {
	/** directory in which we store parsed csv data */
	private final static File parsedDirectory = new File("downloadOasth");
	/** directory in which we store downloaded raw html data */
	private final static File downloadDirectory = new File("downloadOasth");
	private final static File parsedPosDirectory = new File("parsedOasth"
			+ File.separatorChar + "lspos");
	private final static File downloadPosDirectory = new File("downloadOasth"
			+ File.separatorChar + "lspos");

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

		FileUtils.writeStringToFile(
				OasthWebPageParser.parseLineNames(response), new File(
						downloadDirectory, "greekLineNames.csv"));

		lineList = OasthWebPageParser.parseLineNamesToArrayList(response);

		log("parsing line names.." + lineList.size());

		log("Downloading English line names..");

		response = OasthHttp.getLineNamesEnglish();

		FileUtils.writeStringToFile(
				OasthWebPageParser.parseLineNames(response), new File(
						downloadDirectory, "englishLineNames.csv"));

		log("written English line names to file..");

		// log("Downloading stop Stop names ..");
		//
		// for (BusLine i : lineList) {
		// downloadStopNameGreek(i, 1);
		// downloadStopNameGreek(i, 2);
		// downloadStopNameEn(i, 1);
		// downloadStopNameEn(i, 2);
		// }

		// long starti = System.currentTimeMillis();
		// for (BusLine i : lineList) {
		// downloadLineAndStopPositions(i, 1);
		// downloadLineAndStopPositions(i, 2);
		// }
		// long endi = System.currentTimeMillis();
		// log("Downloaded in: " + getDuration(starti, endi));

		// parse all stop names
		// parseStopNames(lineList);

		parseLineAndStopPositions(lineList, 1);
		parseLineAndStopPositions(lineList, 2);

	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static void parseStopNames(ArrayList<BusLine> bl) throws IOException {
		parseStopNames(bl, 1, "gr");
		parseStopNames(bl, 2, "gr");
		parseStopNames(bl, 1, "en");
		parseStopNames(bl, 2, "en");
	}

	public static void parseStopNames(ArrayList<BusLine> bl, int direction,
			String language) throws IOException {
		final String inputFileName = "stops_%d_%d_%s.txt";
		final String outputFileName = "stopNames_%s.csv";
		int curcount = 0;
		int totalcount = bl.size();
		String response;
		File inFile;
		File outFile = new File(parsedDirectory, String.format(outputFileName,
				language));

		for (BusLine i : bl) {
			curcount++;

			final String log_parsing = curcount + " from " + totalcount
					+ "  parsing " + language + " stop names for: "
					+ i.getNumber() + "  : " + i.getName() + "  , direction : "
					+ direction;

			log(log_parsing);

			inFile = new File(downloadDirectory, String.format(inputFileName,
					i.getId(), direction, language));

			if (!inFile.exists()) {
				log(String.format("file %s not found!", inFile.getName()));
				continue;
			}

			response = FileUtils.readTextFile(inFile);

			response = OasthWebPageParser.parseStopNames(response, i.getId(),
					direction, language);

			FileUtils.appendStringToFile(response, outFile);

		}

	}

	public static void parseLineAndStopPositions(ArrayList<BusLine> bl,
			int direction) throws IOException {
		final String inputFileName = "linestoppos_%d_%d.txt";
		final String outputStopFileName = "stopPositions.csv";
		final String outputLineFileName = "linePositions.csv";

		int curcount = 0;
		int totalcount = bl.size();
		String response;
		String linePositions = null;
		String stopPositions = null;

		File inFile;
		File lineOutFile = new File(parsedDirectory, outputLineFileName);
		File stopOutFile = new File(parsedDirectory, outputStopFileName);

		for (BusLine i : bl) {
			curcount++;

			final String log_parsing = curcount + " from " + totalcount
					+ "  parsing stop positions for: " + i.getNumber() + "  : "
					+ i.getName() + "  , direction : " + direction;

			log(log_parsing);

			inFile = new File(downloadPosDirectory, String.format(
					inputFileName, i.getId(), direction));

			if (!inFile.exists()) {
				log(String.format("file %s not found!", inFile.getName()));
				continue;
			}

			response = FileUtils.readTextFile(inFile);

			linePositions = OasthWebPageParser.parseLinePositions(response,
					i.getId(), direction);
			stopPositions = OasthWebPageParser.parseStopPositions(response,
					i.getId(), direction);

			FileUtils.appendStringToFile(linePositions, lineOutFile);
			FileUtils.appendStringToFile(stopPositions.toString(), stopOutFile);

		}
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

		FileUtils.writeStringToFile(response, new File(downloadDirectory,
				fileName));
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

		FileUtils.writeStringToFile(response, new File(downloadDirectory,
				fileName));
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

		FileUtils.writeStringToFile(response, new File(downloadPosDirectory,
				fileName));
	}

	public static String getDuration(long startTime, long endTime) {
		long milliseconds = endTime - startTime;
		int hours = (int) milliseconds / (1000 * 60 * 60);
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
