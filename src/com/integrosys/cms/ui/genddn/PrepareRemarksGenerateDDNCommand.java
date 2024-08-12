/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
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
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.TATComparator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/20 10:27:55 $ Tag: $Name: $
 */
public class PrepareRemarksGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareRemarksGenerateDDNCommand() {
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
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE }, });
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
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
			String event = (String) map.get("event");
			boolean wip = proxy.hasPendingGenerateDDNTrx(limit);
			DefaultLogger.debug(this, "wip indicator " + wip);
			if ((!"view_remarks".equals(event)) && wip) {
				resultMap.put("wip", "wip");
				DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			}
			else {
				try {
					IDDNTrxValue trxValue = (IDDNTrxValue) map.get("certTrxVal");
					String custTypeTrxID = trxValue.getTransactionID();
					// HashMap hm = proxy.getNewBCADDN(limit, cust,
					// custTypeTrxID);
					if (!proxy.isDDNGeneratedLatest(limit)) {
						DefaultLogger.info(this, "DDN is not the latest certificate");
						resultMap.put("na", "na");
					}
					else {
						HashMap hm = new HashMap();
						if ("view_remarks".equals(event)) {
							hm = proxy.getDDN(limit, cust, custTypeTrxID, true);
						}
						else {
							hm = proxy.getDDN(limit, cust, custTypeTrxID);
						}
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

						if ((ICMSConstant.STATE_REJECTED.equals(certTrxVal.getFromState()))
								&& (ICMSConstant.STATE_ACTIVE.equals(certTrxVal.getStatus()))) {
							cert = certTrxVal.getDDN();
							if (cert == null) {
								cert = certTrxVal.getStagingDDN();
							}
						}
						else {
							cert = certTrxVal.getStagingDDN();
						}
						if ((cert == null) || certTrxVal.getStatus().equals(ICMSConstant.STATE_NEW)) {
							DefaultLogger.debug(this, " cert is NULL");
							resultMap.put("na", "na");
						}
						else {
							DefaultLogger.debug(this, " ***********************");
							DefaultLogger.debug(this, " Approval date =" + cert.getApprovalDate());
							DefaultLogger.debug(this, " Expiry   date =" + cert.getDeferredToDate());
							if ((cert.getApprovalDate() == null) || (cert.getDeferredToDate() == null)) {
								DefaultLogger.debug(this, " DDN NA  =");
								resultMap.put("na", "na");
							}
							else {
								// DefaultLogger.debug(this,
								// " *********************** DDN ="
								// +cert.toString());
							}
						}
						resultMap.put("certTrxVal", certTrxVal);
						resultMap.put("cert", cert);

						// get BFL final issue date. CR36
						Date bflFinalIssueDate = null;
						String bflFinalIssueDateStr = "";
						ITATEntry[] entries = limit.getTATEntries();
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
						if (bflFinalIssueDate != null) {
							bflFinalIssueDateStr = DateUtil.convertToDisplayDate(bflFinalIssueDate);
						}
						DefaultLogger.debug(this, "bflFinalIssueDateStr = " + bflFinalIssueDateStr);
						resultMap.put("bflFinalIssueDate", bflFinalIssueDateStr);
					}
					// --- end CR36
				}
				catch (Exception e) {
					resultMap.put("na", "na");
					DefaultLogger.debug(this, "No trxValue / DDN Present, have to generate DDN first.");
				}
			}
			// }

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
