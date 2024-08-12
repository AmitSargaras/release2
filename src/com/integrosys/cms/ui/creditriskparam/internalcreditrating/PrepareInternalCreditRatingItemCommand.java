package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.creditriskparam.countrylimit.EventConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class PrepareInternalCreditRatingItemCommand extends AbstractCommand {
public class PrepareInternalCreditRatingItemCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{ 
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"errorEvent", "java.lang.String", REQUEST_SCOPE},
			{"indexID", "java.lang.String", REQUEST_SCOPE},
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},			
			{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"curItem", "com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating", SERVICE_SCOPE},
			{"origItem", "com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating", SERVICE_SCOPE},
			{ "creditRatingGradeValues" , "java.util.Collection" , ICommonEventConstant.REQUEST_SCOPE },
			{ "creditRatingGradeLabels" , "java.util.Collection" , ICommonEventConstant.REQUEST_SCOPE },
			{"errorEvent", "java.lang.String", REQUEST_SCOPE},			
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try
		{
			//CountryLimitUIHelper helper = new CountryLimitUIHelper();
			String event = (String)(map.get("event"));
			String errorEvent = (String)(map.get("errorEvent"));
			String from_event = (String)(map.get("fromEvent"));				
			IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
			IInternalCreditRating curItem = null;
			
			DefaultLogger.debug(this,"internalCreditRatingTrxObj 11:: : "+ internalCreditRatingTrxObj);
			if ("maker_create_item".equals(event))
			{
				curItem = new OBInternalCreditRating();
			}
			else if ("maker_edit_item".equals(event))
			{
				int index = Integer.parseInt((String) map.get("indexID"));
				curItem = getCurWorkingCreditRatingItem(event, from_event, index, internalCreditRatingTrxObj);
			}
			if ( errorEvent == null ) {
				IInternalCreditRating origItem = new OBInternalCreditRating( curItem );
				result.put("origItem", origItem);		
			}
			
			CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.INTERNAL_CREDIT_GRADE);
      result.put ( "creditRatingGradeValues", commonCode.getCommonCodeValues() );
      result.put ( "creditRatingGradeLabels", commonCode.getCommonCodeLabels() );
        
			result.put("curItem", curItem);							
		
			
	        
		}
		catch(Exception ex)
		{
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	public IInternalCreditRating getCurWorkingCreditRatingItem(String event, String fromEvent, int index, IInternalCreditRatingTrxValue trxValue)
	{
		List template = null;
		if (EventConstant.EVENT_READ.equals(fromEvent))
		{
			template = trxValue.getActualICRList();
		}
		else
		{
			template = trxValue.getStagingICRList();
		}
		if (template != null)
		{
			return (IInternalCreditRating)template.get(index);
		}
		return null;
	}	
	
	
}
