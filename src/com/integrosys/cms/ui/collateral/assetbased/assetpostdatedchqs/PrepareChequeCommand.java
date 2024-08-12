/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/PrepareChequeCommand.java,v 1.7 2004/06/04 05:19:56 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.ExchangeControlList;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/04 05:19:56 $ Tag: $Name: $
 */

public class PrepareChequeCommand extends AbstractCommand {
	

	private IOtherBankProxyManager otherBankProxyManager;
	
	private ISystemBankProxyManager systemBankProxy;
	
	
	
	public ISystemBankProxyManager getSystemBankProxy() {
		return systemBankProxy;
	}

	public void setSystemBankProxy(ISystemBankProxyManager systemBankProxy) {
		this.systemBankProxy = systemBankProxy;
	}

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "hdfcBank", "java.lang.String", REQUEST_SCOPE },
				{ "branchCodeVal", "java.lang.String", REQUEST_SCOPE },
				{ "bankCode", "java.lang.String", REQUEST_SCOPE },
				{ "branchCode", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "draweeBankStatus", "java.lang.String", REQUEST_SCOPE },
				{ "issuerStatus", "java.lang.String", REQUEST_SCOPE },});

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chequeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chequeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "issuerID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "issuerLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "secCustodianList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secCustodianID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationSCBID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "otherBankList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				 {"BranchListFormBankId", "java.util.List",REQUEST_SCOPE},
				 { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "locationSCBValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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
		String userName=(String)map.get("userName");
		/*commented for HDFC Bank*/
//		Collection list2 = CurrencyList.getInstance().getCountryValues();
//		result.put("currencyCode", list2);
		 String idStrVal = (String) map.get("collateralID");
         String bankCode= (String)map.get("bankCode");
         String branchCodevalue= (String)map.get("branchCode");
         String hdfcBank=(String)map.get("hdfcBank");
         String branchCode=(String)map.get("branchCodeVal");
		ChequeTypeList cheque = ChequeTypeList.getInstance();
		result.put("chequeID", cheque.getChequeTypeID());
		result.put("chequeValue", cheque.getChequeTypeValue());
		String index =(String) map.get("indexID");
		String issuerStatus=(String) map.get("issuerStatus");
		String draweeBankStatus=(String) map.get("draweeBankStatus");
		CountryList list = CountryList.getInstance();
		result.put("countryLabels", list.getCountryLabels());
		result.put("countryValues", list.getCountryValues());

		ExchangeControlList list1 = ExchangeControlList.getInstance();
		result.put("ExchangeControlID", list1.getExchangeControlID());
		result.put("ExchangeControlValue", list1.getExchangecontrolValue());
		/*
		 * BookingLocationList bookingLoc = BookingLocationList.getInstance();
		 * 
		 * Collection secCustInternal = bookingLoc.getBookingLocationValue();
		 * result.put("secCustodianList", secCustInternal);
		 * result.put("secCustodianID", bookingLoc.getBookingLocationID());
		 */

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral col = (colTrxValue.getStagingCollateral() == null) ? colTrxValue.getCollateral() : colTrxValue
				.getStagingCollateral();

		CommonCodeList commonCode = CommonCodeList.getInstance(col.getCollateralLocation(),
				ICMSConstant.CATEGORY_CODE_BKGLOC, true);
		result.put("secCustodianID", commonCode.getCommonCodeValues());
		result.put("secCustodianList", commonCode.getCommonCodeLabels());

		result.put("locationSCBID", commonCode.getCommonCodeValues());
		result.put("locationSCBValue", commonCode.getCommonCodeLabels());
		
	
		
		 try {
				String event = (String) map.get("event");
				
			//	String issuerStatus=(String) map.get("issuerStatus");
			//	String draweeBankStatus=(String) map.get("draweeBankStatus");
				
				
				IAssetPostDatedCheque iCol = (IAssetPostDatedCheque) colTrxValue.getStagingCollateral();
				IPostDatedCheque[] cheque1 = iCol.getPostDatedCheques();
				
				for(int i=0;i<cheque1.length;i++){
					IPostDatedCheque chq = (IPostDatedCheque)cheque1[Integer.parseInt(String.valueOf(i))];
					if(branchCodevalue!=null && branchCodevalue.equals(chq.getBranchCode()))
					{
						if(chq.getDraweeBank().equals("O")){
	                		IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
	                		IOtherBank obj=otherBank.getOtherBankByCode(cheque1[Integer.parseInt(String.valueOf(i))].getBankCode());
	                		
	                		IOtherBranchDAO otherBankVal = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
	                		SearchResult OtherBranchList1=	otherBankVal. getOtherBranchList("2",cheque1[Integer.parseInt(String.valueOf(i))].getBranchCode());
	                		List listVAL = (List)OtherBranchList1.getResultList();
	                		result.put("BranchListFormBankId", getBranchList(listVAL,chq.getDraweeBank()));	 
	                	}else {
	                		ISystemBankBranchDao otherBranchBank = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
	                		SearchResult OtherBranchList2=otherBranchBank.getSystemBankBranchList(cheque1[Integer.parseInt(String.valueOf(i))].getBranchCode());
	                		List listVAL1 = (List)OtherBranchList2.getResultList();
	                		result.put("BranchListFormBankId", getBranchList(listVAL1,chq.getDraweeBank()));	 
	                	}
					}
				}
				if(cheque1.length>0){
				if(!index.equals("-1")){
			   	if(cheque1[Integer.parseInt(index)]!=null){
            	if(draweeBankStatus!=null){
			   		
            	if(draweeBankStatus.equals("O")){
            		IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
            		IOtherBank obj=otherBank.getOtherBankByCode(cheque1[Integer.parseInt(index)].getBankCode());
            		
            		IOtherBranchDAO otherBankVal = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
            		SearchResult OtherBranchList1=	otherBankVal. getOtherBranchList("2",cheque1[Integer.parseInt(index)].getBranchCode());
            		List listVAL = (List)OtherBranchList1.getResultList();
            		result.put("BranchListFormBankId", getBranchList(listVAL,draweeBankStatus));	 
            	}else {
            		ISystemBankBranchDao otherBranchBank = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
            		SearchResult OtherBranchList2=otherBranchBank.getSystemBankBranchList(cheque1[Integer.parseInt(index)].getBranchCode());
            		List listVAL1 = (List)OtherBranchList2.getResultList();
            		result.put("BranchListFormBankId", getBranchList(listVAL1,draweeBankStatus));	 
            	}
            	}        
            	}
				}else{
					if(hdfcBank!=null){
						if(hdfcBank.equals("S")&& !branchCode.equals("")){
							ISystemBankBranchDao otherBranchBank = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
	            		SearchResult OtherBranchList2=otherBranchBank.getSystemBankBranchList(branchCode);
	            		List listVAL1 = (List)OtherBranchList2.getResultList();
	            		result.put("BranchListFormBankId", getBranchList(listVAL1,hdfcBank));	 
	            
						}else if(hdfcBank.equals("O")&& !branchCode.equals("")){
							IOtherBranchDAO otherBankVal = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
		            		SearchResult OtherBranchList1=	otherBankVal.getOtherBranchFromCode(branchCode);
		            		List listVAL = (List)OtherBranchList1.getResultList();
		            		result.put("BranchListFormBankId", getBranchList(listVAL,hdfcBank));	 
		            
						}
				      }
					}
				
				}else{
					if(hdfcBank!=null){
					if(hdfcBank.equals("S")&& !branchCode.equals("")){
						ISystemBankBranchDao otherBranchBank = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
            		SearchResult OtherBranchList2=otherBranchBank.getSystemBankBranchList(branchCode);
            		List listVAL1 = (List)OtherBranchList2.getResultList();
            		result.put("BranchListFormBankId", getBranchList(listVAL1,hdfcBank));	 
            
					}else if(hdfcBank.equals("O")&& !branchCode.equals("")){
						IOtherBranchDAO otherBankVal = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
	            		SearchResult OtherBranchList1=	otherBankVal. getOtherBranchFromCode(branchCode);
	            		List listVAL = (List)OtherBranchList1.getResultList();
	            		result.put("BranchListFormBankId", getBranchList(listVAL,hdfcBank));	 
	            
					}
			      }
				}
		    	//	String bankId = (String) map.get("bankId");
		    	//	IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
		    	//	IOtherBank obj=otherBank.getOtherBankByCode(bankCode);
		    	//	SearchResult OtherBranchList = getOtherBankProxyManager().getOtherBranchList(null,null,null,null,obj.getId());
		    	//	List list2 = (List)OtherBranchList.getResultList();
		    		
		    	//	result.put("BranchListFormBankId", getBranchList(list2));	        		
			
		    	
		     	  result.put("otherBankList", (List)map.get("otherBankList"));
		    	  result.put("event", event);
				
				 } catch (NoSuchGeographyException nsge) {
			        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
						cpe.initCause(nsge);
						throw cpe;
					} catch (Exception e) {
						CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
						cpe.initCause(e);
						throw cpe;
					}
		
		
		

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.REIMBURSEMENT_BANK);
		result.put("issuerID", commonCode.getCommonCodeValues());
		result.put("issuerLabels", commonCode.getCommonCodeLabels());
		result.put("otherBankList", getotherBankList());
		result.put("serviceColObj", map.get("serviceColObj"));
		result.put("userName", userName);
		result.put("collateralID", idStrVal);
		result.put("indexID", String.valueOf(index));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	 
	
	private List getotherBankList() {
		List lbValList = new ArrayList();
		String id;
		String val;
		LabelValueBean lvBean;
		try {
			List idListval = (ArrayList) getSystemBankProxy().getAllActual();
			OBBankingMethod sysBanking = null;
			 OBSystemBank hdfc = (OBSystemBank) idListval.get(0);
				 id = hdfc.getSystemBankCode();
				 val = hdfc.getSystemBankName();
				 lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			
			 SearchResult searchResult = getOtherBankProxyManager().getOtherBankList(null,null);
			 List idList = (List) searchResult.getResultList();
//			 System.out.println("branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) idList.get(i);
				//String id = Long.toString(otherBank.getId());
				 id = otherBank.getOtherBankCode();
				 val = otherBank.getOtherBankName();
				 lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	private List getBranchList(List idList,String draweeBankStatus) {
		List lbValList = new ArrayList();
		try {		
			if(draweeBankStatus.equals("O")){
			for (int i = 0; i < idList.size(); i++) {
				IOtherBranch branch = (IOtherBranch)idList.get(i);
					String id = branch.getOtherBranchCode();
					String val = branch.getOtherBranchName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
			}
		}else if(draweeBankStatus.equals("S")){
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch branch = (ISystemBankBranch)idList.get(i);
					String id = branch.getSystemBankBranchCode();
					String val = branch.getSystemBankBranchName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
			}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
}