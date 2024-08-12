package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Tan Kien Leong Date: Mar 21, 2007 Time:
 * 11:11:09 AM To change this template use File | Settings | File Templates.
 */
public class ContractFinancingAwarderTypeList extends BaseList { // ------

	private ContractFinancingAwarderTypeList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static ContractFinancingAwarderTypeList contractFinancingAwarderTypeList = null;

	private static HashMap hContractFinancingAwarderTypeList = new HashMap();

	private static Collection hContractFinancingAwarderTypeLabel = new ArrayList();

	private static Collection hContractFinancingAwarderTypeValue = new ArrayList();

	private static Date createdDate;

	public static ContractFinancingAwarderTypeList getInstance() {
		// ------
		if (contractFinancingAwarderTypeList != null) {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				contractFinancingAwarderTypeList = null;

				hContractFinancingAwarderTypeValue = null;
				hContractFinancingAwarderTypeLabel = null;
				hContractFinancingAwarderTypeList = null;
				// setLastDate(current);
			}
		}
		// -----

		if (contractFinancingAwarderTypeList == null) {
			contractFinancingAwarderTypeList = new ContractFinancingAwarderTypeList();
			hContractFinancingAwarderTypeValue = new ArrayList();
			hContractFinancingAwarderTypeLabel = new ArrayList();
			hContractFinancingAwarderTypeList = new HashMap();

			hContractFinancingAwarderTypeList = CommonDataSingleton
					.getCodeCategoryValueLabelMap(ICMSUIConstant.AWARDER_TYPE);

			DefaultLogger.debug(">>>> ", ">>>> in ContractFinancingAwarderTypeList");

			Collection keyvalue = hContractFinancingAwarderTypeList.keySet();

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
				hContractFinancingAwarderTypeValue.add(key);
				hContractFinancingAwarderTypeLabel.add(hContractFinancingAwarderTypeList.get(key));
			}
		}
		return contractFinancingAwarderTypeList;
	}

	public Collection getContractFinancingAwarderTypeLabel() {
		return hContractFinancingAwarderTypeLabel;
	}

	public Collection getContractFinancingAwarderTypeProperty() {
		return hContractFinancingAwarderTypeValue;
	}

	public String getContractFinancingAwarderTypeValue(String key) {
		if (!hContractFinancingAwarderTypeList.isEmpty()) {
			return (String) hContractFinancingAwarderTypeList.get(key);
		}
		else {
			return "";
		}
	}
}
