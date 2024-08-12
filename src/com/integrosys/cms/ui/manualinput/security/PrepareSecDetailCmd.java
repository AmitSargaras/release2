/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareSecDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "secBookingCountry", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secSubtype", "java.lang.String", REQUEST_SCOPE },
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryList", "java.util.List", REQUEST_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE }, { "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ "pledgorRelnshipList", "java.util.List", REQUEST_SCOPE },
				{ "returnUrl", "java.lang.String", REQUEST_SCOPE },
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{"securityTypeSubTypeStr","java.util.String",REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			String event = (String) (map.get("event"));
			ICollateralTrxValue secTrxValue = (ICollateralTrxValue) (map.get("secTrxObj"));
			ICollateral curCol = this.getCollateral(secTrxValue, event);
			String secTypeCode = "";
			String secSubType = "";
			secTypeCode = (String) (map.get("secType"));
			secSubType = (String) (map.get("secSubtype"));
			String isCreate = (String) (map.get("isCreate"));
			if ((secTypeCode == null) && (curCol != null) && (curCol.getCollateralSubType() != null)) {
				secTypeCode = curCol.getCollateralSubType().getTypeCode();
			}
			String secCountry = null;
			secCountry = (String) (map.get("secBookingCountry"));
			if ((secCountry == null) && (curCol != null)) {
				secCountry = curCol.getCollateralLocation();
			}
			String returnUrl = getReturnUrl(secTrxValue, isCreate);
			
			//Added by Uma Khot: PHASE 3 CR Start:For Create Multiple Security – validation Popup message on 
			String securityTypeSubTypeStr="";
			
			if(null!=lmtTrxObj){
				 List<String> securityTypeSubTypeForParty=new ArrayList<String>();
				 securityTypeSubTypeForParty = CollateralDAOFactory.getDAO().getSecurityTypeSubTypeForParty(lmtTrxObj.getLimitProfileID());
				if(null!=securityTypeSubTypeForParty && securityTypeSubTypeForParty.size()!=0){
					Iterator<String> iterator = securityTypeSubTypeForParty.iterator();
					while(iterator.hasNext()){
						String next = iterator.next();
						if(!securityTypeSubTypeStr.contains(next))
						 securityTypeSubTypeStr = next+", "+securityTypeSubTypeStr;
						}
				 
				}
				
				ICollateralAllocation[] collateralAllocations = lmtTrxObj.getStagingLimit().getCollateralAllocations();
				if(null!=collateralAllocations){
					for(ICollateralAllocation alloc:collateralAllocations){
						String type=alloc.getCollateral().getCollateralType().getTypeCode();
						String subType=alloc.getCollateral().getCollateralSubType().getSubTypeCode();
						DefaultLogger.debug(this,"Security type:subType is :"+type+":"+subType);
						securityTypeSubTypeStr=type+":"+subType+", "+securityTypeSubTypeStr;
					}
					
				}
			}
			
			result.put("securityTypeSubTypeStr", securityTypeSubTypeStr);
			//Added by Uma Khot: PHASE 3 CR End:For Create Multiple Security – validation Popup message 
			
			result.put("fundedAmount", map.get("fundedAmount"));
			result.put("nonFundedAmount", map.get("nonFundedAmount"));
			result.put("memoExposer", map.get("memoExposer"));
			result.put("sanctionedLimit", map.get("sanctionedLimit"));
			result.put("facCat", map.get("facCat"));
			result.put("inrValue", map.get("inrValue"));
			result.put("countryList", getListAllCountry());
			result.put("orgList", getListAllSystemBankBranch(secCountry));
			result.put("secTypeList", getSecurityTypeList());
			result.put("secSubtypeList", getSecuritySubtypeList(secTypeCode));
			result.put("currencyList", getCurrencyList());
			result.put("pledgorRelnshipList", getPledgorRelnshipList());
			result.put("returnUrl", returnUrl);
			result.put("collateralList",getCollateralCodeList(secSubType));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private String getReturnUrl(ICollateralTrxValue secTrxValue, String isCreate) {
		if (ICMSConstant.STATE_DRAFT.equals(secTrxValue.getStatus())
				|| ICMSConstant.STATE_REJECTED.equals(secTrxValue.getStatus())) {
			String returnUrl = "ToDo.do";
			return returnUrl;
		}
		else {
			return "";
		}
	}

	

	private List getPledgorRelnshipList() {
		List lbValList = new ArrayList();
		Collection relnshipValues = CommonDataSingleton
				.getCodeCategoryValues(ICMSConstant.CATEGORY_CODE_PLEDGOR_RELNSHIP);
		Collection relnshipLabels = CommonDataSingleton
				.getCodeCategoryLabels(ICMSConstant.CATEGORY_CODE_PLEDGOR_RELNSHIP);

		Iterator labelIter = relnshipLabels.iterator();
		Iterator valueIter = relnshipValues.iterator();

		while (labelIter.hasNext()) {
			String id = (String) labelIter.next();
			String val = (String) valueIter.next();
			LabelValueBean lvBean = new LabelValueBean(id, val);
			lbValList.add(lvBean);

		}
		return CommonUtil.sortDropdown(lbValList);
	}


	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance().getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance().getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private ICollateral getCollateral(ICollateralTrxValue trxValue, String event) {
		if (trxValue == null) {
			return null;
		}
		if (EventConstant.EVENT_READ.equals(event)) {
			return trxValue.getCollateral();
		}
		else {
			return trxValue.getStagingCollateral();
		}
	}
	

	private List getListAllCountry() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICountry[] countryLst = CollateralDAOFactory.getDAO().getListAllCountry();
				
				if (countryLst != null) {
					for (int i = 0; i < countryLst.length; i++) {
						ICountry lst = countryLst[i];
						String id = lst.getCountryCode();
						String value = lst.getCountryName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getListAllSystemBankBranch(String country) {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(country);
				
				if (branch != null) {
					for (int i = 0; i < branch.length; i++) {
						ISystemBankBranch lst = branch[i];
						String id = lst.getSystemBankBranchCode();
						String value = lst.getSystemBankBranchName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getBuyCurrency().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
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
