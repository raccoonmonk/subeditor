package ru.raccoonmonk.android_subeditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;

/**
 * Class {@code IOFiles} implements work with files.
 */
public class IOFiles {
	/**
	 * Reads from {@code file} and creates ArrayList.
	 * 
	 * @param file
	 *            the file to parse
	 * @return ArrayList of Records
	 */
	public static ArrayList<SubRecord> readFileSubRip(File file) {
		ArrayList<SubRecord> arr = new ArrayList<SubRecord>();
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			//CharsetDecoder cd;
			//cd.detectedCharset()
			
			String line = null;
			String[] ar = new String[2];
			int count = 0;
			StringBuilder sb2 = new StringBuilder(0);
			while ((line = reader.readLine()) != null) {
				if (count < 2) {
					if (line.equals(""))
						continue;
					ar[count] = line;
					++count;
				} else {
					if (line.equals("")) {
						count = 0;
						arr.add(new SubRecord(ar[0], ar[1], sb2.toString()
								.trim()));
						sb2.setLength(0);
					} else {
						sb2.append(line);
						sb2.append("\n");
					}
				}
			}
			reader.close();
			fis.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return arr;

	}

	/**
	 * Saves ArrayList to a {@code file}
	 * 
	 * @param file
	 *            Destination file
	 * @param arr
	 *            ArrayList
	 */
	public static void writeFileSubRip(File file, ArrayList<SubRecord> arr) {

		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos));
			for (SubRecord sbRec : arr) {
				writer.append(sbRec.num.trim());
				writer.append("\r\n");
				writer.append(sbRec.time.trim());
				writer.append("\r\n");
				writer.append(sbRec.text.trim());
				writer.append("\r\n");
				writer.append("\r\n");
			}
			writer.close();
			fos.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}
}
