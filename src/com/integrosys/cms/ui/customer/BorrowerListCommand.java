/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ProcessDetailsCustomerCommand.java,v 1.19 2006/11/08 07:57:02 jzhai Exp $
 */

package com.integrosys.cms.ui.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/11/08 07:57:02 $ Tag: $Name: $
 */
public class BorrowerListCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "borrowerList", "java.util.List", REQUEST_SCOPE },
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE }});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ICMSCustomer customerOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		List borrowerList = new ArrayList();
		// borrowerList.add(customerOB);

		ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
		List jointBorrowerList = null;
		try {
			if (limitProfileOB != null) {
				jointBorrowerList = customerProxy.getJointBorrowerList(limitProfileOB.getLimitProfileID());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if ((jointBorrowerList != null) && (jointBorrowerList.size() > 0)) {
			borrowerList.addAll(jointBorrowerList);
		}
		else {
			borrowerList.add(customerOB);
		}
		result.put("legalCustomerId", String.valueOf(customerOB.getCustomerID()));

		result.put("borrowerList", borrowerList);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}