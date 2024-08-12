package com.integrosys.cms.ui.common;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.ValidatorConstant;

public class ValidationUtil implements ValidatorConstant{
	
	private static StringTokenizer st;

	static String validateNumber(String iNumber, boolean chk, BigDecimal min, BigDecimal max, int decimalPlaces,
			Locale locale) {
		String ss = null;

		try {
			if (iNumber != null) {
				iNumber = iNumber.trim();
			}

			if ((ss = checkMandatory(iNumber, chk)) == null) {
				ss = chkNumber(iNumber, min, max, decimalPlaces);
			}
		} catch (Exception var8) {
			DefaultLogger.error("ValidationUtil", "", var8);
			ss = "format";
		}

		return ss;
	}
	
	private static String chkNumber(String iNumber, BigDecimal min, BigDecimal max, int decimalPlaces) {
		String sNumber = "";
		String ss = "noerror";

		try {
			PatternCompiler compiler = new Perl5Compiler();
			PatternMatcher matcher = new Perl5Matcher();
			PatternMatcherInput input = new PatternMatcherInput(iNumber);
			Pattern pattern = compiler.compile("[^-0-9,.]");
			if (matcher.contains(input, pattern)) {
				DefaultLogger.debug("ValidationUtil", input + ": input contains pattern other than 0-9,',','.'");
				ss = "format";
			} else {
				int iIndex = iNumber.indexOf(46);
				if (iIndex != -1) {
					if (iIndex != iNumber.lastIndexOf(46)) {
						ss = "format";
						return ss;
					}

					if (iNumber.indexOf(44, iIndex) != -1) {
						ss = "format";
						return ss;
					}
				}

				if (iNumber.indexOf(44) == 0) {
					ss = "format";
				} else {
					for (st = new StringTokenizer(iNumber, ",", false); st
							.hasMoreTokens(); sNumber = sNumber + st.nextToken()) {
						;
					}

					BigDecimal iNumVal = new BigDecimal(sNumber.trim());
					int ii = sNumber.lastIndexOf(46);
					if (ii != -1) {
						String fraction = sNumber.substring(ii);
						if (fraction.length() > decimalPlaces) {
							ss = "decimalexceeded";
							return ss;
						}
					}

					if (iNumVal.compareTo(min) == -1) {
						ss = "lessthan";
					}

					if (iNumVal.compareTo(max) == 1) {
						ss = "greaterthan";
					}
				}
			}
		} catch (NumberFormatException var14) {
			ss = "format";
		} catch (Exception var15) {
			DefaultLogger.error("ValidationUtil", "", var15);
			ss = "format";
		}

		return ss;
	}
	
	public static String checkMandatory(String aString, boolean chk) {
		if (isEmptyString(aString)) {
			return chk ? "mandatory" : "noerror";
		} else {
			return null;
		}
	}
	
	static boolean isEmptyString(String aString) {
		if (aString != null) {
			aString = aString.trim();
		}

		return aString == null || aString.equals("");
	}
}
