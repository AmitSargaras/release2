package com.integrosys.cms.app.common.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.util.DateUtil;

public class MapperUtil {
	
	public static final String defaultDateFormat = "dd/MMM/yyyy";
	
	public static String emptyIfNull(String input) {
		return input==null ? "" : input ;
	}
	
	public static String bigDecimalToString(BigDecimal input) {
		if(input==null)
			return "";
		return input.toString();
	}
	
	public static String doubleToString(Double input) {
		if(input==null)
			return "";
		return input.toString();
	}
	
	public static String longToString(Long input) {
		if(input==null)
			return "";
		return input.toString();
	}

	public static BigDecimal stringToBigDecimal(String input) {
		if(StringUtils.isEmpty(input))
			return null;
		return new BigDecimal(input);
	}
	
	public static Double stringToDouble(String input) {
		if(StringUtils.isEmpty(input))
			return null;
		return Double.valueOf(input);
	} 
	
	public static String dateToString(Date date, String format) {
		if(date==null)
			return null;
		format = StringUtils.isEmpty(format) ? defaultDateFormat : format ;
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		return sdf2.format(date);
	}
	
	public static Date stringToDate(String dateStr) {
		if(StringUtils.isEmpty(dateStr))
			return null;
		return DateUtil.parseDate(defaultDateFormat, dateStr);
	}
}
