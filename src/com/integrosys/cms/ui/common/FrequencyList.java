package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 10, 2003 Time: 12:03:45 PM
 * To change this template use Options | File Templates.
 */
public class FrequencyList extends BaseList { // ------
	private FrequencyList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static FrequencyList frequencyList = null;

	private static HashMap hFrequencyList = new HashMap();

	private static Collection hFrequencyLabel = new ArrayList();

	private static Collection hFrequencyValue = new ArrayList();

	private static Date createdDate;

	public static FrequencyList getInstance() {
		// ------
		if (frequencyList != null) {
			Date current = new Date();
			if (frequencyList != null) {
				if (toRefresh(current, createdDate)) {
					frequencyList = null;

					hFrequencyValue = null;
					hFrequencyLabel = null;
					hFrequencyList = null;

					// setLastDate(current);
				}
			}
		}

		// -----
		if (frequencyList == null) {
			frequencyList = new FrequencyList();
			hFrequencyValue = new ArrayList();
			hFrequencyLabel = new ArrayList();
			hFrequencyList = new HashMap();

			hFrequencyList = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.FREQ_TYPE);

			Collection keyvalue = hFrequencyList.keySet();

			String[] keyValueList = (String[]) keyvalue.toArray(new String[0]);
			Arrays.sort(keyValueList, new Comparator() {
				public int compare(Object o1, Object o2) {
					int id1 = UIUtil.getFreqCode((String) o1);
					int id2 = UIUtil.getFreqCode((String) o2);
					return id2 - id1;
				}
			});
			for (int i = 0; i < keyValueList.length; i++) {
				String key = keyValueList[i];
				hFrequencyValue.add(key);
				hFrequencyLabel.add(hFrequencyList.get(key));
			}
		}
		return frequencyList;
	}

	public Collection getFrequencyLabel() {
		return hFrequencyLabel;
	}

	public Collection getFrequencyProperty() {
		return hFrequencyValue;
	}

	public String getFrequencyValue(String key) {
		if (!hFrequencyList.isEmpty()) {
			return (String) hFrequencyList.get(key);
		}
		else {
			return "";
		}
	}

}
