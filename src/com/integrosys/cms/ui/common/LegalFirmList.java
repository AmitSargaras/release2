package com.integrosys.cms.ui.common;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.commondata.app.bus.ICodeCategoryEntry;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 9, 2003 Time: 5:29:11 PM
 * To change this template use Options | File Templates.
 */
public class LegalFirmList extends BaseList { // ------

	private static HashMap legalFirmMap = null;

	private Collection legalFirmLabel = new ArrayList();

	private Collection legalFirmValue = new ArrayList();

	private static Date createdDate;

	private static LegalFirmList thisInstance;

	protected LegalFirmList() {
	}

	public static LegalFirmList getInstance(String aCountry) {
		if (thisInstance == null) {
			thisInstance = new LegalFirmList(aCountry);
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new LegalFirmList(aCountry);
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	public LegalFirmList(String aCountry) {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		legalFirmMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_LEGAL_FIRM);
		// DefaultLogger.debug(this, "IN " + legalFirmMap);
		Collection keyvalue = legalFirmMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		ICodeCategoryEntry entry = null;
		ArrayList list = new ArrayList();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			entry = new OBCodeCategoryEntry(key, (String) legalFirmMap.get(key), true);
			list.add(entry);
		}
		ICodeCategoryEntry[] entryList = (ICodeCategoryEntry[]) list.toArray(new ICodeCategoryEntry[0]);
		if ((entryList != null) && (entryList.length > 0)) {
			Arrays.sort(entryList);
			for (int ii = 0; ii < entryList.length; ii++) {
				if (entryList[ii].getEntryCode().startsWith("LF_" + aCountry)) {
					legalFirmValue.add(entryList[ii].getEntryCode());
					legalFirmLabel.add(entryList[ii].getEntryName());
				}
			}
		}
	}

	public Collection getLegalFirmLabel() {
		return legalFirmLabel;
	}

	public Collection getLegalFirmProperty() {
		return legalFirmValue;
	}

	public String getLegalFirmLabel(String key) {
		if (!legalFirmMap.isEmpty()) {
			return (String) legalFirmMap.get(key);
		}
		else {
			return "";
		}
	}
}
