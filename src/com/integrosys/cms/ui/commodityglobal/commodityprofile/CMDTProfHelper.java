package com.integrosys.cms.ui.commodityglobal.commodityprofile;

import java.util.ArrayList;
import java.util.Collection;

import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

public class CMDTProfHelper {
	public static Collection getPriceTypeIDCollection() {
		Collection priceTypeColl = new ArrayList();
		priceTypeColl.add(IProfile.PRICE_TYPE_CASH);
		priceTypeColl.add(IProfile.PRICE_TYPE_FUTURES);
		priceTypeColl.add(IProfile.PRICE_TYPE_NOC_RIC);
		return priceTypeColl;
	}

	public static Collection getPriceTypeDescCollection() {
		Collection priceTypeColl = new ArrayList();
		priceTypeColl.add("Cash");
		priceTypeColl.add("Futures");
		priceTypeColl.add("Non-RIC");
		return priceTypeColl;
	}

	public static String getPriceTypeDesc(String priceTypeID) {
		if (IProfile.PRICE_TYPE_CASH.equals(priceTypeID)) {
			return "Cash";
		}
		else if (IProfile.PRICE_TYPE_FUTURES.equals(priceTypeID)) {
			return "Futures";
		}
		else if (IProfile.PRICE_TYPE_NOC_RIC.equals(priceTypeID)) {
			return "Non-RIC";
		}
		return "";
	}
}