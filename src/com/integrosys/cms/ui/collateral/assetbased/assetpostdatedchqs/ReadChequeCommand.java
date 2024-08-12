/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/ReadChequeCommand.java,v 1.3 2003/09/09 04:15:14 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/09 04:15:14 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class ReadChequeCommand extends AbstractCommand {
	

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
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "form.chequeObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "otherBankList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "actualCheque", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque",
						REQUEST_SCOPE },
				{ "stageCheque", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque",
						REQUEST_SCOPE }, });
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
		 String idStrVal = (String) map.get("collateralID");
		long index = Long.parseLong((String) map.get("indexID"));

		String from_event = (String) map.get("from_event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IAssetPostDatedCheque iAsset;
		String event = (String) map.get("event");
		if ((from_event != null) && from_event.equals("read")) {
			iAsset = (IAssetPostDatedCheque) itrxValue.getCollateral();
		}
		else {
			iAsset = (IAssetPostDatedCheque) itrxValue.getStagingCollateral();
			if ((from_event != null) && from_event.equals("process")) {
				IPostDatedCheque actualCheque = null;
				if (itrxValue.getCollateral() != null) 
					actualCheque = getItem(((IAssetPostDatedCheque) itrxValue.getCollateral())
						.getPostDatedCheques(), index);
				IPostDatedCheque stageCheque = getItem(iAsset.getPostDatedCheques(), index);
				result.put("actualCheque", actualCheque);
				result.put("stageCheque", stageCheque);
			}
		}

		IPostDatedCheque postCheque;
		if (from_event != null) {
			postCheque = getItem(iAsset.getPostDatedCheques(), index);
			if ((postCheque == null) && from_event.equals("process")) {
				postCheque = getItem(((IAssetPostDatedCheque) itrxValue.getCollateral()).getPostDatedCheques(), index);
			}
		}
		else {
			postCheque = iAsset.getPostDatedCheques()[(int) index];
		}
		result.put("form.chequeObject", postCheque);
		result.put("otherBankList", getotherBankList());
		result.put("indexID", map.get("indexID"));
		result.put("subtype", map.get("subtype"));
		result.put("from_event", from_event);
		result.put("event", event);
		result.put("userName", userName);
		result.put("collateralID", idStrVal);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	
	private IPostDatedCheque getItem(IPostDatedCheque temp[], long itemRef) {
		IPostDatedCheque item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
	
/*	private List getotherBankList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getOtherBankProxyManager().getOtherBankList(null,null);
			 List idList = (List) searchResult.getResultList();
			 System.out.println("branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) idList.get(i);
			//	String id = Long.toString(otherBank.getId());
				String id = otherBank.getOtherBankCode();
				String val = otherBank.getOtherBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}*/
	
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

}
