/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/PrepareApprovedCommCommand.java,v 1.3 2004/07/19 13:42:41 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/19 13:42:41 $ Tag: $Name: $
 */

public class PrepareApprovedCommCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "securitySubType", "java.lang.String", REQUEST_SCOPE },
				{ "productType", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "secID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "productID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "productValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "productSubID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "productSubValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
		int ind = -1;
		int secInd = -1;
		String index = (String) map.get("indexID");
		String secIndex = (String) map.get("secIndexID");

		if ((index != null) && (secIndex != null)) {
			ind = Integer.parseInt(index);
			secInd = Integer.parseInt(secIndex);
		}

		Collection secID = new ArrayList();
		if (col != null) {
			for (int i = 0; i < col.length; i++) {
				if (((secInd != -1) && (i == secInd))
						|| (!col[i].getStatus().equals(ICMSConstant.STATE_DELETED) && !col[i].getStatus().equals(
								ICMSConstant.STATE_PENDING_DELETE))) {
					secID.add(String.valueOf(col[i].getSCISecurityID()));
				}
			}
		}

		result.put("secID", secID);
		result.put("secValue", secID);
		IProfile profile = null;
		if ((ind != -1) && (secInd != -1)) {
			profile = col[secInd].getApprovedCommodityTypes()[ind].getProfile();
		}
		String event = (String) map.get("event");
		String category = (String) map.get("securitySubType");
		DefaultLogger.debug(this, "category - before prepare: " + category);
		if (event.equals(ApprovedCommAction.EVENT_PREPARE) || event.equals(ApprovedCommAction.EVENT_PREPARE_UPDATE_SUB)) {
			if ((category == null) || (category.length() == 0)) {
				if ((ind != -1) && (secInd != -1)) {
					category = profile.getCategory();
				}
			}
		}
		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		result.put("productID", categoryList.getCommProductID(category));
		result.put("productValue", categoryList.getCommProductValue(category));

		String productType = (String) map.get("productType");
		DefaultLogger.debug(this, "event is: " + event + "\tind: " + ind + "\tsecInd: " + secInd);
		if (event.equals(ApprovedCommAction.EVENT_PREPARE) || event.equals(ApprovedCommAction.EVENT_PREPARE_UPDATE_SUB)) {
			if ((ind != -1) && (secInd != -1)) {
				if (productType == null) {
					productType = profile.getProductType();
				}
			}
		}
		DefaultLogger.debug(this, "category: " + category + "\tproductType: " + productType);
		Collection productSubTypeID = new ArrayList();
		Collection productSubTypeValue = new ArrayList();
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			Map productSubTypeMap = proxy.getProductSubTypesByCategoryAndProduct(category, productType);
			if (productSubTypeMap != null) {
				productSubTypeID = productSubTypeMap.keySet();
				Iterator itr = productSubTypeID.iterator();
				while (itr.hasNext()) {
					productSubTypeValue.add(productSubTypeMap.get(itr.next()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		if ((profile != null) && !productSubTypeID.contains(String.valueOf(profile.getProfileID()))) {
			productSubTypeID.add(String.valueOf(profile.getProfileID()));
			productSubTypeValue.add(profile.getProductSubType());
		}
		result.put("productSubID", productSubTypeID);
		result.put("productSubValue", productSubTypeValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
