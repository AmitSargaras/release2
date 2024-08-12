package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

public class RefreshBranchCommand extends AbstractCommand{
	
	private IOtherBankProxyManager otherBankProxyManager;
	private ISystemBankBranchProxyManager systemBankBranchProxy;
	
	
	



	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{"bankId", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
				 {"hdfcBank", "java.lang.String", REQUEST_SCOPE},				 
				 { "otherBankList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
		        { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
			 {"event", "java.lang.String", REQUEST_SCOPE},
				 { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				 { "otherBankList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				 {"BranchListFormBankId", "java.util.List",REQUEST_SCOPE},
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        SearchResult idListval=new SearchResult();
        List list=new ArrayList();
    
       String hdfcBank= (String) map.get("hdfcBank");
		
		
		 try {
		String event = (String) map.get("event");
    	if( event.equals("refresh_branch_id")){	        		
    		String bankId = (String) map.get("bankId");
    		//	List idListval = (ArrayList) getSystemBankProxy().getAllActual();
    		if(hdfcBank.trim().equals("O")){
    			IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
        		IOtherBank obj=otherBank.getOtherBankByCode(bankId);
        	
    			SearchResult OtherBranchList = getOtherBankProxyManager().getOtherBranchList(null,null,null,null,obj.getId());
        		 list = (List)OtherBranchList.getResultList();
        				
    		
    		}else if(hdfcBank.trim().equals("S")){
    			 idListval = (SearchResult) getSystemBankBranchProxy().getAllActualBranch();
    				
    		}
    		
    	resultMap.put("BranchListFormBankId", getBranchList(list,idListval));       		
	
    	} 
    	ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
    	result.put("otherBankList", (List)map.get("otherBankList"));
    	result.put("serviceColObj", itrxValue);
        resultMap.put("event", event);
		
		 } catch (NoSuchGeographyException nsge) {
	        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
				cpe.initCause(nsge);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
		
	private List getBranchList(List idList,SearchResult idListval) {
		List lbValList = new ArrayList();
		String id;
		String val;
		LabelValueBean lvBean;
		try {		
			 List idList2 = (List) idListval.getResultList();
			 if(idList2!=null){
			 if(idList2.size()>0){
			 
			 for (int i = 0; i < idList2.size(); i++) {
				 OBSystemBankBranch hdfc = (OBSystemBankBranch) idList2.get(i);
				
						 id = hdfc.getSystemBankBranchCode();
						 val = hdfc.getSystemBankBranchName();
						 lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			  }
			 }
			 
			 if(idList!=null){
			 if(idList.size()>0){
			for (int i = 0; i < idList.size(); i++) {
				IOtherBranch branch = (IOtherBranch)idList.get(i);
					 id = branch.getOtherBranchCode();
					 val = branch.getOtherBranchName();
					 lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
			}
			 }
			 }
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
