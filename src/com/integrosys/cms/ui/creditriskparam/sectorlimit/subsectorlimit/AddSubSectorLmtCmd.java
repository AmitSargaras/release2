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

public class AddSubSectorLmtCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},				
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        
        	IMainSectorLimitParameter sectorLimitObj = (IMainSectorLimitParameter)map.get("mainSectorLimitObj");
        	ISubSectorLimitParameter subObj = (ISubSectorLimitParameter)map.get("subSectorLmtForm");
        
        	Collection originalSubLimitList = sectorLimitObj.getSubSectorList();
        
        	if(sectorLimitObj != null){
        		DefaultLogger.debug(this, "SectorLimit originalSubLimitList size = " + sectorLimitObj.getLoanPurposeCode().length());
        	}
        
        	int subSectorLimitSize = (originalSubLimitList!=null)?originalSubLimitList.size() : 0;
        	DefaultLogger.debug(this, "subSectorLimitSize = " + subSectorLimitSize);
        
        	List subSeclist = new ArrayList();
        
        	if (subSectorLimitSize > 0){
        		subSeclist.addAll(originalSubLimitList);
        	}
                                                                  //todo kc check this should error
        	List subSectorList = getSectorLimitProxy().listSubSectorLimit();
        	subSectorList.addAll(subSeclist);

        	boolean isDuplicate = false;
    	
        	if (subSectorList != null) {
        		isDuplicate = validateDuplicate(subObj, subSectorList, exceptionMap);
        	}
    	
        	if (!isDuplicate) {
        		subSeclist.add(subObj);
        	}

        	String remarks = (String)map.get("remarks");
        	resultMap.put("remarks", remarks);
        

        	sectorLimitObj.setSubSectorList(subSeclist);
            
        	resultMap.put("mainSectorLimitObj", sectorLimitObj);      
        }
        
        catch (SectorLimitException e) {
        	DefaultLogger.debug(this,"SectorLimitException caught! " + e.toString());
        }
        	
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}	
	
	private boolean validateDuplicate(ISubSectorLimitParameter subObj, List subSectorList, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
        
		for (int jj= 0; jj < subSectorList.size(); jj++) {
                    
            		if (subObj.getSectorCode().equals(((ISubSectorLimitParameter)subSectorList.get(jj)).getSectorCode())){
                        exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                        isDuplicate = true;
                        break;
                        
                    }
        }
		
       return isDuplicate;
    }
	
	class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            ISubSectorLimitParameter s1 = (ISubSectorLimitParameter) o1;
            ISubSectorLimitParameter s2 = (ISubSectorLimitParameter) o2;
            return s1.getLoanPurposeCode().compareTo(s2.getLoanPurposeCode());
        }
    }
}
