/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealFieldValidator.java,v 1.4 2004/07/03 05:32:56 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/03 05:32:56 $ Tag: $Name: $
 */
public class CommodityDealFieldValidator {
	public static HashMap validateInput(ICommodityDeal commDealObj, Locale locale) {
		HashMap exceptionMap = new HashMap();
		ActionErrors errors = new ActionErrors();
		if ((commDealObj.getIsPledgeDocumentRequired() == null)
				|| (commDealObj.getIsPledgeDocumentRequired().length() == 0)) {
			exceptionMap.put("overallError1", new ActionMessage("error.commodity.deal.commdoc.confirmpledge"));
		}
		if (commDealObj.getContractProfileID() <= 0) {
			exceptionMap.put("overallError2", new ActionMessage("error.commodity.deal.dealinfo.contractprofile"));
		}
		if (commDealObj.getContractQuantity() == null) {
			exceptionMap.put("overallError3", new ActionMessage("error.commodity.deal.dealinfo.contractqty"));
		}

		return exceptionMap;
	}
}
