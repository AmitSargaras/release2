package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedGroup;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageDAO;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

public class ListInsuranceCurrencyCmd extends ExchangeRateCommand {
	
	public ListInsuranceCurrencyCmd(){
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				{"event", "java.lang.String", REQUEST_SCOPE},
				
			};
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
				{ "currencyList", "java.util.List", SERVICE_SCOPE },
				
			});
	}
	
public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List systemBankList= null;
		ISystemBank systemBank= null;
		String event = (String) map.get("event");
		
		
		resultMap.put("currencyList", getCurrencyList());
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

private List getCurrencyList() {
	List lbValList = new ArrayList();
	try {
			MISecurityUIHelper helper = new MISecurityUIHelper();
			IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
			
			if (currency != null) {
				for (int i = 0; i < currency.length; i++) {
					IForexFeedEntry lst = currency[i];
//					String id = lst.getCurrencyIsoCode().trim();
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

}
