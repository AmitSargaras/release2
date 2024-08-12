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
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/01/29 07:22:17 $ Tag: $Name: $
 */
public class ReadStagingGenerateCCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingGenerateCCCCommand() {
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
				{ "custDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail", REQUEST_SCOPE },
				{ "custDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail", SERVICE_SCOPE },
				{ "coBorrowerDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail",
						SERVICE_SCOPE },
				{ "pledgorDetail", "com.integrosys.cms.app.cccertificate.bus.ICCCertificateCustomerDetail",
						SERVICE_SCOPE }, { "pledgorColList", "java.util.List", SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", FORM_SCOPE },
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
			HashMap hm = proxy.getCCCertificate(limit, cust, custTypeTrxID);
			ICCCertificateCustomerDetail custDetail = (ICCCertificateCustomerDetail) hm.get(ICMSConstant.CCC_OWNER);
			ICreditGrade creditGrade = custDetail.getCreditGrade();
			if (creditGrade == null) {
				custDetail.setCreditGrade(new OBCreditGrade());
			}
			ICreditStatus creditStatus = custDetail.getCreditStatus();
			if (creditStatus == null) {
				custDetail.setCreditStatus(new OBCreditStatus());
			}

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
			ICCCertificateTrxValue certTrxVal = (ICCCertificateTrxValue) hm.get(ICMSConstant.CCC);
			ICCCertificate cert = certTrxVal.getStagingCCCertificate();
			resultMap.put("certTrxVal", certTrxVal);
			resultMap.put("cert", cert);
			resultMap.put("custDetail", custDetail);
			resultMap.put("coBorrowerDetail", coBorrowerDetail);
			resultMap.put("pledgorDetail", pledgorDetail);
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
}
