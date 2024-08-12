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
import com.integrosys.cms.app.common.bus.IBookingLocation;
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
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/26 01:57:24 $ Tag: $Name: $
 */
public class ReadStagingGeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingGeneratePSCCCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "closeFlag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE } });
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
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			HashMap hm = proxy.getPartialSCCertificate(limit, cust, custTypeTrxID);
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
			IPartialSCCertificate cert = certTrxVal.getStagingPartialSCCertificate();
			refreshLocationDetails(cert, resultMap); // R1.5 CMS-3345

			resultMap.put("certTrxVal", certTrxVal);
			resultMap.put("cert", cert);
			resultMap.put("custDetail", custDetail);
			resultMap.put("closeFlag", "true");
			resultMap.put("frame", "false");// used to hide frames when user
											// comes from to do list
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

	private void refreshLocationDetails(IPartialSCCertificate cert, HashMap resultMap) {
		IBookingLocation creditLoc = null;
		IBookingLocation seniorLoc = null;
		if (cert != null) {
			creditLoc = cert.getCreditOfficerLocation();
			seniorLoc = cert.getSeniorOfficerLocation();
		}
		CountryOrgCodeUtil.fillCountryOrgCode2Map(resultMap, creditLoc, seniorLoc);
	}

}
