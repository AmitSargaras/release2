package com.integrosys.cms.ui.collateral;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Sep 24, 2003 Time:
 * 2:28:37 PM To change this template use Options | File Templates.
 */
public class CommonNatureOfCharge {

	public static String getNatureOfChargeDescription(String colType, String code) {
		String desc = "-";
		DefaultLogger.debug(CommonNatureOfCharge.class.getName(), "collateralType---->" + colType);
		if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(colType)) {
			return com.integrosys.cms.ui.collateral.property.NatureOfChargeList.getInstance().getNatureOfChargeItem(
					code);
		}
		if (ICMSConstant.SECURITY_TYPE_CASH.equals(colType)) {
			return com.integrosys.cms.ui.collateral.cash.NatureOfChargeList.getInstance().getNatureOfChargeItem(code);
		}
		if (ICMSConstant.SECURITY_TYPE_INSURANCE.equals(colType)) {
			return com.integrosys.cms.ui.collateral.insprotection.NatureOfChargeList.getInstance()
					.getNatureOfChargeItem(code);
		}
		if (ICMSConstant.SECURITY_TYPE_MARKETABLE.equals(colType)) {
			return com.integrosys.cms.ui.collateral.marketablesec.NatureOfChargeList.getInstance()
					.getNatureOfChargeItem(code);
		}
		if (ICMSConstant.SECURITY_TYPE_ASSET.equals(colType)) {
			return com.integrosys.cms.ui.collateral.assetbased.NatureOfChargeList.getInstance().getNatureOfChargeItem(
					code);
		}
		if (ICMSConstant.SECURITY_TYPE_OTHERS.equals(colType)) {
			return com.integrosys.cms.ui.collateral.others.NatureOfChargeList.getInstance().getNatureOfChargeItem(code);
		}
		return desc;
	}
}