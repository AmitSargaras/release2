package com.integrosys.cms.ui.creditriskparam.sectorlimit;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.OBSectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 16, 2008
 */
public class SubmitSectorLimitCmd extends SectorLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
            {"SectorLimitForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", FORM_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        ISectorLimitParameterTrxValue trxValue = null;

        try {
        	 IMainSectorLimitParameter obj = (IMainSectorLimitParameter)map.get("SectorLimitForm");
             String sectorCode = obj.getLoanPurposeCode();
             DefaultLogger.debug(this, "sectorCode" +sectorCode);
        	
	         ITrxContext ctx = (ITrxContext)map.get("theOBTrxContext");
	         ISectorLimitParameterTrxValue sectorLimitTrxObj = (ISectorLimitParameterTrxValue) map.get("sectorLimitTrxObj");
	         if (sectorLimitTrxObj == null)
	           	sectorLimitTrxObj = new OBSectorLimitParameterTrxValue();
	            
	         IMainSectorLimitParameter sectorLimitParameter = sectorLimitTrxObj.getActualMainSectorLimitParameter();
	         DefaultLogger.debug(this, "SectorLimitParameter"+ sectorLimitParameter);
	         
	         List sectorLimitList = getSectorLimitProxy().listSectorLimit();
	         
	         boolean isDuplicate = false;
		        
	         if (sectorLimitList != null) {
	        	 if (sectorLimitParameter != null){
	        		 for (int i = 0; i < sectorLimitList.size(); i++) {
		        		if (((IMainSectorLimitParameter)(sectorLimitList.get(i))).getSectorCode().equals(obj.getSectorCode())) {
		        			sectorLimitList.remove(i);
		        			break;
		        		}
		        	}
	        	 }
	        	 isDuplicate = validateDuplicate(sectorLimitList, obj, exceptionMap);
	        	 
	         }
	         
	         if (!isDuplicate) {	      
	        	 if (null == sectorLimitParameter){
	        		 sectorLimitTrxObj.setStagingMainSectorLimitParameter(obj);
	        		 trxValue = getSectorLimitProxy().makerUpdateList(ctx, sectorLimitTrxObj);
	 
	        	 } else {
	        		 //update
	        		 sectorLimitTrxObj.setStagingMainSectorLimitParameter(obj);
	        		 trxValue = getSectorLimitProxy().makerUpdateList(ctx, sectorLimitTrxObj);
	        	 }
	         }
	         resultMap.put("request.ITrxValue", trxValue);
	         returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	         returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        }catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
    
    private boolean validateDuplicate(List sectorLimitList, IMainSectorLimitParameter sectorLimit, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
        
		for (int i = 0; i < sectorLimitList.size(); i++) {

            if (((IMainSectorLimitParameter)sectorLimitList.get(i)).getSectorCode().equals(sectorLimit.getSectorCode())){
            	exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                isDuplicate = true;
                break;                     
            }
        }
       return isDuplicate;
    }
}
