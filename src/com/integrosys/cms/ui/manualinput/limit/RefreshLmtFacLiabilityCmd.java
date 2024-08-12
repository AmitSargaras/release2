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
public class RefreshLmtFacLiabilityCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "subProfileId", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "liabilityIDList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String subProfileId = (String) (map.get("subProfileId"));
			
//			System.out.println("custID++"+custID);
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			if ((subProfileId != null) && !subProfileId.trim().equals("")) {
				try {
					result.put("liabilityIDList", getLiabilityList(proxy.getLiabilityIDList(subProfileId)));
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
	
	private List getLiabilityList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[0]+" - "+mgnrLst[1],mgnrLst[0]+" - "+mgnrLst[1] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}

}
