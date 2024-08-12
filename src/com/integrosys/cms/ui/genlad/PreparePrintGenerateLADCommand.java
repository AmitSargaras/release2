/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.bus.OBCreditStatus;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/25 08:44:49 $ Tag: $Name: $
 */
public class PreparePrintGenerateLADCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PreparePrintGenerateLADCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "custDetail", "com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail", SERVICE_SCOPE },
				{ "isView", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "certTrxValPrev", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "certPrev", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "certPrev", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
                { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
            //System.out.println("Print GDDN Map : " + map.toString());
			IDDNTrxValue trxValue = (IDDNTrxValue) map.get("certTrxVal");
			String custTypeTrxID = trxValue.getTransactionID();

			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();

			String isView = (String) map.get("isView");

			if (trxValue != null) {
				DefaultLogger.debug(this, "**** From State >" + trxValue.getFromState());
				DefaultLogger.debug(this, "**** To Status  >" + trxValue.getStatus());
			}

			// CMS-1831
			HashMap hm = null;
			if (isView != null) {
				hm = new HashMap();
				hm.put(ICMSConstant.DDN_OWNER, map.get("custDetail"));
				hm.put(ICMSConstant.DDN, trxValue);
			}
			else if (((ICMSConstant.STATE_PENDING_UPDATE.equals(trxValue.getStatus())))
					|| ((ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())))) {
				DefaultLogger.debug(this, "After  Submit Print Previous.");
				hm = proxy.getDDN(limit, cust, custTypeTrxID);

			}
			else {
				DefaultLogger.debug(this, "Before Submit Print Previous.");
				hm = proxy.getPreviousDDN(limit, cust, custTypeTrxID);
			}

			if (hm != null) {
				IDDNCustomerDetail custDetail = (IDDNCustomerDetail) hm.get(ICMSConstant.DDN_OWNER);

				ICreditGrade creditGrade = custDetail.getCreditGrade();
				if (creditGrade == null) {
					custDetail.setCreditGrade(new OBCreditGrade());
				}

				ICreditStatus creditStatus = custDetail.getCreditStatus();
				if (creditStatus == null) {
					custDetail.setCreditStatus(new OBCreditStatus());
				}
				IDDNTrxValue certTrxVal = (IDDNTrxValue) hm.get(ICMSConstant.DDN);

				IDDN cert = null;
				if (certTrxVal != null) {
					DefaultLogger.debug(this, "---- From State >" + certTrxVal.getFromState());
					DefaultLogger.debug(this, "---- To Status  >" + certTrxVal.getStatus());
					if ((isView != null) && "true".equals(isView)) {
						cert = certTrxVal.getDDN();
					}
					else if (certTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
						cert = certTrxVal.getDDN();
					}
					else {
						cert = certTrxVal.getStagingDDN();
					}
				}
				else {
					DefaultLogger.debug(this, "certTrxVal is null " + certTrxVal);
				}
				resultMap.put("certTrxValPrev", certTrxVal);
				resultMap.put("certPrev", cert);
			}
			else {
				DefaultLogger.debug(this, "HashMap is null " + hm);
			}

            //let get all the documents out...TODO : Might need a command class to cater this..so right now...let put it here
            DocumentHeldSearchCriteria criteria = new DocumentHeldSearchCriteria();
            criteria.setCompletedOnly(false);
            if (!cust.getNonBorrowerInd()) { // if borrower
                long limitProfileID = limit.getLimitProfileID();
                criteria.setLimitProfileID(limitProfileID);
            }
            else { // non-borrower
                long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

                if (limit != null) {
                    limitProfileID = limit.getLimitProfileID();
                }

                criteria.setLimitProfileID(limitProfileID);
                criteria.setSubProfileID(cust.getCustomerID());
                criteria.setSearchCategory(DocumentHeldSearchCriteria.CATEGORY_NON_BORROWER);
            }
            ICheckListProxyManager checkListProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
            HashMap docListMap = checkListProxy.getDocumentsHeld(criteria);
            resultMap.put("docsHeldMap", docListMap);
            //System.out.println("Doc Held Map : " + docListMap.toString());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
