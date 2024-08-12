/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genccc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/12/12 10:03:12 $ Tag: $Name: $
 */
public class PrepareGenerateCCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareGenerateCCCCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "certSummary", "java.util.List", SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.List", REQUEST_SCOPE },
				{ "countryValues", "java.util.List", REQUEST_SCOPE },
				{ "creditOrgCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "creditOrgCodeValues", "java.util.List", REQUEST_SCOPE },
				{ "seniorOrgCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "seniorOrgCodeValues", "java.util.List", REQUEST_SCOPE } });
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

			CountryList list = CountryList.getInstance();
			ArrayList countryLabels = new ArrayList(list.getCountryLabels());
			ArrayList countryValues = new ArrayList(list.getCountryValues());
			resultMap.put("countryLabels", countryLabels);
			resultMap.put("countryValues", countryValues);

			ICCCertificate cert = getCCCertificate(map);
			ArrayList creditOrgCodeLabels = new ArrayList();
			ArrayList creditOrgCodeValues = new ArrayList();
			ArrayList seniorOrgCodeLabels = new ArrayList();
			ArrayList seniorOrgCodeValues = new ArrayList();
			if (cert != null) {
				IBookingLocation creditLoc = cert.getCreditOfficerLocation();
				IBookingLocation seniorLoc = cert.getSeniorOfficerLocation();
				if (creditLoc != null) {
					CountryOrgCodeUtil.fillOrgCode2List(creditLoc.getCountryCode(), creditOrgCodeValues,
							creditOrgCodeLabels);
				}
				if (seniorLoc != null) {
					CountryOrgCodeUtil.fillOrgCode2List(seniorLoc.getCountryCode(), seniorOrgCodeValues,
							seniorOrgCodeLabels);
				}
			}
			resultMap.put("creditOrgCodeLabels", creditOrgCodeLabels);
			resultMap.put("creditOrgCodeValues", creditOrgCodeValues);
			resultMap.put("seniorOrgCodeLabels", seniorOrgCodeLabels);
			resultMap.put("seniorOrgCodeValues", seniorOrgCodeValues);

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

	private ICCCertificate getCCCertificate(HashMap map) {
		ICCCertificate cert = null;
		try {
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile limit = null;
			if (cust.getNonBorrowerInd()) {
				limit = new OBLimitProfile();
			}
			else {
				limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			}

			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			HashMap hm = null;
			String index = (String) map.get("index");
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			DefaultLogger.debug(this, "Index : " + (index == null ? "NULL" : index));
			DefaultLogger.debug(this, "CustTypeTrxID : " + (custTypeTrxID == null ? "NULL" : custTypeTrxID));
			if ((custTypeTrxID == null) && (index == null)) {
				return null;
			}
			else if (custTypeTrxID != null) {
				hm = proxy.getCCCertificate(limit, cust, custTypeTrxID);
			}
			else {
				List list = (List) map.get("certSummary");
				CCCertificateSummary certificate = (CCCertificateSummary) list.get(Integer.parseInt(index));
				hm = proxy.getCCCertificate(limit, cust, certificate);
			}

			ICCCertificateTrxValue certTrxVal = (ICCCertificateTrxValue) hm.get(ICMSConstant.CCC);
			if (certTrxVal != null) {
				if (certTrxVal.getTransactionID() == null) {
					cert = certTrxVal.getStagingCCCertificate();
				}
				else {
					cert = certTrxVal.getCCCertificate();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cert;
	}
}
