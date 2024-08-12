package com.integrosys.cms.host.stp;

import java.io.Serializable;
import java.io.StringReader;

/**
 * @author $Author: marvin $<br>
 * @author Chin Kok Cheong
 * @version $Id$
 */
public class MessageReader implements Serializable {
	String message = null;

	private String correlationId;

	/**
	 * @return the correlationId
	 */
	public final String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public final void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public MessageReader(String str) {
		message = removeWhiteSpace(str);
		// message = str;
	}

	public StringReader getStringReader() {
		return new StringReader(message);
	}

	public String removeWhiteSpace(String inputString) {
		// System.out.println(inputString);
		if (inputString == null) {
			return inputString;
		}

		String result = "";

		char xmlArray[] = new char[inputString.length()];

		inputString.getChars(0, inputString.length(), xmlArray, 0);

		StringBuffer xmlBuffer = new StringBuffer();
		StringBuffer tempBuffer = new StringBuffer();
		String token = "A";

		// 0020 SPACE
		// 003E >
		// 003C <

		for (int i = 0; i < inputString.length(); i++) {
			if (xmlArray[i] == '\u003E') {
				token = "B";
			}

			if (token.equals("B") && (xmlArray[i] == '\u003C')) {
				token = "C";
			}

			if (token.equals("B") && (xmlArray[i] != '\u003E')) {
				tempBuffer.append(xmlArray[i]);
				continue;
			}

			if (token.equals("C")) {
				if (tempBuffer.length() > 0) {
					xmlBuffer.append(tempBuffer.toString().trim());
					tempBuffer = new StringBuffer();
				}
			}

			xmlBuffer.append(xmlArray[i]);
		}

		result = xmlBuffer.toString();

		// System.out.println(result);
		return result;

	}
}