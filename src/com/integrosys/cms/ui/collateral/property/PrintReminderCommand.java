/*
 * Created on Feb 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintReminderCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "registedHolder", "java.lang.String", REQUEST_SCOPE },
				{ "property", "java.lang.String", REQUEST_SCOPE },
				{ "customerNameList", "java.util.List", REQUEST_SCOPE },
				{ "famCodeList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			IPropertyCollateral propertyCol = (IPropertyCollateral) (itrxValue.getCollateral());
			result.put("registedHolder", propertyCol.getRegistedHolder());
			result.put("property", propertyCol.getPropertyAddress());
			List securityBCAList = SecuritySubTypeUtil.getSecurityBCAList(propertyCol);
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			HashMap customerList = (HashMap) proxy.getFamcodeCustNameByCustomer(securityBCAList);
			Collection customerCol = customerList.values();
			Iterator iter = customerCol.iterator();
			ArrayList customerNameList = new ArrayList();
			ArrayList famCodeList = new ArrayList();
			while (iter.hasNext()) {
				String[] curDtl = (String[]) (iter.next());
				customerNameList.add(curDtl[0]);
				famCodeList.add(curDtl[1]);
			}
			result.put("customerNameList", customerNameList);
			result.put("famCodeList", famCodeList);
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
