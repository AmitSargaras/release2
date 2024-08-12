package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.forex.OBForex;

public class RefreshCurrency extends AbstractCommand {

	public IForexFeedProxy getForexFeedProxy() {
		return (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
	}
	
public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"currencyId", "java.lang.String", REQUEST_SCOPE},
	            {"regionId", "java.lang.String", REQUEST_SCOPE},
	            {"stateId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "regionList", "java.util.List",REQUEST_SCOPE },
	            { "currencyList", "java.util.List", REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
			});
	    }
	
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        BigDecimal exchangeRate = null;
	        try {
	        	String event = (String) map.get("event");
	        	ArrayList currencyList=new ArrayList();      		
	        		String currency = (String) map.get("currencyId");
	        		OBForex cur=null;
	        		cur = getForexFeedProxy().retriveCurrency(currency);
	        		currencyList.add(cur);
	        		//resultMap.put("regionList", getRegionList(countryId));
	        	
	        	if(currencyList!= null)
	    		{
	        		resultMap.put("currencyList", currencyList);
	    		}
	    		else
	    		{
	    			resultMap.put("currencyList", currencyList);
	    		}
		        resultMap.put("event", event);
	        } catch (NoSuchGeographyException nsge) {
	        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
				cpe.initCause(nsge);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
}
