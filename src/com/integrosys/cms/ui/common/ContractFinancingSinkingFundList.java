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
public class ContractFinancingSinkingFundList extends BaseList { // ------
	private ContractFinancingSinkingFundList() {
		// ------
		super();
		createdDate = super.getLastDate();
		// -----
	}

	static ContractFinancingSinkingFundList contractFinancingSinkingFundList = null;

	private static HashMap hContractFinancingSinkingFundList = new HashMap();

	private static Collection hContractFinancingSinkingFundLabel = new ArrayList();

	private static Collection hContractFinancingSinkingFundValue = new ArrayList();

	private static Date createdDate;

	public static ContractFinancingSinkingFundList getInstance() {

		// ------
		if (contractFinancingSinkingFundList != null) {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				contractFinancingSinkingFundList = null;

				hContractFinancingSinkingFundValue = null;
				hContractFinancingSinkingFundLabel = null;
				hContractFinancingSinkingFundList = null;

				// setLastDate(current);
			}
		}

		// -----
		if (contractFinancingSinkingFundList == null) {
			contractFinancingSinkingFundList = new ContractFinancingSinkingFundList();
			hContractFinancingSinkingFundValue = new ArrayList();
			hContractFinancingSinkingFundLabel = new ArrayList();
			hContractFinancingSinkingFundList = new HashMap();

			hContractFinancingSinkingFundList = CommonDataSingleton
					.getCodeCategoryValueLabelMap(ICMSUIConstant.SINKING_FUND_PARTY);

			DefaultLogger.debug(">>>> ", ">>>> in ContractFinancingSinkingFundList");

			Collection keyvalue = hContractFinancingSinkingFundList.keySet();

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
				hContractFinancingSinkingFundValue.add(key);
				hContractFinancingSinkingFundLabel.add(hContractFinancingSinkingFundList.get(key));
			}
		}
		return contractFinancingSinkingFundList;
	}

	public Collection getContractFinancingSinkingFundLabel() {
		return hContractFinancingSinkingFundLabel;
	}

	public Collection getContractFinancingSinkingFundProperty() {
		return hContractFinancingSinkingFundValue;
	}

	public String getContractFinancingSinkingFundValue(String key) {
		if (!hContractFinancingSinkingFundList.isEmpty()) {
			return (String) hContractFinancingSinkingFundList.get(key);
		}
		else {
			return "";
		}
	}
}
