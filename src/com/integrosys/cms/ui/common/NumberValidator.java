package com.integrosys.cms.ui.common;

import java.math.BigDecimal;
import java.util.Locale;

import com.integrosys.base.techinfra.validation.ValidatorConstant;

public class NumberValidator implements ValidatorConstant{

	public static String checkNumber(String input, boolean check, BigDecimal min, BigDecimal max, int decimalPlaces,
			Locale locale) {
		String result = ValidationUtil.validateNumber(input, check, min, max, decimalPlaces, locale);
		return result;
	}
}
