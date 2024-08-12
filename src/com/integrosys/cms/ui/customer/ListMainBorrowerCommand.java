package com.integrosys.cms.ui.customer;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jul 31, 2006 Time: 10:56:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListMainBorrowerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListMainBorrowerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
				"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "mainBorrowerList", "java.util.ArrayList", REQUEST_SCOPE },

		});
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
		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ArrayList mainBorrowerList = new ArrayList();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			mainBorrowerList = custproxy.getMBlistByCBleId(custOB.getCustomerID());
			result.put("mainBorrowerList", mainBorrowerList);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute of ListMainBorrowerCommand" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}
}
