package com.integrosys.cms.ui.caseCreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 $Author: Abhijit R $
 
 */
public class ListOptionsCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	
	
	public ListOptionsCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				{"event", "java.lang.String", REQUEST_SCOPE},
				
			};
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				
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
		List systemBankList= null;
		ISystemBank systemBank= null;
		String event = (String) map.get("event");
		
		
		resultMap.put("branchList", getBranchList());
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			HashMap sysBranch=new HashMap();
			ISystemBankBranchProxyManager systemBankBranchProxy =(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
			 SearchResult searchResult = systemBankBranchProxy.getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = bankBranch.getSystemBankBranchCode();
				String val = bankBranch.getSystemBankBranchName();
				String value=id+" ("+val + ")";
				sysBranch.put(id, value);
			}
			ICaseBranchProxyManager caseBranchProxy =(ICaseBranchProxyManager) BeanHouse.get("caseBranchProxy");
			 SearchResult caseBranchSearchResult= (SearchResult)  caseBranchProxy.getAllActualCaseBranch();
			 List caseBranchList = (List) caseBranchSearchResult.getResultList();
			for (int i = 0; i < caseBranchList.size(); i++) {
				OBCaseBranch caseBranch=(OBCaseBranch)caseBranchList.get(i);
				String id = caseBranch.getBranchCode();
				String value=(String) sysBranch.get(caseBranch.getBranchCode());
				LabelValueBean lvBean = new LabelValueBean(value, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	
}