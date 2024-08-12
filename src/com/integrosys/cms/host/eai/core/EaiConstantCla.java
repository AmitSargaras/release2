package com.integrosys.cms.host.eai.core;

import com.integrosys.cms.app.common.constant.ICMSConstant;


public class EaiConstantCla {

	private final static String[] ALLOWED_VALUES_YES_NO = new String[] { ICMSConstant.TRUE_VALUE,
			ICMSConstant.FALSE_VALUE };

	private final static String[] ALLOWED_VALUES_YES_NO_NA = new String[] { ICMSConstant.TRUE_VALUE,
			ICMSConstant.FALSE_VALUE, ICMSConstant.NOT_AVAILABLE_VALUE };

	/** Allowed values for Frequency, ie, "D", "W", "M", "Y" */
	private final static String[] ALLOWED_VALUES_FREQUENCY_UNIT = new String[] { ICMSConstant.FREQ_UNIT_DAYS,
			ICMSConstant.FREQ_UNIT_WEEKS, ICMSConstant.FREQ_UNIT_MONTHS, ICMSConstant.FREQ_UNIT_YEARS };

	/** Allowed values for Update Status Indicators, ie, "I", "U", "D" */
	private final static String[] ALLOWED_VALUES_UPDATE_STATUS_INDICATORS = new String[] {
			ICMSConstant.HOST_STATUS_INSERT, ICMSConstant.HOST_STATUS_DELETE, ICMSConstant.HOST_STATUS_UDPATE };

	/** Allowed values for Custodian Types, ie, "I", "E" */
	private final static String[] ALLOWED_VALUES_CUSTODIAN_TYPES = new String[] { ICMSConstant.INTERNAL_COL_CUSTODIAN,
			ICMSConstant.EXTERNAL_COL_CUSTODIAN };

	public static String[] getAllowedValuesYesNo() {
		return ALLOWED_VALUES_YES_NO;
	}

	public static String[] getAllowedValuesYesNoNa() {
		return ALLOWED_VALUES_YES_NO_NA;
	}

	public static String[] getAllowedValuesFrequencyUnit() {
		return ALLOWED_VALUES_FREQUENCY_UNIT;
	}

	public static String[] getAllowedValuesUpdateStatusIndicators() {
		return ALLOWED_VALUES_UPDATE_STATUS_INDICATORS;
	}

	public static String[] getAllowedValuesCustodianTypes() {
		return ALLOWED_VALUES_CUSTODIAN_TYPES;
	}

}
