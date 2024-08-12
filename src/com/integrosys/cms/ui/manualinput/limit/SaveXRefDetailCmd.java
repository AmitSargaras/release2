/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;
import com.integrosys.cms.ui.manualinput.line.covenant.AddCovenantDetailCommand;
import com.integrosys.cms.ui.manualinput.line.covenant.ILineCovenantConstants;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveXRefDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "xrefDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, 
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "lineCovenantObj",ILineCovenant.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ ILineCovenantConstants.SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "isLineCovenantUpdated", "java.lang.String", REQUEST_SCOPE },
				{ "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
				{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
			{ "lineCovenantObj",ILineCovenant.class.getName(), SERVICE_SCOPE },
			{ "xrefDetailFormObj",OBCustomerSysXRef.class.getName(), SERVICE_SCOPE },
			{ "lmtId", "java.lang.String", REQUEST_SCOPE },
			{ "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
			{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
			{ ILineCovenantConstants.SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ ILineCovenantConstants.SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String event = (String) (map.get("event"));
			String isLineCovenantUpdated = (String) (map.get("isLineCovenantUpdated"));
			result.put("lmtId",  (String) (map.get("limitId")));
			String fromEventForLineCov=(String)map.get("fromEventForLineCov");
			result.put("fromEventForLineCov", fromEventForLineCov);
			result.put("isLineCreate", (String)map.get("isLineCreate"));
			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			String from_event = (String) map.get("fromEvent");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			ILimit curLimit = lmtTrxObj.getStagingLimit();
			DefaultLogger.debug(this, "-Event : " + event);
			
			String adhocFacility = null;
			if(null!=lmtTrxObj.getLimit()) {
			if(null!=lmtTrxObj.getLimit().getAdhocFacility()) {
			adhocFacility = lmtTrxObj.getLimit().getAdhocFacility();
			System.out.println("=============================adhoc Facility at line 201 prepare xref command "+adhocFacility);
			}
			}
			result.put("adhocFacility",adhocFacility);
			
			OBCustomerSysXRef account = (OBCustomerSysXRef) (map.get("xrefDetailForm"));
			if(null== account) {
				 account=new OBCustomerSysXRef();

			}
			if("return_from_line_covenant".equals(fromEventForLineCov)) {
				ILineCovenant lineCovenantObj = (ILineCovenant) map.get("lineCovenantObj");
				result.put("lineCovenantObj", lineCovenantObj);
			}else {
				result.put("sessionCountryListForLine",new ArrayList());
				result.put("sessionCurrencyListForLine",new ArrayList());
				result.put("sessionBankListForLine",new ArrayList());
				result.put("sessionDrawerListForLine",new ArrayList());
				result.put("sessionDraweeListForLine",new ArrayList());
				result.put("sessionBeneListForLine",new ArrayList());
				result.put("sessionCovenantGoodsRestrictionListForLine",new ArrayList());
			}
			
			ILimitSysXRef[] limitSysXRefs1 =null;
			Set <String> cbHashset=new HashSet<String>();
			String liabId="";
			if(lmtTrxObj.getStagingLimit() != null && lmtTrxObj.getStagingLimit().getLimitSysXRefs()!=null) {
				limitSysXRefs1 = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
			
				for (int i = 0; i < limitSysXRefs1.length; i++) {
				//	ILimitSysXRef iLimitSysXRef1 = limitSysXRefs1[i];	
				
				ILimitXRefCoBorrower[] coborroObj=	limitSysXRefs1[i].getCustomerSysXRef().getXRefCoBorrowerData();
						if(null != coborroObj ) {
							for (int j = 0; j < coborroObj.length; j++) {
								liabId =coborroObj[j].getCoBorrowerId();
								cbHashset.add(liabId);
							}	
						}
				}
			}
			List<String> LineCoBorrowerIdList = new ArrayList<String>();

			if (cbHashset != null && !cbHashset.isEmpty()) {
                for (String borrowId : cbHashset) {
                	LineCoBorrowerIdList.add(borrowId);
                }
           }
			String lineCBId = UIUtil.getJSONStringFromList(LineCoBorrowerIdList, ",");

			lineCBId = lineCBId==null ? "" : lineCBId ;
			System.out.println("IN SaveXrefDetailCmd ssssssssssssssssssss lineCBId==============="+lineCBId);
			result.put("LineCoBorrowIds", lineCBId);

			
			DateFormat dtval=new SimpleDateFormat("dd/MM/yyyy");
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			
			//DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			//String checkDate=df.format(applicationDate);
			
			if(event!= null && (event.equals("submit") || event.equals("submit_ubs") || event.equals("submit_ts")
					||event.equals("submit_rejected") || event.equals("submit_ubs_rejected") || event.equals("submit_ts_rejected")))
			{
				account.setEdited("Y");
			}
			
			else if(event!= null && (event.equals("create") || event.equals("create_ubs") || event.equals("create_ts") || "create_covenant_detail_line".equals(event))){
				account.setEdited("N");
				account.setHiddenSerialNo(account.getSerialNo());
				//account.setAction(ICMSConstant.STATE_NEW);
				if("BAHRAIN".equals(account.getFacilitySystem()) || 
						"HONGKONG".equals(account.getFacilitySystem()) ||
						"GIFTCITY".equals(account.getFacilitySystem())) {
					account.setAction(ICMSConstant.STATE_NEW);
					account.setStatus("PENDING_UPDATE");
					//This is added in case its modify for the above 3 systems.
					if("SUCCESS".equals(account.getStatus())){
						account.setStatus("PENDING_UPDATE");
						account.setSendToFile("N");
						account.setSendToCore("N");
					}
					account.setSendToFile("N");
					account.setSendToCore("N");
					if("BAHRAIN".equals(account.getFacilitySystem())) {
						account.setLiabBranch("001");
					}
					if("HONGKONG".equals(account.getFacilitySystem())) {
						account.setLiabBranch("101");
					}
					if("GIFTCITY".equals(account.getFacilitySystem())) {
						account.setLiabBranch("301");
					}
				}
			}
			
			
			account.setCreatedBy(user.getLoginID());
			account.setCreatedOn(applicationDate);
			
			boolean isCreate = EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_CREATE_UBS.equals(event) || EventConstant.EVENT_CREATE_TS.equals(event);
			ILimitSysXRef[] refArr = curLimit.getLimitSysXRefs();
			boolean isDuplicate = isDuplicate(account, refArr, !isCreate);
			DefaultLogger.debug(this, "-isDuplicate: " + isDuplicate);
			if (isDuplicate) {
				exceptionMap.put("accountNo", new ActionMessage("error.manualinput.limit.duplicate.accountNo"));
				exceptionMap.put("serialNo", new ActionMessage("error.manualinput.limit.duplicate.serialNo"));
				// errors.add("accountNo", new
				// ActionMessage("error.string.mandatory", "1", "50"));
			}
			else {
				if (isCreate) {
					
					ILineCovenant lineCovenantObj = (ILineCovenant) map.get("lineCovenantObj");
					if(lineCovenantObj != null) {
						saveCovenantDetails(account, lineCovenantObj, map);
					}
					
					int arrayLength = (refArr == null ? 0 : refArr.length);
					ILimitSysXRef[] newArray = new ILimitSysXRef[arrayLength + 1];
					if (arrayLength != 0) {
						System.arraycopy(refArr, 0, newArray, 0, arrayLength);
					}
					newArray[arrayLength] = createLimitSysXRef(account);
					refArr = newArray;
					curLimit.setLimitSysXRefs(refArr);
					
					result.put("lineCovenantObj", null);
					
					
				}
				else if(EventConstant.EVENT_SUBMIT_UBS.equals(event) && "Y".equalsIgnoreCase(isLineCovenantUpdated)) {
					
				}
				else if(EventConstant.EVENT_CREATE_COVENANT_LINE.equals(event)) {
					result.put("xrefDetailFormObj",account);
				}
			}
			
			
			
			
		result.put("sessionCriteria",map.get("sessionCriteria"));	
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ILimitSysXRef createLimitSysXRef(ICustomerSysXRef account) {
		ILimitSysXRef link = new OBLimitSysXRef();
		link.setCustomerSysXRef(account);
		link.setStatus(ICMSConstant.STATE_ACTIVE);
		return link;
	}

	private boolean isDuplicate(ICustomerSysXRef acc, ILimitSysXRef[] accArray, boolean inArray) {
		if (accArray == null) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < accArray.length; i++) {
			ICustomerSysXRef tmp = accArray[i].getCustomerSysXRef();
	        if ((acc.getFacilitySystemID()+acc.getSerialNo()).equals(tmp.getFacilitySystemID()+tmp.getSerialNo())) {
				if (inArray) {
					if (count == 0) {
						count++;
					}
					else {
						return true;
					}
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	private void saveCovenantDetails(OBCustomerSysXRef account, ILineCovenant lineCovenantObj, HashMap map) {

		List countryList = (List)map.get(ILineCovenantConstants.SESSION_COUNTRY_LIST_LINE);
		List currencyList = (List)map.get(ILineCovenantConstants.SESSION_CURRENCY_LIST_LINE);
		List bankList = (List)map.get(ILineCovenantConstants.SESSION_BANK_RESTRICTION_LIST_LINE);
		List drawerList = (List)map.get(ILineCovenantConstants.SESSION_DRAWER_LIST_LINE);
		List draweeList = (List)map.get(ILineCovenantConstants.SESSION_DRAWEE_LIST_LINE);
		List beneList = (List)map.get(ILineCovenantConstants.SESSION_BENE_LIST_LINE);
		List<OBLineCovenant> goodsRestList = (List)map.get(ILineCovenantConstants.SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE);
		Long xRefsId = (Long) map.get("xRefsId");
		
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
		if(goodsRestList==null) {
			goodsRestList = new ArrayList();
		}
		
		ILineCovenant lmt = lineCovenantObj;
		
//		int countrySize =  countryList!=null ? countryList.size() : 0;
//		int currencySize =  currencyList!=null ? currencyList.size() : 0;
//		int bankSize =  bankList!=null ? bankList.size() : 0;
//		int drawerSize =  drawerList!=null ? drawerList.size() : 0;
//		int draweeSize =  draweeList!=null ? draweeList.size() : 0;
//		int beneSize =  beneList!=null ? beneList.size() : 0;
		
		if(account != null) {
			ICustomerSysXRef lineStg = account;
			//int arr[]={countrySize,currencySize,bankSize,drawerSize,draweeSize,beneSize};  
			//int size = AddCovenantDetailCommand.getLargestSize(arr,6);
			AddCovenantDetailCommand.addCovenantDetail(lineStg, lmt,countryList,currencyList,bankList,drawerList,draweeList,beneList,goodsRestList);
		}
	

	}
}
