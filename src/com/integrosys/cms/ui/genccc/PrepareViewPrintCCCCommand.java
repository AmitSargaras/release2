/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genccc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;
import com.integrosys.cms.app.customer.bus.OBCreditStatus;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/26 13:32:42 $ Tag: $Name: $
 */
public class PrepareViewPrintCCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareViewPrintCCCCommand() {
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
				{ "trxValueID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "certTrxVal", "com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", FORM_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "custDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail", REQUEST_SCOPE },// to
																															// be
																															// used
																															// only
																															// for
																															// print
																															// ccc
				{ "custDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail", SERVICE_SCOPE },
				{ "coBorrowerDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail",
						SERVICE_SCOPE },
				{ "pledgorDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail",
						SERVICE_SCOPE }, { "pledgorColList", "java.util.List", REQUEST_SCOPE }, });
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
			String custTypeTrxID = (String) map.get("trxValueID");
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile limit = null;
			if (cust.getNonBorrowerInd()) {
				limit = new OBLimitProfile();
			}
			else {
				limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			}
			String event = (String) map.get("event");
			HashMap hm = proxy.getCCCertificate(limit, cust, custTypeTrxID, false);
			ICCCertificateCustomerDetail custDetail = (ICCCertificateCustomerDetail) hm.get(ICMSConstant.CCC_OWNER);
			ICreditGrade creditGrade = custDetail.getCreditGrade();
			if (creditGrade == null) {
				custDetail.setCreditGrade(new OBCreditGrade());
			}
			ICreditStatus creditStatus = custDetail.getCreditStatus();
			if (creditStatus == null) {
				custDetail.setCreditStatus(new OBCreditStatus());
			}
			ICCCertificateTrxValue certTrxVal = (ICCCertificateTrxValue) hm.get(ICMSConstant.CCC);
			ICCCertificate cert = null;
			if (((ICMSConstant.STATE_REJECTED.equals(certTrxVal.getFromState())) && (ICMSConstant.STATE_ACTIVE
					.equals(certTrxVal.getStatus())))
					|| "view_print_doc".equals(event)) {
				cert = certTrxVal.getCCCertificate();
			}
			else {
				cert = certTrxVal.getStagingCCCertificate();
			}
			resultMap.put("certTrxVal", certTrxVal);
			resultMap.put("cert", cert);

			ICCCertificateCustomerDetail coBorrowerDetail = (ICCCertificateCustomerDetail) hm
					.get(ICMSConstant.CCC_COBORROWER_DETAIL);
			if (coBorrowerDetail != null) {
				if (coBorrowerDetail.getCreditGrade() == null) {
					coBorrowerDetail.setCreditGrade(new OBCreditGrade());
				}

				if (coBorrowerDetail.getCreditStatus() == null) {
					coBorrowerDetail.setCreditStatus(new OBCreditStatus());
				}
			}

			ICCCertificateCustomerDetail pledgorDetail = (ICCCertificateCustomerDetail) hm
					.get(ICMSConstant.CCC_PLEDGOR_DETAIL);
			if (pledgorDetail != null) {
				if (pledgorDetail.getCreditGrade() == null) {
					pledgorDetail.setCreditGrade(new OBCreditGrade());
				}

				if (pledgorDetail.getCreditStatus() == null) {
					pledgorDetail.setCreditStatus(new OBCreditStatus());
				}
				ICollateral[] pledgorColList = (ICollateral[]) hm.get(ICMSConstant.CCC_PLEDGOR_COLLATERAL_LIST);
				List l = Arrays.asList(pledgorColList);
				resultMap.put("pledgorColList", l);
			}
			resultMap.put("frame", "true");// used to apply frames
			resultMap.put("custDetail", custDetail);
			resultMap.put("coBorrowerDetail", coBorrowerDetail);
			resultMap.put("pledgorDetail", pledgorDetail);
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
