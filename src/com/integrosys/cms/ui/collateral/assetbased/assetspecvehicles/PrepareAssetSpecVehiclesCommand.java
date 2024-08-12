//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.collateral.assetbased.PrepareAssetBasedCommand;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 3:05:13 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetSpecVehiclesCommand extends PrepareAssetBasedCommand {

	
	private ISystemBankBranchProxyManager systemBankBranchProxy;
	 
	
	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	
	
	public String[][] getParameterDescriptor() {
		String[][] thisDesc = PrepareAssetSpecVehiclesCommandHelper.getParameterDescriptor();
		
		String[][] fromSuper = super.getParameterDescriptor();
		return super.mergeResultDescriptor(thisDesc, fromSuper);
	}
	
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		String[][] thisDesc = PrepareAssetSpecVehiclesCommandHelper.getResultDescriptor();

		String[][] fromSuper = super.getResultDescriptor();
		return super.mergeResultDescriptor(thisDesc, fromSuper);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		result.put("branchList", getBranchList());
		PrepareAssetSpecVehiclesCommandHelper.fillPrepare(map, result, exceptionMap);

		HashMap fromSuper = super.doExecute(map);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, super.mergeResultMap(result, fromSuper));
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, super.mergeExceptionMap(exceptionMap, fromSuper));
		return temp;
	}
	
	
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = Long.toString(bankBranch.getId());
				String val = bankBranch.getSystemBankBranchName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}

}
