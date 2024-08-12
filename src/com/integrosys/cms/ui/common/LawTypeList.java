package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 10, 2003 Time: 12:03:45 PM
 * To change this template use Options | File Templates.
 */
public class LawTypeList {
	private LawTypeList() {

	}

	static LawTypeList LawTypeList = null;

	private static HashMap hLawTypeList = new HashMap();

	private static Collection hLawTypeLabel = new ArrayList();

	private static Collection hLawTypeValue = new ArrayList();

	public static LawTypeList getInstance() throws CollateralException {

		if (LawTypeList == null) {
			LawTypeList = new LawTypeList();
			hLawTypeList.put("ENG", "English");
			hLawTypeList.put("OTH", "Others");
			hLawTypeValue.add("ENG");
			hLawTypeValue.add("OTH");
			hLawTypeLabel.add("English");
			hLawTypeLabel.add("Others");
			DefaultLogger.debug("Security type List ---->", hLawTypeList);
		}
		return LawTypeList;
	}

	public Collection getLawTypeLabel() throws CollateralException {
		return hLawTypeLabel;
	}

	public Collection getLawTypeProperty() throws CollateralException {
		return hLawTypeValue;
	}

	public String getLawTypeValue(String key) {
		if (!hLawTypeList.isEmpty()) {
			return (String) hLawTypeList.get(key);
		}
		else {
			return "";
		}
	}

}
