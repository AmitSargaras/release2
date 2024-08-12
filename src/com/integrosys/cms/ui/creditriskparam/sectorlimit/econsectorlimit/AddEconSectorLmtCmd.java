package com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitCommand;
import org.apache.struts.action.ActionMessage;

import java.util.*;

public class AddEconSectorLmtCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"econSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter", FORM_SCOPE},	
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        	ISubSectorLimitParameter sectorLimitObj = (ISubSectorLimitParameter)map.get("subSectorLimitObj");
        	IEcoSectorLimitParameter econObj = (IEcoSectorLimitParameter)map.get("econSectorLmtForm");
        
        	DefaultLogger.debug(this, "SectorLimit getLoanPurposeCode = " + sectorLimitObj.getLoanPurposeCode());
        	DefaultLogger.debug(this, "SectorLimit getEcoSectorList = " + sectorLimitObj.getEcoSectorList());
        	List  originalEcoLimitList = (List) sectorLimitObj.getEcoSectorList();
        
        	if(sectorLimitObj != null){
        		DefaultLogger.debug(this, "SectorLimit originalEcoLimitList size = " + sectorLimitObj.getLoanPurposeCode().length());
        	}
        
        	int ecoSectorLimitSize = (originalEcoLimitList!=null)?originalEcoLimitList.size() : 0;
        	DefaultLogger.debug(this, "ecoSectorLimitSize = " + ecoSectorLimitSize);
        
        	List newEconLimitList = new ArrayList();
        
        	if (ecoSectorLimitSize > 0){
        		newEconLimitList.addAll(originalEcoLimitList);
        	}
        
        	List ecoSectorList = getSectorLimitProxy().listEcoSectorLimit();
        	ecoSectorList.addAll(newEconLimitList);
    	
        	boolean isDuplicate = false;
	
        	if (ecoSectorList != null) {
        		isDuplicate = validateDuplicate(econObj, ecoSectorList, exceptionMap);
        	}
	
        	if (!isDuplicate) {
        		newEconLimitList.add(econObj);
        	}

        	sectorLimitObj.setEcoSectorList(newEconLimitList);

            DefaultLogger.debug(this, "getEcoSectorList = " + sectorLimitObj.getEcoSectorList());

        	String remarks = (String)map.get("remarks");
        	resultMap.put("remarks", remarks);
        
        	resultMap.put("subSectorLimitObj", sectorLimitObj); 
        }
        catch (SectorLimitException e) {
        	DefaultLogger.debug(this,"SectorLimitException caught! " + e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}
	
	private boolean validateDuplicate(IEcoSectorLimitParameter econObj, List ecoSectorList, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
        
		for (int jj= 0; jj < ecoSectorList.size(); jj++) {
                    
            		if (econObj.getSectorCode().equals(((IEcoSectorLimitParameter)ecoSectorList.get(jj)).getSectorCode())){
                        exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                        isDuplicate = true;
                        break;
                        
                    }
            }

       return isDuplicate;
    }
}
