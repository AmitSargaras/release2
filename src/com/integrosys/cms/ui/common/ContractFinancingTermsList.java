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
public class ContractFinancingTermsList extends BaseList { // ------
	private ContractFinancingTermsList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static ContractFinancingTermsList contractFinancingTermsList = null;

	private static HashMap hContractFinancingTermsList = new HashMap();

	private static Collection hContractFinancingTermsLabel = new ArrayList();

	private static Collection hContractFinancingTermsValue = new ArrayList();

	private static Date createdDate;

	public static ContractFinancingTermsList getInstance() {
		// ------
		if (contractFinancingTermsList != null) {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				contractFinancingTermsList = null;

				hContractFinancingTermsValue = null;
				hContractFinancingTermsLabel = null;
				hContractFinancingTermsList = null;

				// setLastDate(current);
			}
		}

		// -----
		if (contractFinancingTermsList == null) {
			contractFinancingTermsList = new ContractFinancingTermsList();
			hContractFinancingTermsValue = new ArrayList();
			hContractFinancingTermsLabel = new ArrayList();
			hContractFinancingTermsList = new HashMap();

			hContractFinancingTermsList = CommonDataSingleton
					.getCodeCategoryValueLabelMap(ICMSUIConstant.CONTRACT_TERMS);

			DefaultLogger.debug(">>>> ", ">>>> in ContractFinancingTermsList");

			Collection keyvalue = hContractFinancingTermsList.keySet();

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
				hContractFinancingTermsValue.add(key);
				hContractFinancingTermsLabel.add(hContractFinancingTermsList.get(key));
			}
		}
		return contractFinancingTermsList;
	}

	public Collection getContractFinancingTermsLabel() {
		return hContractFinancingTermsLabel;
	}

	public Collection getContractFinancingTermsProperty() {
		return hContractFinancingTermsValue;
	}

	public String getContractFinancingTermsValue(String key) {
		if (!hContractFinancingTermsList.isEmpty()) {
			return (String) hContractFinancingTermsList.get(key);
		}
		else {
			return "";
		}
	}
}
