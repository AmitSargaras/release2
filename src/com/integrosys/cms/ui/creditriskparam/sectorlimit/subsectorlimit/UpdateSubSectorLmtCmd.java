package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitCommand;
import org.apache.struts.action.ActionMessage;

import java.util.*;

public class UpdateSubSectorLmtCmd extends SectorLimitCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"subIndexId", "java.lang.String", REQUEST_SCOPE},
			{"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},				
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
            {"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        	
        	String indexId = (String)map.get("subIndexId");

            ISubSectorLimitParameter lastestEcoList = (ISubSectorLimitParameter)map.get("subSectorLimitObj");
        	IMainSectorLimitParameter sectorLimitObj = (IMainSectorLimitParameter)map.get("mainSectorLimitObj");
        	ISubSectorLimitParameter subObj = (ISubSectorLimitParameter)map.get("subSectorLmtForm");
        
        	List originalSubLimitList =  (List) sectorLimitObj.getSubSectorList();
        
        	ISubSectorLimitParameter originalSubSector = (ISubSectorLimitParameter)originalSubLimitList.get(Integer.parseInt(indexId));
        	
        	subObj.setCmsRefId(originalSubSector.getCmsRefId());
        	subObj.setEcoSectorList(lastestEcoList.getEcoSectorList());
        	
        	List subSectorList = getSectorLimitProxy().listSubSectorLimit();
        	
        	List editedsubSectorList = new ArrayList();
        	editedsubSectorList.addAll(originalSubLimitList);
        	editedsubSectorList.remove(Integer.parseInt(indexId));
        	subSectorList.addAll(editedsubSectorList);
        	
        	boolean isDuplicate = false;

        	if (subSectorList != null && !originalSubSector.getSectorCode().equals(subObj.getSectorCode())) {
        		isDuplicate = validateDuplicate(subObj, subSectorList, exceptionMap);
    		}
        	
        	if (!isDuplicate) {
        		originalSubLimitList.set(Integer.parseInt(indexId),subObj);
        	}
        
            sectorLimitObj.setSubSectorList(originalSubLimitList);
        
        	DefaultLogger.debug(this, "Update Sub Sectorlimit = " + sectorLimitObj.getSubSectorList());
        	
        	String remarks = (String)map.get("remarks");
        	resultMap.put("remarks", remarks);
        
        	resultMap.put("sectorLimitObj", sectorLimitObj);
        }
        catch (SectorLimitException e) {
        	DefaultLogger.debug(this,"SectorLimitException caught! " + e.toString());
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}	
	
	private boolean validateDuplicate(ISubSectorLimitParameter originalSubSector, List subSectorList, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
		
		for (int jj= 0; jj < subSectorList.size(); jj++) {
                    
            		if (originalSubSector.getSectorCode().equals(((ISubSectorLimitParameter)subSectorList.get(jj)).getSectorCode())){
                        exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                        isDuplicate = true;
                        break;           
                    }
        }
		return isDuplicate;
    }
}



