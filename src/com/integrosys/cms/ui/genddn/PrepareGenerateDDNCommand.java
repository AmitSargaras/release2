/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNNotRequiredException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/12/12 10:03:30 $ Tag: $Name: $
 */
public class PrepareGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareGenerateDDNCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE } });
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

			IDDN cert = getCCCertificate(map, resultMap);

			ArrayList creditOrgCodeLabels = new ArrayList();
			ArrayList creditOrgCodeValues = new ArrayList();
			ArrayList seniorOrgCodeLabels = new ArrayList();
			ArrayList seniorOrgCodeValues = new ArrayList();
			if (cert != null) {
				DefaultLogger.debug(this, "cert != null");
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
			else {
				DefaultLogger.debug(this, "cert == null");
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

	private IDDN getCCCertificate(HashMap map, HashMap resultMap) {
		IDDN cert = null;
		try {
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
			String custTypeTrxID = (String) map.get("trxID");
			HashMap hm = null;
			if (custTypeTrxID == null) {
				hm = proxy.getPreviousDDN(limitProfile, cust);
			}
			else {
				hm = proxy.getDDN(limitProfile, cust, custTypeTrxID);
			}
			IDDNTrxValue certTrxVal = (IDDNTrxValue) hm.get(ICMSConstant.DDN);
			if (certTrxVal != null) {
				if (certTrxVal.getTransactionID() == null) {
					cert = certTrxVal.getStagingDDN();
				}
				else {
					cert = certTrxVal.getDDN();
				}
			}
		}catch (DDNNotRequiredException ddnnre) {
            DefaultLogger.debug(this, "got exception in doExecute" + ddnnre);
            String errorCode = "";
            if (ddnnre.getErrorCode() == null) {
                String errMsg = ddnnre.getMessage();
                if (errMsg.indexOf("#Error Code") != -1) {
                    errorCode = errMsg.substring(errMsg.indexOf("[") + 1, errMsg.indexOf("]"));
                }
            } else {
                errorCode = ddnnre.getErrorCode();
            }
            resultMap.put("error", errorCode);
        }catch (DDNException ddne) {
            DefaultLogger.debug(this, "got exception in doExecute" + ddne);
            String errorCode = "";
            if (ddne.getErrorCode() == null) {
                String errMsg = ddne.getMessage();
                if (errMsg.indexOf("#Error Code") != -1) {
                    errorCode = errMsg.substring(errMsg.indexOf("[") + 1, errMsg.indexOf("]"));
                }
            } else {
                errorCode = ddne.getErrorCode();
            }
            resultMap.put("error", errorCode);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		return cert;
	}
}