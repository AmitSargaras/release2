/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/PeriodUnit.java,v 1.2 2004/06/18 04:02:58 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Map;

import com.integrosys.cms.app.commodity.common.NameValuePair;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/18 04:02:58 $ Tag: $Name: $
 */
public class PeriodUnit extends NameValuePair {

	public static PeriodUnit DAY;

	public static PeriodUnit WEEK;

	public static PeriodUnit MONTH;

	public static PeriodUnit YEAR;

	private static PeriodUnit[] UNIT_LIST;

	static {
		init();
	}

	public PeriodUnit() {
		super();
	}

	private PeriodUnit(String aCode, String aLabel) {
		super(aCode, aLabel);
	}

	public static PeriodUnit[] getAll() {
		return UNIT_LIST;
	}

	public static PeriodUnit valueOf(String aCode) {
		if (ICMSConstant.TIME_FREQ_DAY.equals(aCode)) {
			return DAY;
		}
		if (ICMSConstant.TIME_FREQ_WEEK.equals(aCode)) {
			return WEEK;
		}
		if (ICMSConstant.TIME_FREQ_MONTH.equals(aCode)) {
			return MONTH;
		}
		if (ICMSConstant.TIME_FREQ_YEAR.equals(aCode)) {
			return YEAR;
		}
		throw new RuntimeException("No such period unit : " + aCode);
	}

	public static void refresh() {
		init();
	}

	private static void init() {
		Map periodUnitMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_TIME_FREQ);

		DAY = getPeriodUnit(periodUnitMap, ICMSConstant.TIME_FREQ_DAY);
		WEEK = getPeriodUnit(periodUnitMap, ICMSConstant.TIME_FREQ_WEEK);
		MONTH = getPeriodUnit(periodUnitMap, ICMSConstant.TIME_FREQ_MONTH);
		YEAR = getPeriodUnit(periodUnitMap, ICMSConstant.TIME_FREQ_YEAR);

		UNIT_LIST = new PeriodUnit[] { DAY, WEEK, MONTH, YEAR };
	}

	private static PeriodUnit getPeriodUnit(Map periodUnitMap, String name) {
		String label = (String) periodUnitMap.get(name);

		if ((label == null) || (label.length() == 0)) {
			throw new RuntimeException("Missing Common Code - " + name);
		}
		return new PeriodUnit(name, label);
	}
}
