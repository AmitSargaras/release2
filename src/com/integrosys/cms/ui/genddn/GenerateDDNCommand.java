/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.TATComparator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class GenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GenerateDDNCommand() {
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
                { "deferredList", "java.util.List", SERVICE_SCOPE },
                { "deferredApprovalList", "java.util.List", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "custDetail", "com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail", SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE },
                { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE}});
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
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
            List deferredList = map.get("deferredList") == null ? null : (List)map.get("deferredList");
            List deferredApprovalList = map.get("deferredApprovalList") == null ? null : (List)map.get("deferredApprovalList");
			String event = (String) map.get("event");
			//boolean wip = proxy.hasPendingGenerateDDNTrx(limitProfile);
			/*if ((!"view_generate_ddn".equals(event)) && (!"view_remarks".equals(event)) && wip) {
				resultMap.put("wip", "wip");
			}
			else {*/
				// HashMap hm =proxy.getNewBCADDN (limitProfile, cust);
				HashMap hm = new HashMap();
			/*	if ("view_generate_ddn".equals(event)) {
					DefaultLogger.debug(this, "<<<<<<<<<<< call get latest remarks proxy");
					// hm = proxy.getDDN(limitProfile, cust, true);
					hm = proxy.getPreviousDDN(limitProfile, cust);
					DefaultLogger.debug(this, "<<<<<<<<<<< after call get latest remarks proxy");
				}
				else {
                    *//**
                     * Chee Hong - include 'deferredList' into getDDN for systemCreateDefaultDDN function call. Deferred document will be store into DDNITEM level.
                     * *//*
					hm = proxy.getDDN(limitProfile, cust, deferredList, deferredApprovalList);
				}
				IDDNTrxValue certTrxVal = (IDDNTrxValue) hm.get(ICMSConstant.DDN);*/
                
		/*		if ("view_generate_ddn".equals(event)
						&& ((certTrxVal == null) || ICMSConstant.STATE_NEW.equals(certTrxVal.getStatus()) || (ICMSConstant.STATE_PENDING_CREATE
								.equals(certTrxVal.getFromState()) && ICMSConstant.STATE_REJECTED.equals(certTrxVal
								.getStatus())))) {
					resultMap.put("na", "na");
				}
				else {
					IDDNCustomerDetail custDetail = (IDDNCustomerDetail) hm.get(ICMSConstant.DDN_OWNER);
					IDDN cert = certTrxVal == null ? null : certTrxVal.getDDN();

					resultMap.put("certTrxVal", certTrxVal);
					resultMap.put("cert", cert);
					resultMap.put("frame", "true");// used to apply frames
					resultMap.put("custDetail", custDetail);

					// get BFL final issue date. CR36
					Date bflFinalIssueDate = null;
					String bflFinalIssueDateStr = "";
					ITATEntry[] entries = limitProfile.getTATEntries();
					if ((entries != null) && (entries.length != 0)) {
						Arrays.sort(entries, new TATComparator());
						for (int ii = (entries.length - 1); ii >= 0; ii--) {
							if (ICMSConstant.TAT_CODE_ISSUE_FINAL_BFL.equals(entries[ii].getTATServiceCode())) {
								bflFinalIssueDate = entries[ii].getTATStamp();
								DefaultLogger.debug(this, "Final BFL Date =" + bflFinalIssueDate);
							}
							else if (ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL.equals(entries[ii].getTATServiceCode())) {
								bflFinalIssueDate = entries[ii].getTATStamp();
								DefaultLogger.debug(this, "Clean BFL Date =" + bflFinalIssueDate);
							}
							else if (ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL.equals(entries[ii]
									.getTATServiceCode())) {
								bflFinalIssueDate = entries[ii].getTATStamp();
								DefaultLogger.debug(this, "Special Clean BFL Date =" + bflFinalIssueDate);
							}
						}
					}
					DefaultLogger.debug(this, "bflFinalIssueDate = " + bflFinalIssueDate);
					bflFinalIssueDateStr = DateUtil.convertToDisplayDate(bflFinalIssueDate);
					DefaultLogger.debug(this, "bflFinalIssueDateStr = " + bflFinalIssueDateStr);
					resultMap.put("bflFinalIssueDate", bflFinalIssueDateStr);
					// --- end CR36
				}*/

                //let get all the documents out...TODO : Might need a command class to cater this..so right now...let put it here
                DocumentHeldSearchCriteria criteria = new DocumentHeldSearchCriteria();
                criteria.setCompletedOnly(false);
                if (!cust.getNonBorrowerInd()) { // if borrower
                    long limitProfileID = limitProfile.getLimitProfileID();
                    criteria.setLimitProfileID(limitProfileID);
                }
                else { // non-borrower
                    long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

                    if (limitProfile != null) {
                        limitProfileID = limitProfile.getLimitProfileID();
                    }

                    criteria.setLimitProfileID(limitProfileID);
                    criteria.setSubProfileID(cust.getCustomerID());
                    criteria.setSearchCategory(DocumentHeldSearchCriteria.CATEGORY_NON_BORROWER);
                }
                ICheckListProxyManager checkListProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
                HashMap docListMap = checkListProxy.getDocumentsHeld(criteria);
                resultMap.put("docsHeldMap", docListMap);
                //System.out.println("Doc Held Map : " + docListMap.toString());
		//	}
		}
		catch (DDNNotRequiredException e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			resultMap.put("error", "error");
		}
		catch (DDNException e) {
			DefaultLogger.error(this, "got exception in doExecute" + e.getMessage());
			String errCode = e.getErrorCode();

            if (errCode == null) {
                String errMsg = e.getMessage();
                if (errMsg.indexOf("#Error Code") != -1) {
                    errCode = errMsg.substring(errMsg.indexOf("[") + 1, errMsg.indexOf("]"));
                }
            }

            if (errCode == null) {
				throw (new CommandProcessingException(e.getMessage()));
			}            

			resultMap.put("error", errCode);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
