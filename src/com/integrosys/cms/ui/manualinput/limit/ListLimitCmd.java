/*
 * Created on Mar 8, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ListLimitCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "source", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "prevSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitList", "java.util.List", REQUEST_SCOPE },
				{ "isManualInput", "java.lang.String", REQUEST_SCOPE }, 
				{ "prevSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE }, 
				{ "listOfLimitId", "java.util.List", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			DefaultLogger.debug(this, "**********Start ListLimitCmd : 60 ");
			String limitProfileId = (String) (map.get("limitProfileID"));
			LimitDAO lmdao=new LimitDAO();
			try {
				System.out.println("Inside ListLimitCmd.java=>Going for getPartyIdUsingLimitProfileId(limitProfileId)=> limitProfileId=>"+limitProfileId);
				String partyid=lmdao.getPartyIdUsingLimitProfileId(limitProfileId);
				System.out.println("ListLimitCmd.java=>After  getPartyIdUsingLimitProfileId(limitProfileId)=> partyid=>"+partyid);
				lmdao.executeSpCollateralMove(partyid);
			}
			catch(Exception ex)
			{
				System.out.println("Exception ListLimitCmd.java=>ex=>"+ex);
				ex.printStackTrace();
//				throw (new CommandProcessingException(ex.getMessage()));	
			}
			System.out.println("ListLimitCmd.java line 75..");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			DefaultLogger.debug(this, "**********Before getLimitSummaryListByAA() : 65 limitProfileId "+limitProfileId);
			List lmtList = proxy.getLimitSummaryListByAA(limitProfileId);
			DefaultLogger.debug(this, "**********After getLimitSummaryListByAA() : 67 limitProfileId "+lmtList.size());
			String source = (String) (map.get("source"));
			
			//added by santosh For UBS CR
			String event = (String) (map.get("event"));
			if("rejectedFlow_list_limit".equals(event)) {
				
				CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
				String sessionCriteria1 = null;
				boolean flag=false;
				
				if(isNotEmptyStr(formCriteria.getFacilitySystem()) && isNotEmptyStr(formCriteria.getFacilitySystemID())){
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
					String cust = customerDAO.searchPartyIDBySysID(formCriteria.getFacilitySystem(), formCriteria.getFacilitySystemID());
					formCriteria.setLegalID(cust);
					flag=true;
				}
				
				
				if (isEmptySearchCriteria(formCriteria)) {
					sessionCriteria1 = (String) map.get("sessionCriteria");
					if(!"".equals(sessionCriteria1) && null!=sessionCriteria1 && !"null".equals(sessionCriteria1))
						lmtList = proxy.getRejectedLimitSummaryListByAA(sessionCriteria1);
					else {
						if(flag) {
							sessionCriteria1=formCriteria.getLegalID()+","+"legalID";
							lmtList = proxy.getRejectedLimitSummaryListByAA(sessionCriteria1);
						}
						else
							lmtList = proxy.getRejectedLimitSummaryListByAA();
					}
				}
				else {
					if(null!=formCriteria.getCustomerName()&&!"".equals(formCriteria.getCustomerName())) {
						sessionCriteria1=formCriteria.getCustomerName()+","+"PartyName";	
						lmtList = proxy.getRejectedLimitSummaryListByAA(sessionCriteria1);
					}else if(null!=formCriteria.getLegalID()&&!"".equals(formCriteria.getLegalID())){
						sessionCriteria1=formCriteria.getLegalID()+","+"legalID";	
						lmtList = proxy.getRejectedLimitSummaryListByAA(sessionCriteria1);
					}else {
						lmtList = proxy.getRejectedLimitSummaryListByAA();
					}
				}
				
				result.put("sessionCriteria", sessionCriteria1);
			}
			//end santosh
			DefaultLogger.debug(this, "**********Before formatLimitListView() : 114 ");
			List lmtListFormated = helper.formatLimitListView(lmtList, locale);
			DefaultLogger.debug(this, "**********After formatLimitListView() : 116 ");
			result.put("limitList", lmtListFormated);
			if ("manualinput".equals(source)) {
				result.put("isManualInput", "Y");
			}
			
//			LimitDAO lmdao=new LimitDAO();
			List listOfLimitId=new ArrayList();
			DefaultLogger.debug(this, "**********Before getFacilityLimitId() : 124 ");
			listOfLimitId=lmdao.getFacilityLimitId();
			DefaultLogger.debug(this, "**********After getFacilityLimitId() : 126 "+listOfLimitId.size());
			result.put("listOfLimitId", listOfLimitId);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		return true;
	}

	private boolean isNotEmptyStr(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		return true;
	}
}
