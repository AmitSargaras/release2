/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshLmtFacDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facName", "java.lang.String", REQUEST_SCOPE },{ "customerID", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facDetailList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String facName = (String) (map.get("facName"));
			String custID = (String) (map.get("customerID"));
			
//			System.out.println("custID++"+custID);
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			if ((facName != null) && !facName.trim().equals("")) {
				try {
					result.put("facDetailList", proxy.getFacDetailList(facName, custID));
				} catch (LimitException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					throw (new CommandProcessingException(e.getMessage()));
				}
			}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
