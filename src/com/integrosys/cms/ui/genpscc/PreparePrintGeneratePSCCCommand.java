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
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.bus.OBCreditStatus;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateCustomerDetail;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/26 13:16:31 $ Tag: $Name: $
 */
public class PreparePrintGeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PreparePrintGeneratePSCCCommand() {
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
				{ "certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", REQUEST_SCOPE },
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", FORM_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "custDetail", "com.integrosys.cms.app.sccertificate.bus.ISCCertificateCustomerDetail", SERVICE_SCOPE } });
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
			IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get("certTrxVal");
			String custTypeTrxID = trxValue.getTransactionID();
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			HashMap hm = proxy.getPartialSCCertificate(limit, cust, custTypeTrxID, false);
			ISCCertificateCustomerDetail custDetail = (ISCCertificateCustomerDetail) hm.get(ICMSConstant.PSCC_OWNER);
			ICreditGrade creditGrade = custDetail.getCreditGrade();
			if (creditGrade == null) {
				custDetail.setCreditGrade(new OBCreditGrade());
			}
			ICreditStatus creditStatus = custDetail.getCreditStatus();
			if (creditStatus == null) {
				custDetail.setCreditStatus(new OBCreditStatus());
			}
			IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) hm.get(ICMSConstant.PSCC);
			IPartialSCCertificate cert = null;
			String isView = (String) map.get("isView");
			if ((isView != null) && "true".equals(isView)) {
				cert = certTrxVal.getPartialSCCertificate();
			}
			else if ((ICMSConstant.STATE_REJECTED.equals(certTrxVal.getStatus()))
					|| (ICMSConstant.STATE_ACTIVE.equals(certTrxVal.getStatus()))) {
				cert = certTrxVal.getPartialSCCertificate();
				if (cert == null) {
					cert = certTrxVal.getStagingPartialSCCertificate();
				}
			}
			else {
				cert = certTrxVal.getStagingPartialSCCertificate();
			}
			resultMap.put("certTrxVal", certTrxVal);
			resultMap.put("cert", cert);
			resultMap.put("custDetail", custDetail);
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
