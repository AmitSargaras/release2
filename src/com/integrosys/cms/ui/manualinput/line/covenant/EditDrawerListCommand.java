package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class EditDrawerListCommand extends AbstractCommand implements ILmtCovenantConstants, ILineCovenantConstants{
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "drawerName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerAmount", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustId", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
			});
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
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			{ "restCountryList", "java.util.List", SERVICE_SCOPE },
			{ "restBankList", "java.util.List", SERVICE_SCOPE },
			{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
			{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
			{ "restBeneList", "java.util.List", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
			{ "amount", "java.lang.String", REQUEST_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
			});
	}
			
			public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
				HashMap resultMap = new HashMap();
				HashMap returnMap = new HashMap();
				String event = (String) map.get("event");
				String index = (String) map.get("index");
				if(AbstractCommonMapper.isEmptyOrNull(event)) {
					event="edit_drawer_rest";
				}
				DefaultLogger.debug(this, "Inside doExecute() editdrawerlistCommand "+event);
				DefaultLogger.debug(this, "Inside doExecute() editdrawerlistCommand "+index);

				List vendor = null;
				List list = (List)map.get(SESSION_DRAWER_LIST_LINE);
				HashMap exceptionMap = new HashMap();
				ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
				String drawerName = (String)map.get("drawerName");
				String drawerAmount = (String)map.get("drawerAmount");
				String drawerCustId = (String)map.get("drawerCustId");
				String drawerCustName = (String)map.get("drawerCustName");
				String from_event = (String) (map.get("fromEvent"));
				
				DefaultLogger.debug(this, "drawerName in editdrawerlistCommand.java "+drawerName);
				DefaultLogger.debug(this, "drawerAmount in editdrawerlistCommand.java "+drawerAmount);
				DefaultLogger.debug(this, "drawerCustId in editdrawerlistCommand.java "+drawerCustId);
				DefaultLogger.debug(this, "drawerCustName in editdrawerlistCommand.java "+drawerCustName);
					
				if(list==null)
				{
				list = new ArrayList();
				}
				boolean flag = false;
				ILimitDAO lmtDao = LimitDAOFactory.getDAO();
				List vNames = new ArrayList();
				if(lmtTrxObj.getReferenceID()!=null) {
			     vNames = lmtDao.getRestrictedCustomerListForLine(lmtTrxObj.getReferenceID());
				}
				
				if(list!=null && list.size()!=0)
				{	
					for(int i = 0;i<list.size();i++)
					{
						OBLineCovenant ven = (OBLineCovenant)list.get(i);
						if(ven.getDrawerName()!=null && ven.getDrawerCustId()!=null && ven.getDrawerCustName()!=null
								&&ven.getDrawerAmount()!=null) {
							if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerCustId)
									&&ven.getDrawerCustName().equals(drawerCustName)&&ven.getDrawerAmount().equals(drawerAmount)){
								flag = false;
							}
						}
					}
				}
				
				String errorCode = null;
				//String errorCode = "";
				if(!drawerAmount.equals("") && !"".equals(drawerAmount)) {
					if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(drawerAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
						exceptionMap.put("drawerAmount", new ActionMessage("error.string.serial.no24"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					}
				}
				
				if(exceptionMap.size()>0) {
					return returnMap;
				}
				
				if(!flag)
				{
					if(vNames!=null && vNames.size()!=0)
					{	
						for(int i = 0;i<vNames.size();i++)
						{
							OBLineCovenant ven = (OBLineCovenant)vNames.get(i);
								if(ven.getDrawerName()!=null && ven.getDrawerCustId()!=null && ven.getDrawerCustName()!=null
										&&ven.getDrawerAmount()!=null) {
									if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerCustId)
											&&ven.getDrawerCustName().equals(drawerCustName)&&ven.getDrawerAmount().equals(drawerAmount))	
								{
									exceptionMap.put("drawerListError", new ActionMessage("error.string.duplicate.drawer.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
						}
						}
					
						for(int i = 0;i<list.size();i++)
						{
							OBLineCovenant ven = (OBLineCovenant)list.get(i);
							if(ven.getDrawerName()!=null && ven.getDrawerCustId()!=null && ven.getDrawerCustName()!=null
									&&ven.getDrawerAmount()!=null) {
								if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerCustId)
										&&ven.getDrawerCustName().equals(drawerCustName)&&ven.getDrawerAmount().equals(drawerAmount))	
								{
									exceptionMap.put("drawerListError", new ActionMessage("error.string.duplicate.drawer.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
							}
						}
					
				}
				}	
				OBLineCovenant value = new OBLineCovenant();
				if(from_event.equals("edit_drawer_rest")){	
					value =(OBLineCovenant) list.get((Integer.parseInt(index))-1);
					value.setDrawerName(drawerName);
					value.setDrawerCustId(drawerCustId);
					value.setDrawerCustName(drawerCustName);
					value.setDrawerAmount(drawerAmount);
					value.setDrawerInd(ICMSConstant.YES);
				}
				else{	
					value.setDrawerName(drawerName);
					value.setDrawerCustId(drawerCustId);
					value.setDrawerCustName(drawerCustName);
					value.setDrawerAmount(drawerAmount);
					value.setDrawerInd(ICMSConstant.YES);
				}
				list.remove((Integer.parseInt(index))-1);
				list.add((Integer.parseInt(index))-1, value);
				resultMap.put("index",index);
				resultMap.put("event",event);
				resultMap.put("fromEvent", from_event);
				resultMap.put("restBankList", map.get("restBankList"));
				resultMap.put("restCurrencyList", map.get("restCurrencyList"));
				resultMap.put("restBankList", map.get("restBankList"));
				resultMap.put(SESSION_DRAWER_LIST_LINE, list);
				resultMap.put("restDraweeList", map.get("restDraweeList"));
				resultMap.put("restBeneList", map.get("restBeneList") );
				
				
				DefaultLogger.debug(this, " -------- Create Successfull -----------");
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;		
			}
		}

