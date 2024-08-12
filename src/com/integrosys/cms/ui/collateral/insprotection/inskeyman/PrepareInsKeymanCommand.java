//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.insprotection.inskeyman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.collateral.insprotection.PrepareInsProtectionCommand;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 3:05:13 PM
 * To change this template use Options | File Templates.
 */
public class PrepareInsKeymanCommand extends PrepareInsProtectionCommand {

	private IOtherBankProxyManager otherBankProxyManager;
	
	
	

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		
		String[][] thisDesc = PrepareInsKeymanCommandHelper.getResultDescriptor();

		/*
		 * new String[][]{ //TODO Please Fill this UP {"_change_key",
		 * "java.util.Collection", REQUEST_SCOPE} });
		 */
		String[][] fromSuper = super.getResultDescriptor();
		return super.mergeResultDescriptor(thisDesc, fromSuper);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		result.put("insurerList", getInsurerList());
		PrepareInsKeymanCommandHelper.fillPrepare(map, result, exceptionMap);

		HashMap fromSuper = super.doExecute(map);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, super.mergeResultMap(result, fromSuper));
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, super.mergeExceptionMap(exceptionMap, fromSuper));
		

		
		return temp;
	}
	
	private List getInsurerList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getOtherBankProxyManager().getInsurerList();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				IInsuranceCoverage otherBank = (IInsuranceCoverage) idList.get(i);
			//	String id = Long.toString(otherBank.getId());
				String id = otherBank.getInsuranceCoverageCode();
				String val = otherBank.getCompanyName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}

}
