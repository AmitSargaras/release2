package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class AddCovenantDetailCommand extends AbstractCommand implements ILmtCovenantConstants, ILineCovenantConstants{
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
					{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
					{ COVENANT_LINE_DETAIL_FORM, ILineCovenant.class.getName(), FORM_SCOPE },
					{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
					{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE }, 
					{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
					{ "covenantMap", Map.class.getName(), SERVICE_SCOPE },
					{ "xRefsId", Long.class.getName(), SERVICE_SCOPE },
					{ "xrefDetailFormObj",OBCustomerSysXRef.class.getName(), SERVICE_SCOPE },
				};
	}

	
	public String[][] getResultDescriptor() {
		return new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitId", "java.lang.String", REQUEST_SCOPE }, 
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{"lineCovenantObj",ILineCovenant.class.getName(), SERVICE_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "isLineCovenantUpdated", "java.lang.String", REQUEST_SCOPE },
				{ "xrefDetailFormObj",OBCustomerSysXRef.class.getName(), SERVICE_SCOPE }
				};
	}
	
	

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			List countryList = (List)map.get(SESSION_COUNTRY_LIST_LINE);
			List currencyList = (List)map.get(SESSION_CURRENCY_LIST_LINE);
			List bankList = (List)map.get(SESSION_BANK_RESTRICTION_LIST_LINE);
			List drawerList = (List)map.get(SESSION_DRAWER_LIST_LINE);
			List draweeList = (List)map.get(SESSION_DRAWEE_LIST_LINE);
			List beneList = (List)map.get(SESSION_BENE_LIST_LINE);
			List goodsRestrictionList = (List)map.get(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE);
			Long xRefsId = (Long) map.get("xRefsId");
			

			
			ILineCovenant lineCovenant = (ILineCovenant) map.get(COVENANT_LINE_DETAIL_FORM);
			
//			int countrySize =  countryList!=null ? countryList.size() : 0;
//			int currencySize =  currencyList!=null ? currencyList.size() : 0;
//			int bankSize =  bankList!=null ? bankList.size() : 0;
//			int drawerSize =  drawerList!=null ? drawerList.size() : 0;
//			int draweeSize =  draweeList!=null ? draweeList.size() : 0;
//			int beneSize =  beneList!=null ? beneList.size() : 0;

			
			DefaultLogger.info(this, "Adding covenantDetails into session of : " + lmtTrxObj);
			
			if(lmtTrxObj.getStagingLimit().getLimitSysXRefs() !=null) {
				
				ILimitSysXRef stageSysXref = null;
				for(ILimitSysXRef sysXRef : lmtTrxObj.getStagingLimit().getLimitSysXRefs()) {
					if(xRefsId != null && sysXRef.getSID() == xRefsId) {
						stageSysXref = sysXRef;
					}
				}
				
				if(stageSysXref != null) {
					ICustomerSysXRef lineStg = stageSysXref.getCustomerSysXRef();
					addCovenantDetail(lineStg, lineCovenant,countryList,currencyList,bankList,drawerList,draweeList,beneList,goodsRestrictionList);
				}	
			}/*else {
				ICustomerSysXRef account = null;
				account = (ICustomerSysXRef) map.get("xrefDetailFormObj");
				if(account!=null) {
					addCovenantDetail(account, lineCovenant,countryList,currencyList,bankList,drawerList,draweeList,beneList,goodsRestrictionList);
				}
				result.put("xrefDetailFormObj",account);
			}*/
			/*if(lmtTrxObj.getStagingLimit().getLimitSysXRefs() !=null) {
				
				ILimitSysXRef stageSysXref = null;
				for(ILimitSysXRef sysXRef : lmtTrxObj.getStagingLimit().getLimitSysXRefs()) {
					if(xRefsId != null && sysXRef.getSID() == xRefsId) {
						stageSysXref = sysXRef;
					}
				}
				
				if(stageSysXref != null) {
					ICustomerSysXRef lineStg = stageSysXref.getCustomerSysXRef();
					//ILimitSysXRef limtSysRef = stageLimit;
					int arr[]={countrySize,currencySize,bankSize,drawerSize,draweeSize,beneSize};  
					int size = getLargestSize(arr,6);
					addCovenantDetail(lineStg, lmt,size,countryList,currencyList,bankList,drawerList,draweeList,beneList);
				}
				else {
					result.put("lineCovenantObj", lmt);
				}
			}
			else {
				result.put("lineCovenantObj", lmt);
			}*/
			
			HashMap<String,String> covenantMap = (HashMap) map.get("covenantMap");
			
			if(covenantMap!=null) {
				result.put("facCat", (String)covenantMap.get("facCat"));
				result.put("fundedAmount", (String)covenantMap.get("fundedAmount"));
				result.put("nonFundedAmount", (String)covenantMap.get("nonFundedAmount"));
				result.put("memoExposer", (String)covenantMap.get("memoExposer"));
				result.put("sanctionedLimit", (String)covenantMap.get("sanctionedLimit"));
			}
			
			result.put("isLineCovenantUpdated", ICMSConstant.YES);
			result.put("lineCovenantObj", lineCovenant);
			
			result.put("lmtTrxObj", lmtTrxObj);
			result.put("limitProfileID",map.get("limitProfileID"));
			result.put("limitId",map.get("limitId"));
			result.put("customerID",map.get("customerID"));
			result.put("trxID",map.get("trxID"));
			result.put("event",map.get("event"));
			

			
		}catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
}
	

	public static void addCovenantDetail(ICustomerSysXRef xref, ILineCovenant covDetail,List<OBLineCovenant> countryList,List<OBLineCovenant> currencyList,
			List<OBLineCovenant> bankList,List<OBLineCovenant> drawerList,List<OBLineCovenant> draweeList,List<OBLineCovenant>beneList, List<OBLineCovenant> goodsRestrictionList) {

		List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();
		if(countryList!=null && !countryList.isEmpty())
		{
			countryList=removeComa(countryList, "countryList");
			covenants.addAll(countryList);
		}
		if(currencyList!=null && !currencyList.isEmpty())
		{
			currencyList=removeComa(currencyList, "currencyList");
			covenants.addAll(currencyList);
		}
		if(bankList!=null && !bankList.isEmpty())
		{
			bankList=removeComa(bankList, "bankList");
			covenants.addAll(bankList);
		}
		if(drawerList!=null && !drawerList.isEmpty())
		{
			drawerList=removeComa(drawerList, "drawerList");
			covenants.addAll(drawerList);
		}
		if(draweeList!=null && !draweeList.isEmpty())
		{
			draweeList=removeComa(draweeList, "draweeList");
			covenants.addAll(draweeList);
		}
		if(beneList!=null && !beneList.isEmpty())
		{
			beneList=removeComa(beneList, "beneList");
			covenants.addAll(beneList);
		}		
		
			covenants.addAll(goodsRestrictionList);
		
		
		covDetail.setSingleCovenantInd(ICMSConstant.YES);
		covenants.add((OBLineCovenant) covDetail);
	
		xref.setLineCovenant((ILineCovenant[]) covenants.toArray(new ILineCovenant[0]));
		
		
//		ILineCovenant[] newArray = new ILineCovenant[size];
//
//		for(int i=0;i<size;i++) {
//			ILineCovenant newCovDetail = new OBLineCovenant();
//			AccessorUtil.copyValue(covDetail, newCovDetail);
//			if(countryList.size()>i) {
//				newCovDetail.setRestrictedCountryname(countryList.get(i).getRestrictedCountryname());
//				newCovDetail.setRestrictedAmount(countryList.get(i).getRestrictedAmount());
//			}else {
//				newCovDetail.setRestrictedCountryname("");
//				newCovDetail.setRestrictedAmount("");
//			}
//			
//			if(bankList.size()>i) {
//				newCovDetail.setRestrictedBank(bankList.get(i).getRestrictedBank());
//				newCovDetail.setRestrictedBankAmount(bankList.get(i).getRestrictedBankAmount());
//			}else {
//				newCovDetail.setRestrictedBank("");
//				newCovDetail.setRestrictedBankAmount("");
//			}
//			
//			if(currencyList.size()>i) {
//				newCovDetail.setRestrictedCurrency(currencyList.get(i).getRestrictedCurrency());
//				newCovDetail.setRestrictedCurrencyAmount(currencyList.get(i).getRestrictedCurrencyAmount());
//			}else {
//				newCovDetail.setRestrictedBank("");
//				newCovDetail.setRestrictedBankAmount("");
//			}
//			
//			if(drawerList.size()>i) {
//				newCovDetail.setDrawerName(drawerList.get(i).getDrawerName());
//				newCovDetail.setDrawerCustName(drawerList.get(i).getDrawerCustName());
//				newCovDetail.setDrawerAmount(drawerList.get(i).getDrawerAmount());
//				newCovDetail.setDrawerCustId(drawerList.get(i).getDrawerCustId());
//			}else {
//				newCovDetail.setDrawerName("");
//				newCovDetail.setDrawerCustName("");
//				newCovDetail.setDrawerAmount("");
//				newCovDetail.setDrawerCustId("");
//			}
//			
//			if(draweeList.size()>i) {
//			newCovDetail.setDraweeName(draweeList.get(i).getDraweeName()!=null?draweeList.get(i).getDraweeName():null);
//			newCovDetail.setDraweeCustName(draweeList.get(i).getDraweeCustName()!=null?draweeList.get(i).getDraweeCustName():null);
//			newCovDetail.setDraweeAmount(draweeList.get(i).getDraweeAmount()!=null?draweeList.get(i).getDraweeAmount():null);
//			newCovDetail.setDraweeCustId(draweeList.get(i).getDraweeCustId()!=null?draweeList.get(i).getDraweeCustId():null);
//			}else {
//				newCovDetail.setDraweeName("");
//				newCovDetail.setDraweeCustName("");
//				newCovDetail.setDraweeAmount("");
//				newCovDetail.setDraweeCustId("");
//			}
//			
//			if(beneList.size()>i) {
//				newCovDetail.setBeneName(beneList.get(i).getBeneName()!=null?beneList.get(i).getBeneName():null);
//				newCovDetail.setBeneCustName(beneList.get(i).getBeneCustName()!=null?beneList.get(i).getBeneCustName():null);
//				newCovDetail.setBeneAmount(beneList.get(i).getBeneAmount()!=null?beneList.get(i).getBeneAmount():null);
//				newCovDetail.setBeneCustId(beneList.get(i).getBeneCustId()!=null?beneList.get(i).getBeneCustId():null);
//			}else {
//				newCovDetail.setBeneName("");
//				newCovDetail.setBeneCustName("");
//				newCovDetail.setBeneAmount("");
//				newCovDetail.setBeneCustId("");
//			}
//			newArray[i] = newCovDetail;
//		}
//		iCol.setLineCovenant(newArray);

		
	}
	
	public static int getLargestSize(int[] a, int total){  
		int temp;  
		for (int i = 0; i < total; i++)   
		        {  
		            for (int j = i + 1; j < total; j++)   
		            {  
		                if (a[i] > a[j])   
		                {  
		                    temp = a[i];  
		                    a[i] = a[j];  
		                    a[j] = temp;  
		                }  
		            }  
		        }  
		       return a[total-1];  
		}  
	
	public static List<OBLineCovenant> removeComa(List<OBLineCovenant>  list,String actFlag) 
	{
		
		List<OBLineCovenant> listnew = new ArrayList<OBLineCovenant>();
		
		if("countryList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getRestrictedAmount()))
					{
					list11.setRestrictedAmount(UIUtil.removeComma(list11.getRestrictedAmount()));
					}	
				listnew.add(list11);
			}
		
			
		}
		if("currencyList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getRestrictedCurrencyAmount()))
					{
					list11.setRestrictedCurrencyAmount(UIUtil.removeComma(list11.getRestrictedCurrencyAmount()));
					}	
				listnew.add(list11);
			}
			
			
		}
		if("bankList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getRestrictedBankAmount()))
					{
					list11.setRestrictedBankAmount(UIUtil.removeComma(list11.getRestrictedBankAmount()));
					}	
				listnew.add(list11);
			}
			
			
		}

		if("drawerList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getDrawerAmount()))
					{
					list11.setDrawerAmount(UIUtil.removeComma(list11.getDrawerAmount()));
					}	
				listnew.add(list11);
			}
			
			
		}

		if("draweeList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getDraweeAmount()))
					{
					list11.setDraweeAmount(UIUtil.removeComma(list11.getDraweeAmount()));
					}	
				listnew.add(list11);
			}
			
			
		}
		
		if("beneList".equalsIgnoreCase(actFlag))
		{
			for( OBLineCovenant list11 : list)
			{
				if (!AbstractCommonMapper.isEmptyOrNull(list11.getBeneAmount()))
					{
					list11.setBeneAmount(UIUtil.removeComma(list11.getBeneAmount()));
					}	
				listnew.add(list11);
			}
			
			
		}
		
		
		return listnew;
	}
}
