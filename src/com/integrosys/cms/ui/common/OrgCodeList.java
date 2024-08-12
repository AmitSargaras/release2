/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/OrgCodeList.java,v 1.1 2003/11/28 09:04:42 hltan Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.commondata.app.bus.ICodeCategoryEntry;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/28 09:04:42 $ Tag: $Name: $
 */
public class OrgCodeList extends BaseList { // ------

	private static HashMap orgCodeMap;

	private static Collection orgCodeLabel;

	private static Collection orgCodeValue;

	private static Date createdDate;

	private static OrgCodeList thisInstance;

	public synchronized static OrgCodeList getInstance(String[] countryCodes) {
		if (thisInstance == null) {
			thisInstance = new OrgCodeList(countryCodes);
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new OrgCodeList(countryCodes);
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private OrgCodeList(String[] countryCodes) {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		orgCodeMap = new HashMap();
		orgCodeLabel = new ArrayList();
		orgCodeValue = new ArrayList();

		if (countryCodes != null) {
			for (int i = 0; i < countryCodes.length; i++) {
				getLabelValueMapByCountry(countryCodes[i]);
			}

			Collection keyvalue = orgCodeMap.keySet();
			Iterator itr1 = keyvalue.iterator();
			ICodeCategoryEntry entry = null;
			ArrayList list = new ArrayList();
			while (itr1.hasNext()) {
				String key = (String) itr1.next();
				entry = new OBCodeCategoryEntry(key, (String) orgCodeMap.get(key), true);
				list.add(entry);
			}
			ICodeCategoryEntry[] entryList = (ICodeCategoryEntry[]) list.toArray(new ICodeCategoryEntry[0]);
			if ((entryList != null) && (entryList.length > 0)) {
				Arrays.sort(entryList);
				for (int ii = 0; ii < entryList.length; ii++) {
					orgCodeValue.add(entryList[ii].getEntryCode());
					orgCodeLabel.add(entryList[ii].getEntryName());
				}
			}
		}
	}

	private void getLabelValueMapByCountry(String country) {
		DefaultLogger.debug(this, "<<<< country is : " + country);
		HashMap tempOrgCodeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_CODE_BKGLOC,
				null, country);
		orgCodeMap.putAll(tempOrgCodeMap);
	}

	public Collection getOrgCodeValues() {
		return orgCodeValue;
	}

	public Collection getOrgCodeLabels() {
		return orgCodeLabel;
	}

	public String getOrgCodeLabel(String key) {
		if (!orgCodeMap.isEmpty()) {
			return (String) orgCodeMap.get(key);
		}
		return "";
	}
}
