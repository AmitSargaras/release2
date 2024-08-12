/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CalculateFinanceDetailCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "totalNonFundedLimit", "java.lang.String", REQUEST_SCOPE },
				 { "nonFundedSharePercent", "java.lang.String", REQUEST_SCOPE },
				 { "memoExposure", "java.lang.String", REQUEST_SCOPE },
				 { "totalFundedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "fundedSharePercent", "java.lang.String", REQUEST_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				 { "fundedShareLimit", "java.lang.String", REQUEST_SCOPE },
				 { "nonFundedShareLimit", "java.lang.String", REQUEST_SCOPE },
				{ "totalSanctionedLimit", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String totalNonFundedLimits = (String) (map.get("totalNonFundedLimit"));
			if(totalNonFundedLimits == null || "".equals(totalNonFundedLimits))
			{
				totalNonFundedLimits = "0.00";
			}
			String nonFundedSharePercents = (String) (map.get("nonFundedSharePercent"));
			if(nonFundedSharePercents == null || "".equals(nonFundedSharePercents))
			{
				nonFundedSharePercents = "0.00";
			}
			String memoExposures = (String) (map.get("memoExposure"));
			if(memoExposures == null || "".equals(memoExposures))
			{
				memoExposures = "0.00";
			}
			String totalFundedLimits = (String) (map.get("totalFundedLimit"));
			if(totalFundedLimits == null || "".equals(totalFundedLimits))
			{
				totalFundedLimits = "0.00";
			}
			String fundedSharePercents = (String) (map.get("fundedSharePercent"));
			if(fundedSharePercents == null || "".equals(fundedSharePercents))
			{
				fundedSharePercents = "0.00";
			}
			
		BigDecimal fundedShareLimits =new BigDecimal("0.00");
		BigDecimal nonFundedShareLimits = new BigDecimal("0.00");
		BigDecimal totalSanctionedLimits =new BigDecimal("0.00");
	 int roundingMode =  BigDecimal.ROUND_FLOOR;
		BigDecimal totalNonFundedLimit = new BigDecimal(totalNonFundedLimits.replaceAll(",", ""));
		BigDecimal nonFundedSharePercent  =new BigDecimal(nonFundedSharePercents.replaceAll(",", ""));
		BigDecimal memoExposure = new BigDecimal(memoExposures.replaceAll(",", ""));
		BigDecimal totalFundedLimit = new BigDecimal(totalFundedLimits.replaceAll(",", ""));
		BigDecimal fundedSharePercent =  new BigDecimal(fundedSharePercents);
		BigDecimal bigDecimalZero =  new BigDecimal("0.00") ;	
		 BigDecimal hundred = BigDecimal.valueOf(100);
		    BigDecimal percentageFactor = null;
		if(fundedSharePercent != null && !(bigDecimalZero.equals(fundedSharePercent))){
			    BigDecimal percentage = fundedSharePercent;
			    percentageFactor = percentage.divide(hundred, 4,roundingMode);
		fundedShareLimits = totalFundedLimit.multiply(percentageFactor);
		}
		if(nonFundedSharePercent != null && !(bigDecimalZero.equals(nonFundedSharePercent))){
			 BigDecimal percentage = nonFundedSharePercent;
			  percentageFactor = percentage.divide(hundred,4, roundingMode);
	    nonFundedShareLimits= totalNonFundedLimit.multiply(percentageFactor);
		}
		totalSanctionedLimits = totalNonFundedLimit.add(memoExposure);
		totalSanctionedLimits = totalSanctionedLimits.add(totalFundedLimit);
			
//		result.put("fundedShareLimit",String.valueOf(fundedShareLimits));
//		result.put("nonFundedShareLimit", String.valueOf(nonFundedShareLimits));
//		result.put("totalSanctionedLimit", String.valueOf(totalSanctionedLimits));
		
		//Phase 3 CR:comma separated
		result.put("fundedShareLimit",UIUtil.formatWithCommaAndDecimal(String.valueOf(fundedShareLimits)));
		result.put("nonFundedShareLimit", UIUtil.formatWithCommaAndDecimal(String.valueOf(nonFundedShareLimits)));
		result.put("totalSanctionedLimit", UIUtil.formatWithCommaAndDecimal(String.valueOf(totalSanctionedLimits)));
		;
	
				
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
