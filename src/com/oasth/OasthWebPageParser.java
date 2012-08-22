package com.oasth;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OasthWebPageParser {
	private final static String PATTERN_LINE_STOP_POSITION_MOBILE = "<div class=\"ttl\"><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">[^<]*</div>";
	private final static String PATTERN_STOP_NAME_DESKTOP = "<option value=\"([0-9]+)\">([^<]*)</option>";
	private final static String PATTERN_LINE_NAME_DESKTOP = "<option value=\"([0-9]+)\">([^: ]*) *: *([^<]*)</option>";
	public final static String REPLACEMENT_STOP_NAME = "$1,$2";
	public final static String REPLACEMENT_LINE_NAME = REPLACEMENT_STOP_NAME;
	public final static String REPLACEMENT_LINE_STOP_POSITION = REPLACEMENT_STOP_NAME;
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

	public static String aniMarker(String a) {
		String z = a + "";
		z = z.replaceAll("(?i)grZNn", "=");
		z = decodeCoordinates(z);
		z = z.replace('!', '3');
		z = decodeCoordinates(z);
		return z;
	}

	private static String decodeCoordinates(String a) {

		String b = "";
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
			b = b + c;
			if (enc3 != 64) {
				b = b + chr2;
			}
			if (enc4 != 64) {
				b = b + chr3;
			}
		}
		b = g(b);
		return b;
	}

	private static String g(String a) {
		String b = "";
		int i = 0;
		char c;
		char c2;
		char c3;

		while (i < a.length()) {
			c = a.charAt(i);
			if (c < 128) {
				b += c;
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = a.charAt(i + 1);
				b += (char) (((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = a.charAt(i + 1);
				c3 = a.charAt(i + 2);
				b += (char) (((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return b;
	}

}