/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/FeedUtils.java,v 1.2 2003/09/17 10:46:54 btchng Exp $
 */
package com.integrosys.cms.ui.feed;

/**
 * This class contain utility methods for Manual feeds update in the UI layer.
 * 
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 10:46:54 $ Tag: $Name: $
 */
public class FeedUtils {

	/**
	 * Right-pads the fraction portion of the incoming string with zeros to
	 * achieve the specified number of fraction digits if the string already
	 * contains the specified input number of non-zero fraction digits. The
	 * string must be a valid decimal number. Otherwise, the same string will be
	 * returned.
	 * @param s The string to be padded.
	 * @param existingNonZeroFractionDigits The number of existing non-zero
	 *        fraction digits to be matched.
	 * @param numFractionDigits The number of fraction digits to achieve.
	 * @return The padded or unpadded string.
	 */
	public static String padFractionDigits(String s, int existingNonZeroFractionDigits, int numFractionDigits) {

		// Check if the string is a valid number.
		try {
			Double.parseDouble(s);
		}
		catch (NumberFormatException e) {
			// Cannot parse into number, dont pad.
			return s;
		}
		String dec = "";
		String fraction = "";

		if (s.indexOf(".") == -1) {
			dec = s;
		}
		else {
			// Get the fraction portion.
			dec = s.substring(0, s.indexOf("."));
			fraction = s.substring(s.lastIndexOf(".") + 1);
		}

		int currentFractionDigits = fraction.length();

		// Strips off trailing zeros.
		while (true) {
			if (fraction.endsWith("0")) {
				fraction = fraction.substring(0, fraction.length() - 1);

			}
			else {
				break;
			}
		}

		// Get the current number of non-zero fraction digits.
		int currentNonZeroFractionDigits = fraction.length();

		if (currentNonZeroFractionDigits != 0) {
			s = dec.concat(".").concat(fraction);
			// If the current number = specified existing number, pads
			// zero till the specified total number of fraction digits.
			if (currentNonZeroFractionDigits < existingNonZeroFractionDigits) {
				for (int i = 0; i < numFractionDigits - currentFractionDigits; i++) {
					s = s + "0";
				}
			}
		}
		else if (currentNonZeroFractionDigits == 0) {
			s = dec;
		}

		return s;
	}
}
