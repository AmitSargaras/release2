/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/PaymentFreqList.java,v 1.1 2004/06/21 11:43:42 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/21 11:43:42 $ Tag: $Name: $
 */
public class PaymentFreqList extends BaseList { // ------
	private static ArrayList paymentFreqID;

	private static ArrayList paymentFreqValue;

	private static HashMap paymentFreqMap;

	private static Date createdDate;

	private static PaymentFreqList thisInstance;

	public synchronized static PaymentFreqList getInstance() {
		if (thisInstance == null) {
			thisInstance = new PaymentFreqList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new PaymentFreqList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private PaymentFreqList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		paymentFreqID = new ArrayList();
		paymentFreqValue = new ArrayList();
		paymentFreqMap = new HashMap();
		paymentFreqMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityMainConstant.PAYMENT_FREQ);
		Collection keyvalue = paymentFreqMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			paymentFreqID.add(key);
			paymentFreqValue.add(paymentFreqMap.get(key));
		}
	}

	public Collection getPaymentFreqID() {
		return paymentFreqID;
	}

	public Collection getPaymentFreqValue() {
		return paymentFreqValue;
	}

	public String getPaymentFreqItem(String key) {
		if (!paymentFreqMap.isEmpty()) {
			return (String) paymentFreqMap.get(key);
		}
		return "";
	}
}
