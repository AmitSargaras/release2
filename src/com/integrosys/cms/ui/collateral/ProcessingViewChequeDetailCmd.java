package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

public class ProcessingViewChequeDetailCmd extends AbstractCommand {
	
	private IOtherBankProxyManager otherBankProxyManager;
	
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	
	
	

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE},
				 });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] {{ "otherBankList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				               
		                        });

	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String event = (String) map.get("event");
		


		result.put("otherBankList", getotherBankList());
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}
	
	private List getotherBankList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getOtherBankProxyManager().getOtherBankList(null,null);
			 List idList = (List) searchResult.getResultList();
//			 System.out.println("branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) idList.get(i);
				String id = Long.toString(otherBank.getId());
				String val = otherBank.getOtherBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	
	
	
	
}
