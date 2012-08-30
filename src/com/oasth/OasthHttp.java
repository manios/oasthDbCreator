package com.oasth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;

// Git problems with Egit
// http://stackoverflow.com/questions/3601805/auth-problem-with-egit-and-github
// http://stackoverflow.com/questions/8820668/the-current-branch-is-not-configured-for-pull-no-value-for-key-branch-master-mer

public class OasthHttp {
	private final static String TAG = "OasthHttp";
	private final static char[] directionLetters = { 'a', 'b' };
	private final static StringBuilder sbu = new StringBuilder();

	private final static String LINE_NAME_URL_GR = "http://oasth.gr/tools/busTimes.php";
	private final static String LINE_NAME_URL_EN = "http://oasth.gr/tools/busTimes_eng.php";
	private final static String STOP_NAME_URL_GR = "http://oasth.gr/tools/lineStop.php";
	private final static String STOP_NAME_URL_EN = "http://oasth.gr/tools/lineStop_eng.php";
	private final static String LINE_AND_STOP_POSITIONS_URL_GR = "http://nm.oasth.gr/index.php?md=6&sn=3&line=%d&dir=%c";

	private final static long BYTES_TO_SKIP_LINE_NAMES = 0;
	private final static long BYTES_TO_SKIP_STOP_NAMES = 15037;

	private final static String STOP_NAME_POST_PARAMS = "bline=%d&goes=%c&lineStops=";

	public static String getLineNamesGreek() throws IOException {
		return getLineOrStopNames(STOP_NAME_URL_GR, 67, 1,
				BYTES_TO_SKIP_LINE_NAMES);
	}

	public static String getLineNamesEnglish() throws IOException {
		return getLineOrStopNames(STOP_NAME_URL_EN, 67, 1,
				BYTES_TO_SKIP_LINE_NAMES);
	}

	public static String getStopNamesGreek(int lineId, int direction)
			throws IOException {
		return getLineOrStopNames(STOP_NAME_URL_GR, lineId, direction,
				BYTES_TO_SKIP_STOP_NAMES);
	}

	public static String getStopNamesEnglish(int lineId, int direction)
			throws IOException {
		return getLineOrStopNames(STOP_NAME_URL_EN, lineId, direction,
				BYTES_TO_SKIP_STOP_NAMES);
	}

	public static String getLineStopPositions(int lineId, int direction)
			throws IOException {
		return getLineAndStopPositions(LINE_AND_STOP_POSITIONS_URL_GR, lineId,
				direction);
	}

	private static String getLineOrStopNames(String urlString, int lineId,
			int direction, long bytesToSkip) throws IOException {
		cleanSbu();

		// long startTime0 = System.currentTimeMillis();

		URL oracle = new URL(urlString);
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		if (urlString.equals(STOP_NAME_URL_EN)
				|| urlString.equals(LINE_NAME_URL_EN)) {
			populateDesktopHttpHeaders(yc, LINE_NAME_URL_EN, false);
		} else {
			populateDesktopHttpHeaders(yc, LINE_NAME_URL_GR, false);
		}

		yc.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(yc.getOutputStream());
		wr.write(String.format(STOP_NAME_POST_PARAMS, lineId,
				directionLetters[direction - 1]));
		wr.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF8"));
		String inputLine;

		in.skip(bytesToSkip);
		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);

		wr.close();
		in.close();

		// long endTime0 = System.currentTimeMillis();
		// Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

		return sbu.toString();

	}

	private static String getLineAndStopPositions(String urlString, int lineId,
			int direction) throws IOException {
		cleanSbu();

		// long startTime0 = System.currentTimeMillis();

		URL oracle = new URL(String.format(urlString, lineId, direction));
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		if (urlString.equals(STOP_NAME_URL_EN)) {
			populateMobileHttpHeaders(yc, LINE_NAME_URL_EN, false);
		} else {
			populateMobileHttpHeaders(yc, LINE_NAME_URL_GR, false);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF8"));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);
		in.close();

		// long endTime0 = System.currentTimeMillis();
		// Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

		return sbu.toString();
	}

	private static void populateDesktopHttpHeaders(URLConnection urlCon,
			final String referer, boolean isAjax) {
		urlCon.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux i686; rv:2.0b12) Gecko/20110222 Firefox/4.0b12");
		urlCon.setRequestProperty("Referer", referer);
		urlCon.setRequestProperty("Accept-Language",
				"el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3");
		urlCon.setRequestProperty("Accept-Charset",
				"ISO-8859-7,utf-8;q=0.7,*;q=0.7");

		if (isAjax) {
			urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		}
	}

	private static void populateMobileHttpHeaders(URLConnection urlCon,
			final String referer, boolean isAjax) {
		urlCon.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
		urlCon.setRequestProperty("Referer", referer);
		urlCon.setRequestProperty("Accept-Language",
				"el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3");
		urlCon.setRequestProperty("Accept-Charset",
				"ISO-8859-7,utf-8;q=0.7,*;q=0.7");

		if (isAjax) {
			urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		}
	}

	private static void cleanSbu() {
		sbu.delete(0, sbu.length());
	}
}
