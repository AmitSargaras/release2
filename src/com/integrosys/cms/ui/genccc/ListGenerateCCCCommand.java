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
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2005/11/21 11:05:26 $ Tag: $Name: $
 */
public class ListGenerateCCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListGenerateCCCCommand() {
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "certSummary", "java.util.List", SERVICE_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE }, { "error", "java.lang.String", REQUEST_SCOPE },
		// {"dateGenerated","java.util.ArrayList",REQUEST_SCOPE},
		// {"trxValueID","java.util.ArrayList",REQUEST_SCOPE}
		});
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

			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			CCCertificateSummary[] certSummary = null;
			if (customer.getNonBorrowerInd()) {
				certSummary = proxy.getCCCertificateSummaryList(theOBTrxContext, customer);
			}
			else {
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				// DefaultLogger.debug(this,"Limit profile "+limit);
				long limitProfileID = limit.getLimitProfileID();
				theOBTrxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
				DefaultLogger.debug(this, "limitProfileID before backend call" + limitProfileID);
				certSummary = proxy.getCCCertificateSummaryList(theOBTrxContext, limit);
				resultMap.put("limitProfileID", String.valueOf(limitProfileID));
			}
			if ((certSummary != null) && (certSummary.length > 0)) {
				DefaultLogger.debug(this, "Summary size" + certSummary.length);
				// CR CMS-382 Starts
				/**********************************************************************************/
				/*
				 * ICMSCustomer cust =
				 * (ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
				 * ILimitProfile limit = null; if (cust.getNonBorrowerInd())
				 * limit = new OBLimitProfile(); else limit =
				 * (ILimitProfile)map.
				 * get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ); //List list =
				 * Arrays.asList(certSummary); CCCertificateSummary certificate
				 * = null; ArrayList cert1 = new ArrayList(); ArrayList
				 * trxValueList = new ArrayList(); for(int
				 * i=0;i<certSummary.length;i++){ //certificate =
				 * (CCCertificateSummary)list.get(i); certificate =
				 * certSummary[i]; ICCCertificate cert = null;
				 * 
				 * //CMS-210 String trxValueID=null; //CMS-210
				 * 
				 * if (certificate.allowGenerateCCC()) { HashMap hm =
				 * proxy.getCCCertificate(limit,cust,certificate);
				 * ICCCertificateTrxValue certTrxVal =
				 * (ICCCertificateTrxValue)hm.get(ICMSConstant.CCC);
				 * if(certTrxVal!=null){ trxValueID =
				 * certTrxVal.getTransactionID(); if
				 * (certTrxVal.getTransactionID() == null) cert =
				 * certTrxVal.getStagingCCCertificate(); else cert =
				 * certTrxVal.getCCCertificate(); } }else{ cert = null; }
				 * if(cert !=null) cert1.add(i,cert.getDateGenerated()); else
				 * cert1.add(i,null); trxValueList.add(i, trxValueID); }
				 * resultMap.put("dateGenerated", cert1);
				 * resultMap.put("trxValueID", trxValueList);
				 */
				/**********************************************************************************/
				// CR CMS-382 Ends
				List l = Arrays.asList(certSummary);
				resultMap.put("certSummary", l);
			}
		}
		catch (CCCertificateException e) {
			String errorCode = e.getErrorCode();
			DefaultLogger.debug(this, "ErrorCOde:" + errorCode);
			if (errorCode != null) {
				resultMap.put("error", errorCode);
			}
			else {
				DefaultLogger.error(this, "got exception in doExecute", e);
				throw (new CommandProcessingException(e.getMessage()));
			}
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
