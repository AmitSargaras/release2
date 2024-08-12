/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshSancAmountCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "sancAmount", "java.lang.String", REQUEST_SCOPE },
				{ "currencyCode", "java.lang.String", REQUEST_SCOPE },
				{ "tempSancAmount", "java.lang.String", REQUEST_SCOPE },
				{ "adhocAmount", "java.lang.String", REQUEST_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "sancAmount", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String errorCode = null;
		BigDecimal sancAmountVal = new BigDecimal(0);
		String flag = "false";
		
			String sancAmount = (String) (map.get("sancAmount"));
			String currencyCode = (String) (map.get("currencyCode"));
			String tempSancAmount = (String) (map.get("tempSancAmount"));
			String adhocAmount = (String) (map.get("adhocAmount"));
			
			if(adhocAmount.equalsIgnoreCase("undefined")){
				adhocAmount ="0";
			}
			
			if (!(errorCode = Validator.checkNumber(sancAmount, false, 0,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2 )).equals(Validator.ERROR_NONE)) {
				flag = "true";
			}
			if (!tempSancAmount.equalsIgnoreCase("null") && !(errorCode = Validator.checkNumber(tempSancAmount, false, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2)).equals(Validator.ERROR_NONE)) {
				flag = "true";
			}
			if (!(errorCode = Validator.checkNumber(adhocAmount, false, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2)).equals(Validator.ERROR_NONE)) {
				flag = "true";
			}
			
			if(flag.equals("false")) {
			BigDecimal tempValue = null;
			if(sancAmount == null || sancAmount.trim().equals("") || sancAmount.trim().equals("null")){
				tempValue = new BigDecimal("0"); 
				tempValue = new BigDecimal(adhocAmount.replaceAll(",", "")).add(tempValue);
			}else {
					tempValue = new BigDecimal(sancAmount.replaceAll(",", ""));
			}
			
//			System.out.println("sancAmount+"+sancAmount);
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
					BigDecimal exchangeRate = null;
					if(!AbstractCommonMapper.isEmptyOrNull(currencyCode)){
							 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
							 exchangeRate = frxPxy.getExchangeRateWithINR(currencyCode.trim());
					 }
					sancAmountVal = exchangeRate.multiply(tempValue);
			}
		result.put("flag", flag);
	//	result.put("inrValue", sancAmountVal.toString());
		
		//Phase 3 CR:comma separated
		result.put("inrValue", UIUtil.formatWithCommaAndDecimal(sancAmountVal.toString()));
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
