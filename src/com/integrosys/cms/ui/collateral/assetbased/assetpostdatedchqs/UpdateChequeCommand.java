/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/UpdateChequeCommand.java,v 1.5 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

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
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/26 10:12:37 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class UpdateChequeCommand extends AbstractCommand {
	
	

	private IOtherBankProxyManager otherBankProxyManager;
	
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.chequeObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "collateralLoc", "java.lang.String", REQUEST_SCOPE },
				{ "hdfcBank", "java.lang.String", REQUEST_SCOPE }, 
				{ "bankBranchType", "java.lang.String", REQUEST_SCOPE }, 
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "otherBankList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "collateralLoc", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				 { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				 { "collateralList", "java.util.List", REQUEST_SCOPE },
				 { "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
			
				
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
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

		IPostDatedCheque cheque = (IPostDatedCheque) map.get("form.chequeObject");
		int index = Integer.parseInt((String) map.get("indexID"));
		DefaultLogger.debug(this, "SubType:" + map.get("subtype"));
		DefaultLogger.debug(this, "Index is:" + index);
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		CountryList list = CountryList.getInstance();
    	ArrayList countryLabels = new ArrayList(list.getCountryLabels());
		ArrayList countryValues = new ArrayList(list.getCountryValues());
		
		String issuerStatus=(String) map.get("bankBranchType");
		String draweeBankStatus=(String) map.get("hdfcBank");
		
		String collateralLoc = null;
		if (map.get("collateralLoc") != null) {
			collateralLoc = (String) map.get("collateralLoc");
		}
		else {
			if (itrxValue!=null && itrxValue.getStagingCollateral()!=null) {
				collateralLoc = (itrxValue.getStagingCollateral()).getCollateralLocation();
			}
		}


		IAssetPostDatedCheque iAsset = (IAssetPostDatedCheque) itrxValue.getStagingCollateral();

		IPostDatedCheque chequeArray[] = iAsset.getPostDatedCheques();
		cheque.setDraweeBank(draweeBankStatus);
		cheque.setIssuerName(issuerStatus);
		chequeArray[index] = cheque;

		iAsset.setPostDatedCheques(chequeArray);

		List collateralList = new ArrayList();
		collateralList = getCollateralCodeList(itrxValue.getCollateral().getCollateralSubType().getSubTypeCode());
		result.put("collateralList", collateralList);
		
		
		
		itrxValue.setStagingCollateral(iAsset);
		result.put("serviceColObj", itrxValue);
		result.put("collateralLoc", collateralLoc);
		result.put("countryLabels", countryLabels);
		result.put("countryValues", countryValues);
		result.put("otherBankList", getotherBankList());
		result.put("subtype", map.get("subtype"));

		DefaultLogger.debug(this, "After Addition1:" + itrxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IPostDatedCheque[] updateItem(IPostDatedCheque temp[], long itemRef, IPostDatedCheque cheque) {

		for (int i = 0; i < temp.length; i++) {
			DefaultLogger.debug(this, "itemRef: " + itemRef + "\tid: " + temp[i].getRefID() + "\tcheque id:"
					+ cheque.getRefID());
			if (temp[i].getRefID() == itemRef) {
				temp[i] = cheque;
				break;
			}
			else {
				continue;
			}
		}
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
				//String id = Long.toString(otherBank.getId());
				String id = otherBank.getOtherBankCode();
				String val = otherBank.getOtherBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	private List getCollateralCodeList(String subTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (subTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List colCodeLst = helper.getSBMISecProxy().getCollateralCodeBySubTypes(subTypeValue);
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String name = codeLst[1];
						LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(name), UIUtil
								.replaceSpecialCharForXml(code));
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
}
