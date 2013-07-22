package com.manios.oasthdbcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {
	public static String readTextFile(File f) throws IOException {
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
		writeStringToFile(stout, f, false);

	}

	public static void appendStringToFile(String stout, File f)
			throws IOException {
		writeStringToFile(stout, f, true);
	}

	public static void writeStringToFile(String stout, File f, boolean isAppend)
			throws IOException {
		Writer out = new OutputStreamWriter(new FileOutputStream(f, isAppend),
				"UTF8");
		out.write(stout);
		out.close();

	}
}
