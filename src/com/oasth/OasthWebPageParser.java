package com.oasth;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OasthWebPageParser {
	private final static String PATTERN_LINE_STOP_POSITION_BEF_DECODING = "<div class=\"ttl\"><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">[^<]*</div>";
	private final static String PATTERN_LINE_POSITION = "<span ornt=.([^,]*),([^,]*). lbl=.[^\"]*";
	private final static String PATTERN_STOP_POSITION = "<span ornt=\"([^,]*),([^,]*)\" lbl=\"([^\"]*)\" >";

	private final static String PATTERN_STOP_NAME_DESKTOP = "<option value=\"([0-9]+)\">([^<]*)</option>";
	private final static String PATTERN_LINE_NAME_DESKTOP = "<option value=\"([0-9]+)\">([^: ]*) *: *([^<]*)</option>";
	private final static String PATTERN_STOP_NAME_AND_CODE_DIRECTION_GOING_DESKTOP = "(Μετάβαση|Going)</h3>(.*)</td> *<td";
	private final static String PATTERN_STOP_NAME_AND_CODE_DIRECTION_RETURN_DESKTOP = "(Return|Επιστροφή)</h3>(.*)</td> *</tr>";
	private final static String PATTERN_STOP_NAME_AND_CODE_DESKTOP = "fetchStasis[^0-9]([0-9]*)[^0-9]\">([^<]*)[ 	]+<strong>[^0-9]([0-9]*)[^0-9]</strong>";

	private final static String REPLACEMENT_STOP_NAME = "$1,$2";
	private final static String REPLACEMENT_LINE_NAME = "$1,$2,$3";
	private final static String REPLACEMENT_LINE_POSITION = "$2,$1";
	private final static String REPLACEMENT_STOP_POSITION = "$2,$1,$3";
	private final static String e = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	public static ArrayList<String> parse(String response, String pattern,
			String replacement) {
		ArrayList<String> bla = new ArrayList<String>();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(pattern).matcher(response);

		// Check all occurance
		while (matcher.find()) {
			bla.add(matcher.group().replaceAll(pattern, replacement));
		}
		return bla;
	}

	public static String parseS(String response, String pattern,
			String replacement) {
		StringBuilder bla = new StringBuilder();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(pattern).matcher(response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(matcher.group().replaceAll(pattern, replacement))
					.append("\n");
		}
		return bla.toString();
	}

	public static String parseLineNames(String response) {
		StringBuilder bla = new StringBuilder();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(PATTERN_LINE_NAME_DESKTOP).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {

			bla.append(
					matcher.group().replaceAll(PATTERN_LINE_NAME_DESKTOP,
							REPLACEMENT_LINE_NAME)).append("\n");
		}

		int firstNline = bla.indexOf("\n");
		bla.replace(0, firstNline + 1, "");

		return bla.toString();
	}

	public static ArrayList<BusLine> parseLineNamesToArrayList(String response) {
		ArrayList<BusLine> bli = new ArrayList<BusLine>();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(PATTERN_LINE_NAME_DESKTOP).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {
			bli.add(new BusLine(matcher.group().replaceAll(
					PATTERN_LINE_NAME_DESKTOP, REPLACEMENT_LINE_NAME)));
		}
		bli.remove(0);
		return bli;
	}

	public static String parseStopNames(String response, int lineId,
			int direction, String language) {
		StringBuilder bla = new StringBuilder();
		int sorder = 1;

		final String linePrefix = lineId + "," + direction + "," + language
				+ ",";

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(PATTERN_STOP_NAME_DESKTOP).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(sorder++)
					.append(',')
					.append(linePrefix)
					.append(matcher.group().replaceAll(
							PATTERN_STOP_NAME_DESKTOP, REPLACEMENT_STOP_NAME))
					.append('\n');
		}

		return bla.toString();
	}

	public static String parseStopNamesAndCodes(String response, int lineId,
			int direction, String language) {
		StringBuilder bla = new StringBuilder();
		int sorder = 1;

		final String linePrefix = lineId + "," + direction + "," + language
				+ ",";
		String regExPattern = PATTERN_STOP_NAME_AND_CODE_DIRECTION_GOING_DESKTOP;

		if (direction == 2) {
			regExPattern = PATTERN_STOP_NAME_AND_CODE_DIRECTION_RETURN_DESKTOP;

		}
		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(regExPattern).matcher(response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(matcher.group().replaceAll(regExPattern, "$2")).append(
					'\n');
		}

		response = bla.toString();
		bla.delete(0, bla.length());

		matcher = Pattern.compile(PATTERN_STOP_NAME_AND_CODE_DESKTOP).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(sorder++)
					.append(',')
					.append(linePrefix)
					.append(matcher.group().replaceAll(
							PATTERN_STOP_NAME_AND_CODE_DESKTOP, "$1,$2,$3"))
					.append('\n');
		}

		return bla.toString();
	}

	/**
	 * Parses stop position return String and returns a string in this
	 * format:</br>
	 * 
	 * <pre>
	 * 1,lineId,direction,latitude,longtitude,stopNameGreek\n
	 * 2,...\n
	 * 3,...\n
	 * n,lineId,direction,latitude,longtitude,stopNameGreek
	 * </pre>
	 * 
	 * @param response
	 *            the decoded response string
	 * @param lineId
	 *            bus line Id
	 * @param direction
	 *            1:going,2:return
	 */
	private static String parseDecodedStopPositions(String response,
			int lineId, int direction) {
		StringBuilder bla = new StringBuilder();
		int sorder = 1;
		final String linePrefix = lineId + "," + direction + ",";

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(PATTERN_STOP_POSITION).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(sorder++)
					.append(',')
					.append(linePrefix)
					.append(matcher.group().replaceAll(PATTERN_STOP_POSITION,
							REPLACEMENT_STOP_POSITION)).append('\n');
		}

		return bla.toString();
	}

	/**
	 * Parses bus line route position markers String and returns a string in
	 * this format:</br>
	 * 
	 * <pre>
	 * 1,lineId,direction,latitude,longtitude\n
	 * 2,...\n
	 * 3,...\n
	 * n,lineId,direction,latitude,longtitude
	 * </pre>
	 * 
	 * @param response
	 *            the decoded response string
	 * @param lineId
	 *            bus line Id
	 * @param direction
	 *            1:going,2:return
	 */
	private static String parseDecodedLinePositions(String response,
			int lineId, int direction) {
		StringBuilder bla = new StringBuilder();
		int sorder = 1;
		final String linePrefix = lineId + "," + direction + ",";

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(PATTERN_LINE_POSITION).matcher(
				response);

		// Check all occurance
		while (matcher.find()) {
			bla.append(sorder++)
					.append(',')
					.append(linePrefix)
					.append(matcher.group().replaceAll(PATTERN_LINE_POSITION,
							REPLACEMENT_LINE_POSITION)).append('\n');
		}

		return bla.toString();
	}

	public static String parseLinePositions(String response, int lineId,
			int direction) {
		StringBuilder linePos = new StringBuilder();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(
				PATTERN_LINE_STOP_POSITION_BEF_DECODING).matcher(response);

		// Check all occurance
		while (matcher.find()) {
			linePos.append(aniMarker(matcher.group().replaceAll(
					PATTERN_LINE_STOP_POSITION_BEF_DECODING, "$1")));
		}

		return parseDecodedLinePositions(linePos.toString(), lineId, direction);

	}

	public static String parseStopPositions(String response, int lineId,
			int direction) {
		StringBuilder stopPos = new StringBuilder();

		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = Pattern.compile(
				PATTERN_LINE_STOP_POSITION_BEF_DECODING).matcher(response);

		// Check all occurance
		while (matcher.find()) {
			stopPos.append(aniMarker(matcher.group().replaceAll(
					PATTERN_LINE_STOP_POSITION_BEF_DECODING, "$2")));
		}

		return parseDecodedStopPositions(stopPos.toString(), lineId, direction);

	}

	public static String aniMarker(String a) {
		String z = a + "";
		z = z.replaceAll("(?i)grZNn", "=");
		z = decodeCoordinates(z);
		z = z.replace('!', '3');
		z = decodeCoordinates(z);
		return z;
	}

	private static String decodeCoordinates(String a) {

		StringBuilder b = new StringBuilder("");
		char c, chr2, chr3;
		int d, enc2, enc3, enc4;
		int i = 0;
		a = a.replaceAll("[^A-Za-z0-9+/=]", "");
		while (i < a.length()) {
			d = e.indexOf(a.charAt(i++));
			enc2 = e.indexOf(a.charAt(i++));
			enc3 = e.indexOf(a.charAt(i++));
			enc4 = e.indexOf(a.charAt(i++));
			c = (char) ((d << 2) | (enc2 >> 4));
			chr2 = (char) (((enc2 & 15) << 4) | (enc3 >> 2));
			chr3 = (char) (((enc3 & 3) << 6) | enc4);
			b.append(c);
			if (enc3 != 64) {
				b.append(chr2);
			}
			if (enc4 != 64) {
				b.append(chr3);
			}
		}
		b = g(b);
		return b.toString();
	}

	private static StringBuilder g(StringBuilder a) {
		StringBuilder b = new StringBuilder("");
		int i = 0;
		char c;
		char c2;
		char c3;

		while (i < a.length()) {
			c = a.charAt(i);
			if (c < 128) {
				b.append(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = a.charAt(i + 1);
				b.append((char) (((c & 31) << 6) | (c2 & 63)));
				i += 2;
			} else {
				c2 = a.charAt(i + 1);
				c3 = a.charAt(i + 2);
				b.append((char) (((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
				i += 3;
			}
		}
		return b;
	}

}
