package com.integrosys.cms.batch.common.filereader;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * CSV is a delimited data format that has fields/columns separated by the comma
 * character and records/rows separated by newlines. Fields that contain a
 * special character ( comma, newline, or double quote ), must be enclosed in
 * double quotes. However, if a line contains a single entry which is the empty
 * string, it may be enclosed in double quotes. If a field's value contains a
 * double quote character it is escaped by placing another double quote
 * character next to it. The CSV file format does not require a specific
 * character encoding , byte order, or line terminator format.
 * 
 * @author allen
 * 
 */
public class CSVTokenizer implements Enumeration {

	public final static char DEFAULT_DELIMTER = ',';

	private char delimiter;

	private String text;

	private int currentPosition;

	private int maxPosition;

	/**
	 * @param line
	 */
	public CSVTokenizer(String line) {
		text = line;
		currentPosition = 0;
		maxPosition = line.length();
		delimiter = DEFAULT_DELIMTER;
	}

	/**
	 * @param line
	 * @param delimeter
	 */
	public CSVTokenizer(String line, char delimeter) {
		text = line;
		currentPosition = 0;
		maxPosition = line.length();
		this.delimiter = delimeter;
	}

	/**
	 * Only Single Character delimiter is allowed
	 * @param line
	 * @param delimiter
	 */
	public CSVTokenizer(String line, String delimiter) {

		if (delimiter.length() != 1) {
			throw new IllegalArgumentException("Delimiter must be a single character");
		}

		text = line;
		currentPosition = 0;
		maxPosition = line.length();

		this.delimiter = delimiter.charAt(0);
	}

	/**
	 * @param ind
	 * @return
	 */
	private int nextDelimiter(int ind) {
		boolean inquote = false;
		while (ind < maxPosition) {
			char ch = text.charAt(ind);
			if (!inquote && (ch == delimiter)) {
				break;
			}
			else if ('"' == ch) {
				inquote = !inquote;
			}
			ind++;
		}
		return ind;
	}

	public int countTokens() {
		int i = 0;
		int ret = 1;
		while ((i = nextDelimiter(i)) < maxPosition) {
			i++;
			ret++;
		}
		return ret;
	}

	/**
	 * Return next available token
	 * @return
	 */
	public String nextToken() {

		if (currentPosition > maxPosition) {
			throw new NoSuchElementException(toString() + "#nextToken");
		}

		int st = currentPosition;
		currentPosition = nextDelimiter(currentPosition);

		StringBuffer strb = new StringBuffer();
		while (st < currentPosition) {
			char ch = text.charAt(st++);
			if (ch == '"') {

				if ((st < currentPosition) && (text.charAt(st) == '"')) {
					strb.append(ch);
					st++;
				}
			}
			else {
				strb.append(ch);
			}
		}
		currentPosition++;
		return new String(strb);
	}

	public Object nextElement() {
		return nextToken();
	}

	/**
	 * @return
	 */
	public boolean hasMoreTokens() {

		return (nextDelimiter(currentPosition) <= maxPosition);
	}

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	public String toString() {
		return "CSVTokenizer(\"" + text + "\")";
	}

	public static void main(String[] args) {
		CSVTokenizer ss = new CSVTokenizer("Hello,\"World\",\"A,B\",\" \"\" \"" + ",Allen Teoh"
				+ ",\" A,B,C \"\" \" \",fdsadsad", ",");

		while (ss.hasMoreTokens()) {
			System.out.print("[");
			System.out.print(ss.nextToken());
			System.out.println("]");
		}
	}
}