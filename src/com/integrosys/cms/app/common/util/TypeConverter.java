package com.integrosys.cms.app.common.util;

import java.math.BigDecimal;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.common.bus.Area;
import com.integrosys.cms.app.common.bus.IArea;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class TypeConverter {

	public static final String YES = "Y";

	public static final String NO = "N";

	// ===============================
	// Convert To Primitive Types
	// ===============================
	public static int convertToPrimitiveType(Integer value) {
		if (value != null) {
			return value.intValue();
		}
		return ICMSConstant.INT_INVALID_VALUE;
	}

	public static long convertToPrimitiveType(Long value) {
		if (value != null) {
			return value.longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public static float convertToPrimitiveType(Float value) {
		if (value != null) {
			return value.floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	public static double convertToPrimitiveType(Double value) {
		if (value != null) {
			return value.doubleValue();
		}
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	// ==========================================
	// Convert Primitive Types to Object Types
	// ==========================================
	public static Integer convertToObjectType(int value) {
		return convertToObjectType(value, false);
	}

	public static Integer convertToObjectType(int value, boolean returnNullIfInvalid) {
		if (returnNullIfInvalid) {
			if (value == ICMSConstant.INT_INVALID_VALUE) {
				return null;
			}
		}
		return new Integer(value);
	}

	public static Long convertToObjectType(long value) {
		return convertToObjectType(value, false);
	}

	public static Long convertToObjectType(long value, boolean returnNullIfInvalid) {
		if (returnNullIfInvalid) {
			if (value == ICMSConstant.LONG_INVALID_VALUE) {
				return null;
			}
		}
		return new Long(value);
	}

	public static Float convertToObjectType(float value) {
		return convertToObjectType(value, false);
	}

	public static Float convertToObjectType(float value, boolean returnNullIfInvalid) {
		if (returnNullIfInvalid) {
			if (value == ICMSConstant.FLOAT_INVALID_VALUE) {
				return null;
			}
		}
		return new Float(value);
	}

	public static Double convertToObjectType(double value) {
		return convertToObjectType(value, false);
	}

	public static Double convertToObjectType(double value, boolean returnNullIfInvalid) {
		if (returnNullIfInvalid) {
			if (value == ICMSConstant.DOUBLE_INVALID_VALUE) {
				return null;
			}
		}
		return new Double(value);
	}

	// ===============================
	// Conversion for boolean
	// ===============================
	public static String convertBooleanToStringEquivalent(boolean flag) {
		return (flag) ? YES : NO;
	}

	public static boolean convertStringToBooleanEquivalent(String str) {
		if (str == null) {
			return false;
		}

		return (str.equals(YES));
	}

	// ===============================
	// Conversion for Amount Object
	// ===============================
	public static Amount convertToAmount(Double value, String currency) {
		if ((value != null) && (currency != null)) {
			return new Amount(value.doubleValue(), currency);
		}
		return null;
	}

	public static Amount convertToAmount(BigDecimal value, String currency) {
		if ((value != null) && (currency != null)) {
			return new Amount(value, new CurrencyCode(currency));
		}
		return null;
	}

	// ===============================
	// Conversion for Area Object
	// ===============================
	public static IArea convertToArea(Double areaSize, String unitOfMeasurement) {
		if ((areaSize != null) && (unitOfMeasurement != null)) {
			return new Area(areaSize.doubleValue(), unitOfMeasurement);
		}
		return null;
	}

}
