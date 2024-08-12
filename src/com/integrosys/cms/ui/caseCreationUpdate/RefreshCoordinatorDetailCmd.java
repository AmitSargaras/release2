/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.caseCreationUpdate;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationDao;
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
public class RefreshCoordinatorDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "branchCode", "java.lang.String", REQUEST_SCOPE },{ "customerID", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "coordinator1Name", "java.lang.String", REQUEST_SCOPE },
				{ "coordinator2Name", "java.lang.String", REQUEST_SCOPE }
		
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String branchCode = (String) (map.get("branchCode"));
			
//			System.out.println("custID++"+custID);
			ICaseCreationDao caseCreationDao= (ICaseCreationDao) BeanHouse.get("caseCreationUpdateDao");
			
			if ((branchCode != null) && !branchCode.trim().equals("")) {
				List resultList=caseCreationDao.getCaseCreationByBranchCode(branchCode);
				if(resultList!=null && resultList.size()>0){
					OBCaseBranch obCaseBranch= (OBCaseBranch)resultList.get(0);
					result.put("coordinator1Name",obCaseBranch.getCoordinator1());
					result.put("coordinator2Name",obCaseBranch.getCoordinator2());
				}
			}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
