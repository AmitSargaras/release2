package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;


public class BankCodePopulationCmd extends AbstractCommand {
	


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
				 {"bankId", "java.lang.String", REQUEST_SCOPE},});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				                { "bankCode", "java.lang.String",REQUEST_SCOPE },
		                        });

	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String event = (String) map.get("event");
		


	
			long bankId = Long.parseLong((String) map.get("bankId"));
			result.put("bankCode", getBankCode(bankId));
   
			
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}
	
	
	
	
	private String getBankCode(long bankCode) {
		String otherBankCodeval="";
		try {
			
			 IOtherBank otherBank = getOtherBankProxyManager().getOtherBankById(bankCode);
			
			 otherBankCodeval=otherBank.getOtherBankCode();
			 
			 
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return otherBankCodeval;
	}
	
	
	
}
