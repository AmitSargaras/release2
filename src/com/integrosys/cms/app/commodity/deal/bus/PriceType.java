/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/PriceType.java,v 1.5 2006/03/23 08:40:07 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.util.Map;

import com.integrosys.cms.app.commodity.common.NameValuePair;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/03/23 08:40:07 $ Tag: $Name: $
 */
public class PriceType extends NameValuePair {
	static {
		init();
	}

	public PriceType() {
		super();
	}

	private PriceType(String aCode, String aLabel) {
		super(aCode, aLabel);
	}

	public static PriceType[] getAll() {
		return TYPE_LIST;
	}

	public static PriceType valueOf(String aCode) {
		if (EOD_PRICE_CODE.equals(aCode)) {
			return EOD_PRICE;
		}
		if (FLOATING_FUTURES_PRICE_CODE.equals(aCode)) {
			return FLOATING_FUTURES_PRICE;
		}
		if (FIXED_FUTURES_PRICE_CODE.equals(aCode)) {
			return FIXED_FUTURES_PRICE;
		}
		if (MANUAL_FLOATING_FUTURES_PRICE_CODE.equals(aCode)) {
			return MANUAL_FLOATING_FUTURES_PRICE;
		}
		if (MANUAL_EOD_PRICE_CODE.equals(aCode)) {
			return MANUAL_EOD_PRICE;
		}
		if (NON_RIC_CODE.equals(aCode)) {
			return NON_RIC_PRICE;
		}
		if (MANUAL_NON_RIC_CODE.equals(aCode)) {
			return MANUAL_NON_RIC_PRICE;
		}
		throw new RuntimeException("No such price type : " + aCode);
	}

	public static void refresh() {
		init();
	}

	private static void init() {
		Map priceTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_COMMODITY_DEAL_PRICE_TYPE);

		EOD_PRICE = getPriceType(priceTypeMap, EOD_PRICE_CODE);
		FLOATING_FUTURES_PRICE = getPriceType(priceTypeMap, FLOATING_FUTURES_PRICE_CODE);
		FIXED_FUTURES_PRICE = getPriceType(priceTypeMap, FIXED_FUTURES_PRICE_CODE);
		MANUAL_FLOATING_FUTURES_PRICE = getPriceType(priceTypeMap, MANUAL_FLOATING_FUTURES_PRICE_CODE);
		MANUAL_EOD_PRICE = getPriceType(priceTypeMap, MANUAL_EOD_PRICE_CODE);
		NON_RIC_PRICE = getPriceType(priceTypeMap, NON_RIC_CODE);
		MANUAL_NON_RIC_PRICE = getPriceType(priceTypeMap, MANUAL_NON_RIC_CODE);
		TYPE_LIST = new PriceType[] { EOD_PRICE, FLOATING_FUTURES_PRICE, FIXED_FUTURES_PRICE,
				MANUAL_FLOATING_FUTURES_PRICE, MANUAL_EOD_PRICE, NON_RIC_PRICE, MANUAL_NON_RIC_PRICE };
	}

	private static PriceType getPriceType(Map priceTypeMap, String name) {
		String label = (String) priceTypeMap.get(name);

		if ((label == null) || (label.length() == 0)) {
			throw new RuntimeException("Missing Common Code - " + name);
		}
		return new PriceType(name, label);
	}

	public static PriceType EOD_PRICE;

	public static PriceType FLOATING_FUTURES_PRICE;

	public static PriceType FIXED_FUTURES_PRICE;

	public static PriceType MANUAL_FLOATING_FUTURES_PRICE;

	public static PriceType MANUAL_EOD_PRICE;

	public static PriceType NON_RIC_PRICE;

	public static PriceType MANUAL_NON_RIC_PRICE;

	private static final String EOD_PRICE_CODE = "E";

	private static final String FLOATING_FUTURES_PRICE_CODE = "F";

	private static final String FIXED_FUTURES_PRICE_CODE = "X";

	private static final String MANUAL_FLOATING_FUTURES_PRICE_CODE = "FM";

	private static final String MANUAL_EOD_PRICE_CODE = "EM";

	private static final String NON_RIC_CODE = "N";

	private static final String MANUAL_NON_RIC_CODE = "NM";

	private static PriceType[] TYPE_LIST;
}
