/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/DeleteSubLimitCommand.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitUtils;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag 
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.DeleteSubLimitCommand
 *      .java
 */
public class DeleteSubLimitCommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.AN_DEL_SLT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_DEL_SLT_MAP, SLUIConstants.CN_HASHMAP, FORM_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see
	 * com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand#execute
	 * (java.util.HashMap, java.util.HashMap, java.util.HashMap)
	 */
	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		HashMap slMap = (HashMap) paramMap.get(SLUIConstants.AN_DEL_SLT_MAP);
		if ((slMap == null) || (limitMap == null)) {
			DefaultLogger.debug(this, "sltMap == null || limitMap == null");
			return;
		}
		Iterator iterator = slMap.keySet().iterator();
		HashMap dealRelatedSLMap = new HashMap();
		while (iterator.hasNext()) {
			String limitId = (String) iterator.next();
			List slList = (List) slMap.get(limitId);
			ArrayList toDeleteSLTList = new ArrayList();
			for (int index = 0; index < slList.size(); index++) {
				String sltId = (String) slList.get(index);
				try {
					if (SubLimitUtils.hasSLRelatedDeal(Long.parseLong(sltId))) {
						dealRelatedSLMap.put(limitId + "_" + sltId, limitId + "_" + sltId);
					}
					else {
						toDeleteSLTList.add(sltId);
					}
				}
				catch (CommodityDealException e) {
					throw new CommandProcessingException(e.getMessage());
				}
			}
			if (toDeleteSLTList.size() > 0) {
				deleteSLFromLimitMap(limitMap, limitId, toDeleteSLTList);
			}
		}
		if (dealRelatedSLMap.size() > 0) {
			exceptionMap.put(SLUIConstants.ERR_DELETE_SL, new ActionMessage(SLUIConstants.ERR_DELETE_SL_INFO));
			resultMap.put(SLUIConstants.AN_DEL_SLT_MAP, dealRelatedSLMap);
		}
		resultMap.put(SLUIConstants.AN_CMDT_LIMIT_MAP, limitMap);
	}

	private void deleteSLFromLimitMap(HashMap limitMap, String limitId, List toDeleteSLTList) {
		ICollateralLimitMap cLimitMap = (ICollateralLimitMap) limitMap.get(limitId);
		ISubLimit[] slArray = cLimitMap.getSubLimit();
		DefaultLogger.debug(this, " - limitId: " + limitId);
		DefaultLogger.debug(this, "Befor delete,Num of sub limit : " + (slArray == null ? 0 : slArray.length));
		if ((slArray == null) || (toDeleteSLTList == null)) {
			DefaultLogger.debug(this, "slArray == null || toDeleteSLTList == null");
			return;
		}
		ArrayList newSLList = new ArrayList();
		for (int index = 0; index < slArray.length; index++) {
			if (toDeleteSLTList.contains(slArray[index].getSubLimitType())) {
				DefaultLogger.debug(this, "Delete a sub limit - sub limit type : " + slArray[index].getSubLimitType());
				continue;
			}
			else {
				newSLList.add(slArray[index]);
			}
		}
		slArray = (ISubLimit[]) newSLList.toArray(new ISubLimit[newSLList.size()]);
		DefaultLogger.debug(this, "After delete,Num of sub limit : " + (slArray == null ? 0 : slArray.length));
		cLimitMap.setSubLimit(slArray);
	}
}
