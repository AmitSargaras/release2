package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class AddCovenantDetailCommand extends AbstractCommand implements ILmtCovenantConstants{
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
					{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
					{ COVENANT_DETAIL_FORM, ILimitCovenant.class.getName(), FORM_SCOPE },
					{"restCountryList","java.util.List", SERVICE_SCOPE },
					{"restCurrencyList","java.util.List", SERVICE_SCOPE },
					{"restBankList","java.util.List", SERVICE_SCOPE },
					{"restDrawerList","java.util.List", SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE }, 
					{"restDraweeList","java.util.List", SERVICE_SCOPE },
					{"restBeneList","java.util.List", SERVICE_SCOPE },
					{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
					{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, "java.util.List", SERVICE_SCOPE },
				};
	}

	
	public String[][] getResultDescriptor() {
		return new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ COVENANT_DETAIL_FORM, ILimitCovenant.class.getName(), FORM_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitId", "java.lang.String", REQUEST_SCOPE }, 
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{"restCountryList","java.util.List", SERVICE_SCOPE },
				{"restCurrencyList","java.util.List", SERVICE_SCOPE },
				{"restBankList","java.util.List", SERVICE_SCOPE },
				{"restDrawerList","java.util.List", SERVICE_SCOPE },
				{"restDraweeList","java.util.List", SERVICE_SCOPE },
				{"restBeneList","java.util.List", SERVICE_SCOPE },
				};
	}
	
	

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			List countryList = (List)map.get("restCountryList");
			List currencyList = (List)map.get("restCurrencyList");
			List bankList = (List)map.get("restBankList");
			List drawerList = (List)map.get("restDrawerList");
			List draweeList = (List)map.get("restDraweeList");
			List beneList = (List)map.get("restBeneList");
			List<OBLimitCovenant> goodsMasterlist = (List)map.get(SESSION_COVENANT_GOODS_RESTRICTION_LIST);
			
			if(draweeList==null) {
				draweeList = new ArrayList();
			}if(drawerList==null) {
				drawerList = new ArrayList();
			}if(beneList==null) {
				beneList = new ArrayList();
			}if(countryList==null) {
				countryList = new ArrayList();
			}if(currencyList==null) {
				currencyList = new ArrayList();
			}if(bankList==null) {
				bankList = new ArrayList();
			}
			if(goodsMasterlist ==null) {
				goodsMasterlist = new ArrayList<OBLimitCovenant>();
			}
			
			for (int i = 0; i < countryList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) countryList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < currencyList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) currencyList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < bankList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) bankList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < drawerList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) drawerList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < draweeList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) draweeList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < beneList.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) beneList.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			for (int i = 0; i < goodsMasterlist.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) goodsMasterlist.get(i);
				covenantDetail.setIsNewEntry("N");
			}
			ILimitCovenant lmt = (ILimitCovenant) map.get(COVENANT_DETAIL_FORM);
			
//			int countrySize =  countryList!=null ? countryList.size() : 0;
//			int currencySize =  currencyList!=null ? currencyList.size() : 0;
//			int bankSize =  bankList!=null ? bankList.size() : 0;
//			int drawerSize =  drawerList!=null ? drawerList.size() : 0;
//			int draweeSize =  draweeList!=null ? draweeList.size() : 0;
//			int beneSize =  beneList!=null ? beneList.size() : 0;

			
			DefaultLogger.info(this, "Adding covenantDetails into session of : " + lmtTrxObj);
			ILimit stageLimit = lmtTrxObj.getStagingLimit();
			//int arr[]={countrySize,currencySize,bankSize,drawerSize,draweeSize,beneSize};  
			//int size = getLargestSize(arr,6);
			addCovenantDetail(stageLimit, lmt,countryList,currencyList,bankList,drawerList,draweeList,beneList,goodsMasterlist,lmtTrxObj);
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
	

	public static void addCovenantDetail(ILimit iLimit, ILimitCovenant covDetail,List<OBLimitCovenant> countryList,List<OBLimitCovenant> currencyList,
			List<OBLimitCovenant> bankList,List<OBLimitCovenant> drawerList,List<OBLimitCovenant> draweeList,List<OBLimitCovenant>beneList, List<OBLimitCovenant> goodsMasterlist, ILimitTrxValue lmtTrxObj) {

		List<OBLimitCovenant> covenants = new ArrayList<OBLimitCovenant>();
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
		covenants.addAll(goodsMasterlist);
		covDetail.setSingleCovenantInd(ICMSConstant.YES);
		covenants.add((OBLimitCovenant) covDetail);
		
		iLimit.setLimitCovenant((ILimitCovenant[]) covenants.toArray(new ILimitCovenant[0]));
		//lmtTrxObj.setLimit(iLimit);
		lmtTrxObj.setStagingLimit(iLimit);
		//ILimitCovenant[] limitcovenant = iLimit.getLimitCovenant();
		
//		ILimitCovenant[] newArray = new ILimitCovenant[size];
//
//		for(int i=0;i<size;i++) {
//			ILimitCovenant newCovDetail = new OBLimitCovenant();
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
//				newCovDetail.setRestrictedCurrency("");
//				newCovDetail.setRestrictedCurrencyAmount("");
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
//				newCovDetail.setDraweeName(draweeList.get(i).getDraweeName()!=null?draweeList.get(i).getDraweeName():null);
//				newCovDetail.setDraweeCustName(draweeList.get(i).getDraweeCustName()!=null?draweeList.get(i).getDraweeCustName():null);
//				newCovDetail.setDraweeAmount(draweeList.get(i).getDraweeAmount()!=null?draweeList.get(i).getDraweeAmount():null);
//				newCovDetail.setDraweeCustId(draweeList.get(i).getDraweeCustId()!=null?draweeList.get(i).getDraweeCustId():null);
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
//		iCol.setLimitCovenant(newArray);
		
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
	
	public static List<OBLimitCovenant> removeComa(List<OBLimitCovenant>  list,String actFlag) 
	{
		
		List<OBLimitCovenant> listnew = new ArrayList<OBLimitCovenant>();
		
		if("countryList".equalsIgnoreCase(actFlag))
		{
			for( OBLimitCovenant list11 : list)
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
			for( OBLimitCovenant list11 : list)
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
			for( OBLimitCovenant list11 : list)
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
			for( OBLimitCovenant list11 : list)
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
			for( OBLimitCovenant list11 : list)
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
			for( OBLimitCovenant list11 : list)
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
