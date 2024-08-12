/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/MakerSubmitLimitProfileCommand.java,v 1.7 2004/06/14 04:49:21 visveswari Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/14 04:49:21 $ Tag: $Name: $
 */
public class MakerSubmitLimitProfileCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public MakerSubmitLimitProfileCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },

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
				{ "limitprofileOBJ", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "fam", "java.lang.String", SERVICE_SCOPE }, { "famcode", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue limitprofiletrxvalue = new OBLimitProfileTrxValue();
			DefaultLogger.debug(this, "after settibng ob");
			String trxID = (String) map.get("TrxId");
			limitprofiletrxvalue = limitproxy.getTrxLimitProfile(trxID);
			String fam = new String();
			String famcode = new String();
			ICMSCustomer globalcustOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			if (null == globalcustOB) {
				throw new CommandProcessingException("ICMSCustomer is null in session!");
			}
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (globalcustOB.getNonBorrowerInd()) {
				if (Long.toString(globalcustOB.getCustomerID()) != null) {
					fam = (String) limitproxy.getFAMNameByCustomer(globalcustOB.getCustomerID()).get(
							ICMSConstant.FAM_NAME);
				}
				famcode = (String) limitproxy.getFAMNameByCustomer(globalcustOB.getCustomerID()).get(
						ICMSConstant.FAM_CODE);
			}
			else {
				if (Long.toString(limitProfileOB.getLimitProfileID()) != null) {
					fam = (String) limitproxy.getFAMName(limitProfileOB.getLimitProfileID()).get(ICMSConstant.FAM_NAME);
					famcode = (String) limitproxy.getFAMName(limitProfileOB.getLimitProfileID()).get(
							ICMSConstant.FAM_CODE);
				}
			}
			result.put("fam", fam);
			result.put("famcode", famcode);
			// if (limitprofiletrxvalue.getStatus().equals(ICMSConstant.
			// STATE_REJECTED)) {
			result.put("limitprofileOb", limitprofiletrxvalue.getStagingLimitProfile());
			// Added for CR CMS-334
			result.put("limitprofileOBJ", limitprofiletrxvalue.getStagingLimitProfile());
			/*
			 * } else { result.put("limitprofileOb",
			 * limitprofiletrxvalue.getLimitProfile()); //Added for CR CMS-334
			 * result.put("limitprofileOBJ",
			 * limitprofiletrxvalue.getLimitProfile()); }
			 */
			result.put("trxValue", limitprofiletrxvalue);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
