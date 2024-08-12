package com.integrosys.cms.app.common.util;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author allen
 *
 * Generate Gems Ticket No based on time
 *
 */
public final class TicketNumberUtil {

	private static int runningCounter = 0;

	/**
	 * @return TicketNumber
	 */
	public synchronized final static String generateTicketNumber() {
		String num = Long.toString(new java.util.Date().getTime()).substring(6);
		long number = Long.parseLong(num);
		return convertToBase(number);
	}

	// Remove I and O - ( Similiar to 1 and 0 )
	private final static char digits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'
			// , 'I'
			, 'J', 'K', 'L', 'M', 'N'
			// , 'O'
			, 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private final static long base = digits.length;

	public final static long convertToLong(String baseString) {
		long total = 0;

		String num = baseString.substring(2);

		for (int i = 0; i < num.length(); i++) {
			int match = 0;
			for (int ii = 0; ii < digits.length; ii++) {
				if (num.charAt(i) == digits[ii]) {
					match = ii;

					break;
				}
			}
			long add = match * ((long) Math.pow(base, (num.length() - i) - 1));

			// System.out.println(add);

			total += add;

		}

		// char checksum = digits[(int) Math.abs(total % base)];

		// char checksum = generateChecksum(num);

		// DefaultLogger.debug(TicketNumberUtil.class, "Checksum = "+checksum);

		// if (checksum!=baseString.charAt(0))
		// throw new InvalidTicketNumberChecksumException();

		return total;
	}

	/**
	 * Check is the TicketNumber contains checksum error
	 *
	 * @param str
	 * @return
	 */
	public final static boolean isValidTicketNumber(String str) {
		try {
			String num = str.substring(2).toUpperCase();
			char checksum = generateChecksum(num);
			if (checksum != str.charAt(0))
				return false;

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public final static String convertToBase(long number) {
		// this array contains all digits that are used in different bases til
		// 16 [hex]

		char output[] = new char[(int) base];
		int i = 0;

		// actual logic
		while (number > 0) {
			// copy character from array digit situated at index = reminder of
			// number / base
			output[i++] = digits[(int) (number % base)];
			number = number / base;
		}
		i--;
		// print output
		StringBuffer sb = new StringBuffer();
		for (; i >= 0; i--) {
			sb.append(output[i]);
		}

		runningCounter++;

		char counterChar = digits[(int) Math.abs(runningCounter % base)];

		sb.insert(0, counterChar);

		char checksum = generateChecksum(sb.toString());

		DefaultLogger.debug(TicketNumberUtil.class, "Checksum = " + checksum);

		sb.insert(0, checksum);

		return sb.toString();
	}

	private final static char generateChecksum(String s) {
		int addup = 0;

		char oldChar = 0;

		for (int i = 0; i < s.length(); i++) {
			addup += s.charAt(i);

			addup += (s.charAt(i) * oldChar % base);

			oldChar = s.charAt(i);
		}

		return digits[(int) Math.abs(addup % base)];
	}

}