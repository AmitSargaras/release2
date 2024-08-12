/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genpscc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.bus.OBCreditStatus;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateCustomerDetail;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.genscc.CertificationHelper;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public class GeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GeneratePSCCCommand() {
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
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } // R1
																										// .5
																										// CR146
		});
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
				{ "custDetail", "com.integrosys.cms.app.sccertificate.bus.ISCCertificateCustomerDetail", SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE }, });
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
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>> Generate PSCC: Inside doExecute()");
		try {
			CertificationHelper.verifyCCCertificate(map, ICMSErrorCodes.PSCC_CCC_NOT_PERFECTED);
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			String event = (String) map.get("event");
			boolean wip = proxy.hasPendingGeneratePartialSCCTrx(limit);
			if ((!"view_generate_pscc".equals(event)) && wip) {
				resultMap.put("wip", "wip");
				DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			}
			else {
				HashMap hm = null;
				if (event.equals("view_generate_pscc")) {
					hm = proxy.getPartialSCCertificate(limit, cust, false);
				}
				else {
					hm = proxy.getPartialSCCertificate(limit, cust);
				}
				ISCCertificateCustomerDetail custDetail = (ISCCertificateCustomerDetail) hm
						.get(ICMSConstant.PSCC_OWNER);
				ICreditGrade creditGrade = custDetail.getCreditGrade();
				if (creditGrade == null) {
					custDetail.setCreditGrade(new OBCreditGrade());
				}
				ICreditStatus creditStatus = custDetail.getCreditStatus();
				if (creditStatus == null) {
					custDetail.setCreditStatus(new OBCreditStatus());
				}
				IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) hm.get(ICMSConstant.PSCC);
				if ("view_generate_pscc".equals(event)
						&& ((certTrxVal.getTransactionID() == null) || (certTrxVal.getPartialSCCertificate() == null))) {
					resultMap.put("na", "na");
				}
				else {
					IPartialSCCertificate cert = null;
					if (certTrxVal != null) {
						if (certTrxVal.getTransactionID() == null) {
							cert = certTrxVal.getStagingPartialSCCertificate();
						}
						else {
							cert = certTrxVal.getPartialSCCertificate();
						}

						// transaction is active but not from checker approval
						if (ICMSConstant.STATE_ACTIVE.equals(certTrxVal.getStatus())
								&& !ICMSConstant.STATE_PENDING_UPDATE.equals(certTrxVal.getFromState())) {
							certTrxVal.setRemarks(cert.getRemarks());
						}
					}

					resultMap.put("certTrxVal", certTrxVal);
					resultMap.put("cert", cert);
					resultMap.put("frame", "true");// used to apply frames
					resultMap.put("custDetail", custDetail);
					// DefaultLogger.debug(this,"custDetail"+custDetail);
				}
			}
		}
		catch (SCCertificateException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			//resultMap.put("error", e.getErrorCode());
            String errorCode = "";
            if (e.getErrorCode() == null) {
                String errMsg = e.getMessage();
                if (errMsg.indexOf("#Error Code") != -1) {
                    errorCode = errMsg.substring(errMsg.indexOf("[") + 1, errMsg.indexOf("]"));
                }
            } else {
                errorCode = e.getErrorCode();
            }
            resultMap.put("error", errorCode);
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
