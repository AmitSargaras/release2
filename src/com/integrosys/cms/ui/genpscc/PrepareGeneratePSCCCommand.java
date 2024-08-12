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
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/26 01:57:24 $ Tag: $Name: $
 */
public class PrepareGeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareGeneratePSCCCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "creditOfficerLocationCountry", "java.lang.String", REQUEST_SCOPE },
				{ "creditOfficerLocationOrgCode", "java.lang.String", REQUEST_SCOPE },
				{ "seniorCreditOfficerLocationCountry", "java.lang.String", REQUEST_SCOPE },
				{ "seniorCreditOfficerLocationOrgCode", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "seniorOrgCodeValues", "java.util.List", REQUEST_SCOPE },
                { "error", "java.lang.String", REQUEST_SCOPE} });
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
			IPartialSCCertificate cert = null;
			IBookingLocation creditLoc = null;
			IBookingLocation seniorLoc = null;
			String event = (String) map.get("event");

			if (event.equals("update_return")) {
				IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) map.get("certTrxVal");
				cert = certTrxVal.getStagingPartialSCCertificate();
				creditLoc = cert.getCreditOfficerLocation();
				seniorLoc = cert.getSeniorOfficerLocation();
			}
			else {
				// if its an error event previously
				String coCountry = (String) map.get("creditOfficerLocationCountry");
				String soCountry = (String) map.get("seniorCreditOfficerLocationCountry");
				if (coCountry != null) {
					String coOrgCode = (String) map.get("creditOfficerLocationOrgCode");
					creditLoc = new OBBookingLocation(coCountry, coOrgCode);
				}
				if (soCountry != null) {
					String soOrgCode = (String) map.get("seniorCreditOfficerLocationOrgCode");
					seniorLoc = new OBBookingLocation(soCountry, soOrgCode);
				}

				if ((creditLoc == null) && (seniorLoc == null)) { // if its the
																	// first
																	// time
																	// coming
																	// into
																	// prepare
																	// event
					cert = getPSCCCertificate(map, resultMap);
					if (cert != null) {
						creditLoc = cert.getCreditOfficerLocation();
						seniorLoc = cert.getSeniorOfficerLocation();
					}
				}
			}

			CountryOrgCodeUtil.fillCountryOrgCode2Map(resultMap, creditLoc, seniorLoc);

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

	private IPartialSCCertificate getPSCCCertificate(HashMap map, HashMap resultMap) {
		IPartialSCCertificate cert = null;
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();

			String trxId = (String) map.get("trxID");
			DefaultLogger.debug(this, "TrxID : " + (trxId == null ? "NULL" : trxId));
			HashMap hm = null;
			if (trxId == null) {
				hm = proxy.getPartialSCCertificate(limit, cust);
			}
			else {
				hm = proxy.getPartialSCCertificate(limit, cust, false);
			}
			IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) hm.get(ICMSConstant.PSCC);
			if (certTrxVal != null) {
				if (certTrxVal.getTransactionID() == null) {
					cert = certTrxVal.getStagingPartialSCCertificate();
				}
				else {
					cert = certTrxVal.getPartialSCCertificate();
				}
			}
		}catch (SCCertificateException scce) {
            DefaultLogger.debug(this, "got exception in doExecute" + scce);
            String errorCode = "";
            if (scce.getErrorCode() == null) {
                String errMsg = scce.getMessage();
                if (errMsg.indexOf("#Error Code") != -1) {
                    errorCode = errMsg.substring(errMsg.indexOf("[") + 1, errMsg.indexOf("]"));        
                }
            } else {
                errorCode = scce.getErrorCode();
            }
            resultMap.put("error", errorCode);
        }catch (Exception e) {
			e.printStackTrace();
		}
		return cert;
	}
}
