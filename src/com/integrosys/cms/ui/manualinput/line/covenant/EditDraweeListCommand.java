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

public class EditDraweeListCommand extends AbstractCommand implements ILmtCovenantConstants, ILineCovenantConstants{
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "draweeName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
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
			{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
			{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
			{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "restBeneList", "java.util.List", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
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
					event="edit_drawee_rest";
				}
				DefaultLogger.debug(this, "Inside doExecute() editdraweelistCommand "+event);
				DefaultLogger.debug(this, "Inside doExecute() editdraweelistCommand "+index);

				List vendor = null;
				List list = (List)map.get(SESSION_DRAWEE_LIST_LINE);
				HashMap exceptionMap = new HashMap();
				ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
				String draweeName = (String)map.get("draweeName");
				String draweeAmount = (String)map.get("draweeAmount");
				String draweeCustId = (String)map.get("draweeCustId");
				String draweeCustName = (String)map.get("draweeCustName");
				String from_event = (String) (map.get("fromEvent"));
				
				DefaultLogger.debug(this, "draweeName in editdraweelistCommand.java "+draweeName);
				DefaultLogger.debug(this, "draweeAmount in editdraweelistCommand.java "+draweeAmount);
				DefaultLogger.debug(this, "draweeCustId in editdraweelistCommand.java "+draweeCustId);
				DefaultLogger.debug(this, "draweeCustName in editdraweelistCommand.java "+draweeCustName);


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
						if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
								&&ven.getDraweeAmount()!=null) {
							if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
									&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount)){
								flag = false;
							}
						}
					}
				}
				
				String errorCode = null;
				//String errorCode = "";
				if(!draweeAmount.equals("") && !"".equals(draweeAmount)) {
					if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(draweeAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
						exceptionMap.put("draweeAmount", new ActionMessage("error.string.serial.no24"));
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
								if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
										&&ven.getDraweeAmount()!=null) {
									if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
											&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount))	
								{
									exceptionMap.put("draweeListError", new ActionMessage("error.string.duplicate.drawee.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
						}
						}
					
						for(int i = 0;i<list.size();i++)
						{
							OBLineCovenant ven = (OBLineCovenant)list.get(i);
							if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
									&&ven.getDraweeAmount()!=null) {
								if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
										&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount))	
								{
									exceptionMap.put("draweeListError", new ActionMessage("error.string.duplicate.drawee.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
							}
						}
					
				}
				}	
				OBLineCovenant value = new OBLineCovenant();
				if(from_event.equals("edit_drawee_rest")){	
					value =(OBLineCovenant) list.get((Integer.parseInt(index))-1);
					value.setDraweeName(draweeName);
					value.setDraweeCustId(draweeCustId);
					value.setDraweeCustName(draweeCustName);
					value.setDraweeAmount(draweeAmount);
					value.setDraweeInd(ICMSConstant.YES);
				}
				else{	
					value.setDraweeName(draweeName);
					value.setDraweeCustId(draweeCustId);
					value.setDraweeCustName(draweeCustName);
					value.setDraweeAmount(draweeAmount);
					value.setDraweeInd(ICMSConstant.YES);
				}
				list.remove((Integer.parseInt(index))-1);
				list.add((Integer.parseInt(index))-1, value);
				resultMap.put("index",index);
				resultMap.put("event",event);
				resultMap.put("fromEvent", from_event);
				resultMap.put("restBankList", map.get("restBankList"));
				resultMap.put("restCurrencyList", map.get("restCurrencyList"));
				resultMap.put("restBankList", map.get("restBankList"));
				resultMap.put("restDrawerList", map.get("restDrawerList"));
				resultMap.put(SESSION_DRAWEE_LIST_LINE, list);
				resultMap.put("restBeneList",map.get("restBeneList") );
				
				
				DefaultLogger.debug(this, " -------- Create Successfull -----------");
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;		
			}
		}

