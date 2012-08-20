package com.oasth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;

// Git problems
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
	private final static String STOP_NAME_POST_PARAMS = "bline=%d&goes=%c&lineStops=";

	public static String getLineArrival(String urlString,int lineId, int direction)
			throws IOException {
		long startTime0 = System.currentTimeMillis();

		cleanSbu();

		URL oracle = new URL(urlString);
		URLConnection yc = oracle.openConnection();

		populateDesktopHttpHeaders(yc, false);

		yc.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(yc.getOutputStream());
		wr.write(String.format(STOP_NAME_POST_PARAMS, lineId,
				directionLetters[direction]));
		wr.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		in.skip(31000);
		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);

		wr.close();
		in.close();

		long endTime0 = System.currentTimeMillis();
		// Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

		return sbu.toString();
	}

	public static String getLinesArrival(int lineId, int direction, int stopId)
			throws IOException {
		cleanSbu();

		// long startTime0 = System.currentTimeMillis();

		URL oracle = new URL("http://oasth.gr/tools/lineTimes.php");
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		populateDesktopHttpHeaders(yc, false);

		yc.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(yc.getOutputStream());

		String parames = "bline=" + lineId + "&goes="
				+ directionLetters[direction - 1] + "&lineStops=" + stopId;

		wr.write(parames);
		wr.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		in.skip(31000);
		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);

		wr.close();
		in.close();

		// long endTime0 = System.currentTimeMillis();
		// Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

		return sbu.toString();

	}

	public static String getMobileLineArrival(int lineId, int direction,
			int stopId) throws IOException {
		cleanSbu();

		URL oracle = new URL("http://nm.oasth.gr/index.php?md=3&sn=3&start="
				+ stopId + "&line=" + lineId + "&dir=" + direction);
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		populateMobileHttpHeaders(yc, true);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);
		in.close();

		return sbu.toString();
	}

	public static String getMobileLinesArrival(int lineId, int direction,
			int stopId) throws IOException {
		cleanSbu();

		URL oracle = new URL("http://nm.oasth.gr/index.php?md=4&sn=4&stp="
				+ stopId + "&dir=" + direction);
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		populateMobileHttpHeaders(yc, true);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);
		in.close();

		return sbu.toString();
	}

	public static String getMobileTextualPosition(int lineId, int direction)
			throws IOException {
		cleanSbu();

		URL oracle = new URL("http://nm.oasth.gr/index.php?md=6&sn=3&line="
				+ lineId + "&dir=" + direction);
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		populateMobileHttpHeaders(yc, true);
		yc.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Linux; U; Android 1.6; en-us; SonyEricssonU20a Build/R1X) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);
		in.close();

		return sbu.toString();
	}

	public static String getMobilePosition(int lineId, int direction)
			throws IOException {
		cleanSbu();

		URL oracle = new URL(
				"http://nm.oasth.gr/index.php?md=6&sn=4&ref=1&line=" + lineId
						+ "&dir=" + direction);
		HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

		populateMobileHttpHeaders(yc, true);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			sbu.append(inputLine);
		in.close();

		return sbu.toString();
	}

	private static void populateDesktopHttpHeaders(URLConnection urlCon,
			boolean isAjax) {
		urlCon.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux i686; rv:2.0b12) Gecko/20110222 Firefox/4.0b12");
		urlCon.setRequestProperty("Referer",
				"http://oasth.gr/tools/lineTimes.php");
		urlCon.setRequestProperty("Accept-Language",
				"el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3");
		urlCon.setRequestProperty("Accept-Charset",
				"ISO-8859-7,utf-8;q=0.7,*;q=0.7");

		if (isAjax) {
			urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		}
	}

	private static void populateMobileHttpHeaders(URLConnection urlCon,
			boolean isAjax) {
		urlCon.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
		urlCon.setRequestProperty("Referer", "http://nm.oasth.gr/");
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
