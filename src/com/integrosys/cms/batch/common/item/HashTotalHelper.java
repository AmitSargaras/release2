package com.integrosys.cms.batch.common.item;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 19-Jun-2007 Time: 13:20:12 To
 * change this template use File | Settings | File Templates.
 */
public class HashTotalHelper {
	public long getHashNumber(String inputString) {

		// if total length less than 5 , the hash number = 0 .
		if ((inputString == null) || (inputString.length() < 5)) {

			DefaultLogger.error(this, "Hash Column less than 5 :" + inputString);

			return 0;
		}

		String aString = inputString.substring(inputString.length() - 5, inputString.length());
		byte byteArray[] = new byte[aString.length()];
		byteArray = aString.getBytes();

		// ((B1 x B2 ) + ( B3 x B4 )) x (B5 x 3)

		long firstResult = ((byteArray[0] * byteArray[1]) + (byteArray[2] * byteArray[3])) * (byteArray[4] * 3);
		String firstString = String.valueOf(firstResult);
		int length = firstString.length();
		long resultA = 0;
		long resultB = 0;
		if (length > 5) {
			resultA = Long.parseLong(firstString.substring(0, 5));
			resultB = Long.parseLong(firstString.substring(5, length));
		}
		else {
			resultA = Long.parseLong(firstString.substring(0, length));
			resultB = 0;
		}
		long resultC = resultA + resultB;
		return resultC;
	}

	public long getHashTotal(long totalHashNumber) {
		String firstString = String.valueOf(totalHashNumber);
		int length = firstString.length();
		long resultA = 0;
		long resultB = 0;
		if (length > 6) {
			resultA = Long.parseLong(firstString.substring(0, 6));
			resultB = Long.parseLong(firstString.substring(6, length));
		}
		else {
			resultA = Long.parseLong(firstString.substring(0, length));
			resultB = 0;
		}
		long resultC = resultA + resultB;
		return resultC;
	}

	public static void main(String args[]) {
		long a = new HashTotalHelper().getHashNumber("1234567890");
		long b = new HashTotalHelper().getHashNumber("9876543210");
		long c = new HashTotalHelper().getHashTotal(a + b);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
}
