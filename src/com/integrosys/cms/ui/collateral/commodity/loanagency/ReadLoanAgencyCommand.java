/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/ReadLoanAgencyCommand.java,v 1.11 2004/11/02 01:57:17 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/11/02 01:57:17 $ Tag: $Name: $
 */
public class ReadLoanAgencyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] {
				{ "loanAgencyObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE },
				{ "actualLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE },
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE }, { "actualSecID", "java.lang.String", REQUEST_SCOPE },
				{ "stageSecID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceSecID", "java.lang.String", SERVICE_SCOPE }, });
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

		long index = Long.parseLong((String) map.get("indexID"));

		String event = (String) map.get("event");
		String from_event = (String) map.get("from_page");
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ILoanAgency objSec = null;
		String securityID = null;
		String collateralID = null;
		if (event.equals(EVENT_PREPARE)) {
			objSec = new OBLoanAgency();
		}
		else {
			HashMap limitMap = new HashMap();
			if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PROCESS)) {
				ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("actual");
				String actualSecID = null;
				String stageSecID = null;
				Object[] actual = getItem(col, index);
				ILoanAgency actualObj = null;
				if (actual != null) {
					actualObj = (ILoanAgency) actual[0];
					actualSecID = (String) actual[1];
				}

				col = (ICommodityCollateral[]) trxValueMap.get("staging");
				Object[] staging = getItem(col, index);
				ILoanAgency stageObj = null;
				if (staging != null) {
					stageObj = (ILoanAgency) staging[0];
					stageSecID = (String) staging[1];
				}

				result.put("actualLoanAgency", actualObj);
				result.put("stageLoanAgency", stageObj);
				result.put("actualSecID", actualSecID);
				result.put("stageSecID", stageSecID);
				objSec = stageObj;
				securityID = stageSecID;

				limitMap = (HashMap) trxValueMap.get("stageLimit");
				if (stageObj == null) {
					objSec = actualObj;
					securityID = actualSecID;
					limitMap = (HashMap) trxValueMap.get("actualLimit");
				}
			}
			else {
				ICommodityCollateral[] col;
				int secIndex = Integer.parseInt((String) map.get("secIndexID"));
				if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_READ)) {
					col = (ICommodityCollateral[]) trxValueMap.get("actual");
					limitMap = (HashMap) trxValueMap.get("actualLimit");
				}
				else {
					col = (ICommodityCollateral[]) trxValueMap.get("staging");
					limitMap = (HashMap) trxValueMap.get("stageLimit");
				}
				objSec = col[secIndex].getLoans()[(int) index];
				securityID = String.valueOf(col[secIndex].getSCISecurityID());
			}
			if (objSec.getLimitIDs() != null) {
				ILoanLimit[] limitList = objSec.getLimitIDs();
				for (int i = 0; i < limitList.length; i++) {
					if ((limitList[i].getLimitID() != ICMSConstant.LONG_INVALID_VALUE)
							&& (limitMap.get(String.valueOf(limitList[i].getLimitID())) == null)) {
						result.put("limitProtection", "limitProtection");
						break;
					}
				}
			}
		}
		collateralID = getCollateralIDBySecurityID((ICommodityCollateral[]) trxValueMap.get("actual"), securityID);
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<< HSHII - collateralID: " + collateralID);
		HashMap loanAgencyMap = new HashMap();
		loanAgencyMap.put("obj", objSec);
		loanAgencyMap.put("securityID", securityID);
		result.put("from_page", from_event);
		result.put("loanAgencyObj", loanAgencyMap);
		result.put("serviceSecID", collateralID);
		try {
			ILoanAgency serviceObj = (ILoanAgency) AccessorUtil.deepClone(objSec);
			result.put("serviceLoanAgency", serviceObj);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private Object[] getItem(ICommodityCollateral col[], long commonRef) {
		Object[] item = null;
		if (col == null) {
			return item;
		}
		item = new Object[2];
		for (int i = 0; i < col.length; i++) {
			if (col[i] != null) {
				ILoanAgency[] temp = col[i].getLoans();
				if (temp != null) {
					for (int j = 0; j < temp.length; j++) {
						if (temp[j].getCommonRef() == commonRef) {
							item[0] = temp[j];
							item[1] = String.valueOf(col[i].getSCISecurityID());
						}
						else {
							continue;
						}
					}
				}
			}
		}
		return item;
	}

	private String getCollateralIDBySecurityID(ICommodityCollateral[] col, String securityID) {
		String collateralID = null;
		if ((securityID != null) && (securityID.length() > 0)) {
			// long secID = Long.parseLong(securityID);
			if (col != null) {
				boolean found = false;
				for (int i = 0; !found && (i < col.length); i++) {
					if (col[i].getSCISecurityID().equals(securityID)) {
						collateralID = String.valueOf(col[i].getCollateralID());
						found = true;
					}
				}
			}
		}
		return collateralID;
	}
}
