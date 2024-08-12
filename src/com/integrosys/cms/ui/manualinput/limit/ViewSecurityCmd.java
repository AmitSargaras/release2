/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ViewSecurityCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "securityId", "java.lang.String", REQUEST_SCOPE },
				{ "securitySubtype", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {{ "lmtId", "java.lang.String", REQUEST_SCOPE },
				{ "securityId", "java.lang.String", REQUEST_SCOPE },
				{ "securitySubtype", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "orgList", "java.util.List", SERVICE_SCOPE },
				{ "currencyList", "java.util.List", SERVICE_SCOPE },
				{ "riskTypeList", "java.util.List", SERVICE_SCOPE },
				{ "collCodeDescMap", "java.util.List", SERVICE_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String lmtID = (String) (map.get("limitId")); //Shiv 300911
		try {
			// just map from form to staging limit and save in trxValue object
			ILimit lmt = (ILimit) (map.get("lmtDetailForm"));
			String event = (String) (map.get("event"));
			if(map.get("lmtId") != null){
				lmtID = (String) (map.get("lmtId")); //Shiv 231111
			}
			
			result.put("lmtId", lmtID); //Shiv 300911		
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		
		result.put("collCodeDescMap", getCollateralCodeList());
		result.put("countryList", getListAllCountry());
		result.put("currencyList", getCurrencyList());
		try {
			result.put("riskTypeList", getRiskTypeList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("orgList", getListAllSystemBankBranch());
		result.put("fundedAmount", map.get("fundedAmount"));
		result.put("nonFundedAmount", map.get("nonFundedAmount"));
		result.put("memoExposer", map.get("memoExposer"));
		result.put("sanctionedLimit", map.get("sanctionedLimit"));
		result.put("inrValue", map.get("inrValue"));
		result.put("lmtId", lmtID); //Shiv 300911
		result.put("securityId", map.get("securityId")); //Shiv 051111
		result.put("securitySubtype", map.get("securitySubtype")); //Shiv 051111
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
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
	
	private List getListAllSystemBankBranch() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List branch = CollateralDAOFactory.getDAO().getSysBankBranchNameList();
				
					for (int i = 0; i < branch.size(); i++) {
						String [] str = (String[]) branch.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCollateralCodeList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List colCodeLst = CollateralDAOFactory.getDAO().getCollateralCodeDesc();
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String desc = codeLst[1];
						LabelValueBean lvBean = new LabelValueBean(code, desc);
						lbValList.add(lvBean);
					}
				
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getRiskTypeList() throws Exception {
		
		List<LabelValueBean> lbValList = new ArrayList<LabelValueBean>();
		IRiskTypeProxyManager riskTypeProxy = (IRiskTypeProxyManager)BeanHouse.get("riskTypeProxy");
		SearchResult riskTypeList= (SearchResult) riskTypeProxy.getAllActualRiskType();
		Iterator itr = riskTypeList.getResultList().iterator();
		
		while(itr.hasNext()) {
			IRiskType riskType = (IRiskType) itr.next();
			LabelValueBean lvBean = new LabelValueBean(riskType.getRiskTypeName(),riskType.getRiskTypeCode());
			lbValList.add(lvBean);
		}
		
		return CommonUtil.sortDropdown(lbValList);
	}
}
