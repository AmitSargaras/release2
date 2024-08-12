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
 * Created by IntelliJ IDEA. User: KienLeong Date: Mar 21, 2007 Time: 11:11:09
 * AM To change this template use File | Settings | File Templates.
 */
public class ContractFinancingFacTypeList extends BaseList { // ------
	private ContractFinancingFacTypeList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static ContractFinancingFacTypeList contractFinancingFacTypeList = null;

	private static HashMap hContractFinancingFacTypeList = new HashMap();

	private static Collection hContractFinancingFacTypeLabel = new ArrayList();

	private static Collection hContractFinancingFacTypeValue = new ArrayList();

	private static Date createdDate;

	public static ContractFinancingFacTypeList getInstance() {

		// ------
		if (contractFinancingFacTypeList != null) {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				contractFinancingFacTypeList = null;

				hContractFinancingFacTypeValue = null;
				hContractFinancingFacTypeLabel = null;
				hContractFinancingFacTypeList = null;

				// setLastDate(current);
			}
		}

		// -----
		if (contractFinancingFacTypeList == null) {
			contractFinancingFacTypeList = new ContractFinancingFacTypeList();
			hContractFinancingFacTypeValue = new ArrayList();
			hContractFinancingFacTypeLabel = new ArrayList();
			hContractFinancingFacTypeList = new HashMap();

			hContractFinancingFacTypeList = CommonDataSingleton
					.getCodeCategoryValueLabelMap(ICMSUIConstant.CONTRACT_FAC_TYPE);

			DefaultLogger.debug(">>>> ", ">>>> in ContractFinancingFacTypeList");

			Collection keyvalue = hContractFinancingFacTypeList.keySet();

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
				hContractFinancingFacTypeValue.add(key);
				hContractFinancingFacTypeLabel.add(hContractFinancingFacTypeList.get(key));
			}
		}
		return contractFinancingFacTypeList;
	}

	public Collection getContractFinancingFacTypeLabel() {
		return hContractFinancingFacTypeLabel;
	}

	public Collection getContractFinancingFacTypeProperty() {
		return hContractFinancingFacTypeValue;
	}

	public String getContractFinancingFacTypeValue(String key) {
		if (!hContractFinancingFacTypeList.isEmpty()) {
			return (String) hContractFinancingFacTypeList.get(key);
		}
		else {
			return "";
		}
	}
}
