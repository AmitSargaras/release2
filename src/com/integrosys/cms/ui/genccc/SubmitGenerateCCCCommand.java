/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genccc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.11.28.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class SubmitGenerateCCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitGenerateCCCCommand() {
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
				{ "certTrxVal", "com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
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
			ICCCertificateTrxValue certTrxVal = (ICCCertificateTrxValue) map.get("certTrxVal");
			ICCCertificate cert = (ICCCertificate) map.get("cert");

			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			// ILimitProfile limit = null;
			/*
			 * if (cust.getNonBorrowerInd()) {
			 * ctx.setTrxCountryOrigin(cust.getOriginatingLocation
			 * ().getCountryCode()); } else { limit =
			 * (ILimitProfile)map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			 * ctx
			 * .setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode
			 * ()); }
			 */
			ctx.setTrxCountryOrigin(CheckListUtil.getTrxCountry(cert.getCheckListID()));
			if (ICMSConstant.STATE_REJECTED.equals(certTrxVal.getStatus())) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker edit generate");
				certTrxVal = proxy.makerEditRejectedGenerateCCC(ctx, certTrxVal, cert);
			}
			else {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker generate");
				certTrxVal = proxy.makerGenerateCCC(ctx, certTrxVal, cert);
			}
			resultMap.put("request.ITrxValue", certTrxVal);
			resultMap.put("certTrxVal", certTrxVal);

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
