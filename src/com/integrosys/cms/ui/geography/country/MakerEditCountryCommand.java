package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditCountryCommand extends AbstractCommand{
	
	private ICountryProxyManager countryProxy;
	
	public ICountryProxyManager getCountryProxy() {
		return countryProxy;
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

	public MakerEditCountryCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "countryObj", "com.integrosys.cms.app.geography.country.bus.ICountry", FORM_SCOPE },
				{"ICountryTrxValue", "com.integrosys.cms.app.geography.country.trx.ICountryTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
			});		
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
			});
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        
	        try {	        	
	        	ICountry country  =(ICountry) map.get("countryObj");
	        	String event = (String) map.get("event");
	        	OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	        	ICountryTrxValue trxValueIn = (OBCountryTrxValue) map.get("ICountryTrxValue");
	        	ICountryTrxValue trxValueOut = new OBCountryTrxValue();

	        	boolean isCountryNameUnique = false;		
				String newCountryName = country.getCountryName();
				String oldCountryName = "";
												
	        	if (event.equals("maker_edit_country")) {	        		
	        		oldCountryName = trxValueIn.getActualCountry().getCountryName();
	        		
					if( ! newCountryName.equals(oldCountryName) )
						isCountryNameUnique = getCountryProxy().isCountryNameUnique(newCountryName.trim());
		
					if(isCountryNameUnique != false){
						exceptionMap.put("countryNameError", new ActionMessage("error.string.exist","Country Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					trxValueOut = getCountryProxy().makerUpdateCountry(ctx, trxValueIn, country);
					
				} else {
					// event is  maker_confirm_resubmit_edit
					if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") )
						oldCountryName = trxValueIn.getStagingCountry().getCountryName();
					else
						oldCountryName = trxValueIn.getActualCountry().getCountryName();
					
					if( ! newCountryName.equals(oldCountryName) )
						isCountryNameUnique = getCountryProxy().isCountryNameUnique(newCountryName.trim());
		
					if(isCountryNameUnique != false){
						exceptionMap.put("countryNameError", new ActionMessage("error.string.exist","Country Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					String remarks = (String) map.get("remarks");
					ctx.setRemarks(remarks);
					trxValueOut = getCountryProxy().makerEditRejectedCountry(ctx, trxValueIn, country);
				}

				resultMap.put("request.ITrxValue", trxValueOut);				
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
