package com.integrosys.cms.ui.manualinput.limit.covenant;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class EditBeneListCommand extends AbstractCommand{
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", SERVICE_SCOPE },
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
			{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
			{ "restBeneList", "java.util.List", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
			{ "amount", "java.lang.String", REQUEST_SCOPE },
			{ "amount", "java.lang.String", SERVICE_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
			});
	}
			
			public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
				HashMap resultMap = new HashMap();
				HashMap returnMap = new HashMap();
				String event = (String) map.get("event");
				String index = (String) map.get("index");
				
				DefaultLogger.debug(this, "Inside doExecute() editbenelistCommand "+event);
				DefaultLogger.debug(this, "Inside doExecute() editbenelistCommand "+index);

				List vendor = null;
				List list = (List)map.get("restBeneList");
				HashMap exceptionMap = new HashMap();
				ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
				String vendorName = (String)map.get("vendorName");
				String beneficiaryName = (String)map.get("beneficiaryName");
				String beneficiaryAmount = (String)map.get("beneficiaryAmount");
				String beneficiaryCustId = (String)map.get("beneficiaryCustId");
				String beneficiaryCustName = (String)map.get("beneficiaryCustName");
				String from_event = (String) (map.get("fromEvent"));
				
				DefaultLogger.debug(this, "beneficiaryName in editbenelistCommand.java "+beneficiaryName);
				DefaultLogger.debug(this, "beneficiaryAmount in editbenelistCommand.java "+beneficiaryAmount);
				DefaultLogger.debug(this, "beneficiaryCustId in editbenelistCommand.java "+beneficiaryCustId);
				DefaultLogger.debug(this, "beneficiaryCustName in editbenelistCommand.java "+beneficiaryCustId);


				if(list==null)
				{
				list = new ArrayList();
				}
				boolean flag = false;
				ILimitDAO lmtDao = LimitDAOFactory.getDAO();
				List vNames = new ArrayList();
				if(lmtTrxObj.getReferenceID()!=null) {
			     vNames = lmtDao.getRestrictedCustomerList(lmtTrxObj.getReferenceID());
				}
				
				if(list!=null && list.size()!=0)
				{	
					for(int i = 0;i<list.size();i++)
					{
					OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
					if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
							&&ven.getBeneAmount()!=null) {
						if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
								&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount)){
						flag = false;
						}
					}
					}
				}
				
				String errorCode = null;
				//String errorCode = "";
				if(!beneficiaryAmount.equals("") && !"".equals(beneficiaryAmount)) {
					if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(beneficiaryAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
						exceptionMap.put("beneficiaryAmount", new ActionMessage("error.string.serial.no24"));
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
							OBLimitCovenant ven = (OBLimitCovenant)vNames.get(i);
								if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
										&&ven.getBeneAmount()!=null) {
									if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
											&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount))	
								{
									exceptionMap.put("beneListError", new ActionMessage("error.string.duplicate.bene.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
						}
						}
					
						for(int i = 0;i<list.size();i++)
						{
							OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
							if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
									&&ven.getBeneAmount()!=null) {
								if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
										&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount))	
								{
									exceptionMap.put("beneListError", new ActionMessage("error.string.duplicate.bene.details"));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
									return returnMap;
								}
							}
						}
					
				}
				}	
				OBLimitCovenant value = new OBLimitCovenant();
				if(from_event.equals("edit_bene_rest")){	
					value =(OBLimitCovenant) list.get((Integer.parseInt(index))-1);
					value.setBeneName(beneficiaryName);
					value.setBeneCustId(beneficiaryCustId);
					value.setBeneCustName(beneficiaryCustName);
					value.setBeneAmount(beneficiaryAmount);
					value.setBeneInd(ICMSConstant.YES);
				}
				else{	
					value.setBeneName(beneficiaryName);
					value.setBeneCustId(beneficiaryCustId);
					value.setBeneCustName(beneficiaryCustName);
					value.setBeneAmount(beneficiaryAmount);
					value.setBeneInd(ICMSConstant.YES);
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
				resultMap.put("restDraweeList", map.get("restDraweeList"));
				resultMap.put("restBeneList", list);
				
				
				DefaultLogger.debug(this, " -------- Create Successfull -----------");
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;		
			}
		}

