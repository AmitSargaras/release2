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
public class ContractFinancingContractTypeList extends BaseList { // ------
	private ContractFinancingContractTypeList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static ContractFinancingContractTypeList contractFinancingContractTypeList = null;

	private static HashMap hContractFinancingContractTypeList = new HashMap();

	private static Collection hContractFinancingContractTypeLabel = new ArrayList();

	private static Collection hContractFinancingContractTypeValue = new ArrayList();

	private static Date createdDate;

	public static ContractFinancingContractTypeList getInstance() {

		// ------
		if (contractFinancingContractTypeList != null) {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				contractFinancingContractTypeList = null;

				hContractFinancingContractTypeValue = null;
				hContractFinancingContractTypeLabel = null;
				hContractFinancingContractTypeList = null;

				// setLastDate(current);
			}
		}

		// -----
		if (contractFinancingContractTypeList == null) {
			contractFinancingContractTypeList = new ContractFinancingContractTypeList();
			hContractFinancingContractTypeValue = new ArrayList();
			hContractFinancingContractTypeLabel = new ArrayList();
			hContractFinancingContractTypeList = new HashMap();

			hContractFinancingContractTypeList = CommonDataSingleton
					.getCodeCategoryValueLabelMap(ICMSUIConstant.CONTRACT_TYPE);

			DefaultLogger.debug(">>>> ", ">>>> in ContractFinancingContractTypeList");

			Collection keyvalue = hContractFinancingContractTypeList.keySet();

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
				hContractFinancingContractTypeValue.add(key);
				hContractFinancingContractTypeLabel.add(hContractFinancingContractTypeList.get(key));
			}
		}
		return contractFinancingContractTypeList;
	}

	public Collection getContractFinancingContractTypeLabel() {
		return hContractFinancingContractTypeLabel;
	}

	public Collection getContractFinancingContractTypeProperty() {
		return hContractFinancingContractTypeValue;
	}

	public String getContractFinancingContractTypeValue(String key) {
		if (!hContractFinancingContractTypeList.isEmpty()) {
			return (String) hContractFinancingContractTypeList.get(key);
		}
		else {
			return "";
		}
	}
}
