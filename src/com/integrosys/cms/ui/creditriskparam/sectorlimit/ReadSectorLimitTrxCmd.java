
package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.OBSectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;

import java.util.HashMap;
/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 17, 2008
 */
public class ReadSectorLimitTrxCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"trxId", "java.lang.String", REQUEST_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	    });
	}	        		

	public String[][] getResultDescriptor() {
		return (new String[][]{
            {"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
            {"SectorLimitForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", FORM_SCOPE},
            {"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	    
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        	ISectorLimitParameterTrxValue trxValue ;
        	
        	String trxId = (String)map.get("trxId");
            
        	String sectorLimitId = (String)map.get("sectorLimitId");
        	
            DefaultLogger.debug(this, "Read trx command,SectorLimit trxID is*** " + trxId);
            
            String event = (String)(map.get("event"));
    		DefaultLogger.debug(this, "Read trx command,SectorLimit event is*** " + event);
    		
    		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            
        	if (SectorLimitAction.EVENT_PREPARE.equals(event)) {
	        	resultMap.put("sectorLimitTrxObj", null);
	        	resultMap.put("mainSectorLimitObj", null);
	        	DefaultLogger.debug(this, "Prepare SectorLimit trxValue, we're here!!! ");
    		} else {
	    		if (trxId != null) {
    			    trxValue = getSectorLimitProxy().getTrxValueByTrxId(ctx,trxId);
	    			DefaultLogger.debug(this, "Prepare Update SectorLimit trxValue : " + trxValue);
	        	} 
	    		else if (sectorLimitId != null) {    			
	    			trxValue = (OBSectorLimitParameterTrxValue) getSectorLimitProxy().getTrxValueById(Long.parseLong(sectorLimitId));
                  

	    		}
	    		else {
	        		trxValue = getSectorLimitProxy().getTrxValue(ctx);
	        		DefaultLogger.debug(this, "Prepare SectorLimit trxValue : " + trxValue);
	        } 
	    		
	    	if (checkSectorWip(event, trxValue)) {
	    			resultMap.put("wip", "wip");
			}
	    	
	    	if(UIUtil.checkDeleteWip(event, trxValue)) {
					   resultMap.put("wip", "wip");
			}

	        resultMap.put("sectorLimitTrxObj", trxValue);
	        resultMap.put("SectorLimitForm", trxValue.getStagingMainSectorLimitParameter());
	        resultMap.put("mainSectorLimitObj", trxValue.getStagingMainSectorLimitParameter());

    		}
        	resultMap.put("event",event);
        } catch (Exception e) {
        	throw new CommandProcessingException (e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
    }	   
	
	public static boolean checkSectorWip(String event, ITrxValue value)
	{
		if ("prepare_update".equals(event) || "maker_prepare_edit".equals(event) || SectorLimitAction.EVENT_PREPARE_EDIT_SECTOR.equals(event))
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
