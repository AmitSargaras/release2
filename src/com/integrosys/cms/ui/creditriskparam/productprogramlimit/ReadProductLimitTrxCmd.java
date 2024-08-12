
package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.OBProductLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;

public class ReadProductLimitTrxCmd extends ProductLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"productLimitId", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"trxId", "java.lang.String", REQUEST_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	    });
	}	        		

	public String[][] getResultDescriptor() {
		return (new String[][]{
            {"productLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", SERVICE_SCOPE},
            {"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
            {"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	    
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        String trxId = (String)map.get("trxId");
        
        String productLimitId = (String)map.get("productLimitId");
        
        OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String)(map.get("event"));
  
        try {
        	IProductLimitParameterTrxValue trxValue = null;
        	
        	
        	if (ProductProgramLimitAction.EVENT_PREPARE.equals(event)) {
	        	resultMap.put("productLimitTrxObj", null);
	        	resultMap.put("productProgramLimitObj", null);
    		} else {	    		
    			if (trxId != null) {
    			    trxValue = getProductLimitProxy().getTrxValueByTrxId(ctx,trxId);
	    			DefaultLogger.debug(this, "Prepare Update SectorLimit trxValue : " + trxValue);
	        	} 
    			else if (productLimitId != null) {    			
	    			trxValue = (OBProductLimitParameterTrxValue) getProductLimitProxy().getTrxValueById(Long.parseLong(productLimitId));
	    		}
    			else {
	        		trxValue = getProductLimitProxy().getTrxValue(ctx);
	        		DefaultLogger.debug(this, "Prepare SectorLimit trxValue : " + trxValue);
	        	}
    			
    			if (checkProductWip(event, trxValue)) {
	    			resultMap.put("wip", "wip");
    			}
    			if(UIUtil.checkDeleteWip(event, trxValue)) {
					   resultMap.put("wip", "wip");
    			}
    			
    			resultMap.put("productLimitTrxObj", trxValue);
    	        resultMap.put("productProgramLimitForm", trxValue.getStagingProductProgramLimitParameter());
    	        resultMap.put("productProgramLimitObj", trxValue.getStagingProductProgramLimitParameter());
    			
	        } 
	    		
	    	
        	resultMap.put("event",event);
        } catch (Exception e) {
        	throw new CommandProcessingException (e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
    }
	
	public static boolean checkProductWip(String event, ITrxValue value)
	{
		if ("prepare_update".equals(event) || "maker_prepare_edit".equals(event) || ProductProgramLimitAction.EVENT_PREPARE_EDIT_PRODUCT.equals(event))
		{
			String status = value.getStatus();
			if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
				|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_DELETE.equals(status)
				|| ICMSConstant.STATE_REJECTED_UPDATE.equals(status) || ICMSConstant.STATE_REJECTED_CREATE.equals(status) 
				|| ICMSConstant.STATE_REJECTED_DELETE.equals(status))
			{
				return true;
			}
		}
		return false;
	}
}
