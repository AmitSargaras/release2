/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/PrepareLimitProfileCommand.java,v 1.8 2004/07/30 07:31:14 pooja Exp $
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
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.ui.common.ApprovingOfficerGradeList;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/07/30 07:31:14 $ Tag: $Name: $
 */
public class PrepareLimitProfileCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public PrepareLimitProfileCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },

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
		return (new String[][] { { "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "approvingOfficerGradeListID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "approvingOfficerGradeListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "sscStatus", "java.lang.String", REQUEST_SCOPE } });
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
		CustomerSearchCriteria objSearch = new CustomerSearchCriteria();
		objSearch = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			ICMSCustomer custOb = new OBCMSCustomer();
			DefaultLogger.debug(this, "after settibng ob");
			String sscStatus = "";
			String subpid = (String) map.get("customerID");
			String limitprofileid = (String) map.get("limitProfileID");
			custOb = custproxy.getCustomer(Long.parseLong(subpid));
			result.put("customerOb", custOb);
			ILimitProfileTrxValue limitprofiletrxvalue = new OBLimitProfileTrxValue();
			ILimitProxy trxlimitproxy = LimitProxyFactory.getProxy();
			limitprofiletrxvalue = trxlimitproxy.getTrxLimitProfile(Long.parseLong(limitprofileid));
			if ((limitprofiletrxvalue.getStatus().equals(ICMSConstant.STATE_ACTIVE))
					|| (limitprofiletrxvalue.getStatus().equals(ICMSConstant.STATE_NEW))) {
				result.put("wip", "active");
				DefaultLogger.debug(this, "inside if");
			}
			else {
				result.put("wip", "passive");
				DefaultLogger.debug(this, "inside else");
			}
			/*
			int scc_status_ind = trxlimitproxy.getSCCStatus(limitprofiletrxvalue.getLimitProfile());
			if (ICMSConstant.SCC_NOT_APPLICABLE == scc_status_ind) {
				sscStatus = "N.A.";
			}
			result.put("sscStatus", sscStatus);
			*/
			ApprovingOfficerGradeList approvingOfficerGradeList = ApprovingOfficerGradeList.getInstance();
			result.put("approvingOfficerGradeListID", approvingOfficerGradeList.getApprovingOfficerGradeListID());
			result.put("approvingOfficerGradeListValue", approvingOfficerGradeList.getApprovingOfficerGradeListValue());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			CommandProcessingException cpe = new CommandProcessingException ("Fail to update limit profile");
			cpe.initCause(e);
			throw cpe;
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
