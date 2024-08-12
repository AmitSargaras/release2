/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCurWorkingLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE }, 
				{ "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{"fromEvent", "java.lang.String", REQUEST_SCOPE},
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", REQUEST_SCOPE },

		});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] {{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE }, 
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "systemIdList", "java.util.List", SERVICE_SCOPE },
				{ "liabilityIDList", "java.util.List", SERVICE_SCOPE },
				{ "BASE_INT_RATE", "java.lang.String", SERVICE_SCOPE },
				{ "BPLR_INT_RATE", "java.lang.String", SERVICE_SCOPE },
				{"limitDashboardList", "java.util.List", REQUEST_SCOPE},
				{"totalLienAmount", "java.util.String", SERVICE_SCOPE} ,
				{"totalLimitInOs", "java.util.String", SERVICE_SCOPE},
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{"fromEvent", "java.lang.String", REQUEST_SCOPE},
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
			//	{ "coBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },


				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				
				
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String strBASE="0";
		int intBASE = 0;
		String strBPLR="0";
		int intBPLR = 0;
		DefaultLogger.debug(this, "**********In SaveCurWorkingLmtCmd() : Line 96 ");
		List<LimitCalculationItem> limitDashboardList=new ArrayList<LimitCalculationItem>();
		
		IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
		IGeneralParamEntry generalParamEntryBase = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.BASE_INT_RATE);		
		if(generalParamEntryBase!=null){
			strBASE =generalParamEntryBase.getParamValue();	
		}
		
		IGeneralParamEntry generalParamEntryBPLR = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.BPLR_INT_RATE);		
		if(generalParamEntryBPLR!=null){
			strBPLR =generalParamEntryBPLR.getParamValue();	
		}
		
			try {
				intBASE = Integer.parseInt(strBASE);
				intBPLR = Integer.parseInt(strBPLR);
			}
			catch (Exception e) {
				intBASE = 0;
				intBPLR = 0;
				DefaultLogger.debug(this,"Please set value BASE_INT_RATE, BPLR_INT_RATE in TABLE CMS_GENERAL_PARAM. ");
//				System.out.println("Please set value BASE_INT_RATE, BPLR_INT_RATE in TABLE CMS_GENERAL_PARAM. ");
			}
			DefaultLogger.debug(this, "********** SaveCurWorkingLmtCmd(): Line 120 ");
		
		
		try {
			// just map from form to staging limit and save in trxValue object
			ILimit lmt = (ILimit) (map.get("lmtDetailForm"));
			String custID = (String) (map.get("customerID"));
			
			result.put("systemIdList", getSysID(lmt.getFacilitySystem(), custID));
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
			
			result.put("vendorList", getVendorDtls( custID));
			lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
			
		//	result.put("coBorrowerList", getCoBorrowerList( custID));
			lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
			
			String facCoBorrowerLiabIds =  (String) map.get("facCoBorrowerLiabIds");
		//	System.out.println("facCoBorrowerLiabIds in JAVA CMD:::::"+facCoBorrowerLiabIds);
			result.put("facCoBorrowerLiabIds", facCoBorrowerLiabIds);
			
			DefaultLogger.debug(this, "********** SaveCurWorkingLmtCmd(): Line 141 custID=>"+custID);
			List facCoBorrowerListLine = new ArrayList();
		  if( null !=lmt.getCoBorrowerDetails() && lmt.getCoBorrowerDetails().size() >0 )	
		  {
			for(int i=0;i<lmt.getCoBorrowerDetails().size();i++) {
       	 		String  id= lmt.getCoBorrowerDetails().get(i).getCoBorrowerLiabId();
       	    //	String  name= lmt.getCoBorrowerDetails().get(i).getCoBorrowerName();
       	 		
       	 			LabelValueBean lvBean1 = new LabelValueBean(id, id);
       	 		facCoBorrowerListLine.add(lvBean1);
       	 		}
			}
			result.put("facCoBorrowerList", facCoBorrowerListLine);
			DefaultLogger.debug(this, "********** SaveCurWorkingLmtCmd(): Line 154 ");
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			if(lmt.getSubPartyName() != null && !lmt.getSubPartyName().trim().equals("")){
				result.put("liabilityIDList", getLiabilityList(proxy.getLiabilityIDList(lmt.getSubPartyName())));
			}
			
			String event=(String)map.get("event");
			DefaultLogger.debug(this, "line 162 .. event:"+event);
			
			String fromEvent=(String)map.get("fromEvent");
			DefaultLogger.debug(this, "fromEvent:"+fromEvent);
			
			String isCreate = (String) (map.get("isCreate"));
			result.put("isCreate", isCreate);
			
			//Phase 3 CR :Limit Calculation Dashboard
			double totalLienAmount = CollateralDAOFactory.getDAO().getAllTotalLienAmountBySubProfileId(custID,lmt.getLimitRef(),lmt.getLineNo());
			
			DefaultLogger.debug(this, "Line 167 .. totalLienAmount: "+BigDecimal.valueOf(totalLienAmount).toPlainString());
			
			String totalReleasedAmount=lmt.getTotalReleasedAmount();
			DefaultLogger.debug(this, "line 170 .. totalReleasedAmount: "+totalReleasedAmount);
			if(null!=totalReleasedAmount && !"".equals(totalReleasedAmount)){
				result.put("totalLimitInOs",totalReleasedAmount);
			}else{
				result.put("totalLimitInOs","0.00");
			}
			result.put("totalLienAmount",BigDecimal.valueOf(totalLienAmount).toPlainString());
			result.put("limitDashboardList",limitDashboardList);
			result.put("sessionCriteria",map.get("sessionCriteria"));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		
		result.put("BASE_INT_RATE", strBASE);
		
		result.put("BPLR_INT_RATE", strBPLR);
		result.put("sessionCriteria",map.get("sessionCriteria"));
		result.put("fundedAmount", map.get("fundedAmount"));
		result.put("nonFundedAmount", map.get("nonFundedAmount"));
		result.put("memoExposer", map.get("memoExposer"));
		result.put("sanctionedLimit", map.get("sanctionedLimit"));
		result.put("facCat", map.get("facCat"));
		result.put("inrValue", map.get("inrValue"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "**********Out SaveCurWorkingLmtCmd() Done: Line 195 ");
		return temp;
	}
	
	private List getSysID(String systemName, String custID) {
		List lbValList = new ArrayList();
		try {
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List sysIDs = proxy.getSystemID(systemName, custID);
			
			
			  Iterator iterator = sysIDs.iterator();
			  while(iterator.hasNext()){
				  String id = iterator.next().toString();
				  LabelValueBean lvBean = new LabelValueBean(id, id);
				lbValList.add(lvBean);
			 }
		
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getVendorDtls(String custID) {
		List lbValList = new ArrayList();
		try {
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List vendorName = proxy.getVendorDtls(custID);
			
			
			  Iterator iterator = vendorName.iterator();
			  while(iterator.hasNext()){
				  String id = iterator.next().toString();
				  LabelValueBean lvBean = new LabelValueBean(id, id);
				lbValList.add(lvBean);
			 }
		
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getLiabilityList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[0]+" - "+mgnrLst[1],mgnrLst[0]+" - "+mgnrLst[1] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	
	
	/*private List getCoBorrowerList(String custID) {
		List lbValList = new ArrayList();
		try {
			System.out.println("custID========================= "+custID);
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List coBorrowerName = proxy.getCoBorrowerList(custID);
			
			
			  Iterator iterator = coBorrowerName.iterator();
			  while(iterator.hasNext()){
				  String id = iterator.next().toString();
				  LabelValueBean lvBean = new LabelValueBean(id, id);
				lbValList.add(lvBean);
			 }
		
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}*/
}
