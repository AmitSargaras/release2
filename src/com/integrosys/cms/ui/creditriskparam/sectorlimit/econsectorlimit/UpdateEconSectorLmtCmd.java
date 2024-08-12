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

public class UpdateEconSectorLmtCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"ecoIndexId", "java.lang.String", REQUEST_SCOPE},
			{"econSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter", FORM_SCOPE},	
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        	
        	String indexId = (String)map.get("ecoIndexId");
        
        	ISubSectorLimitParameter sectorLimitObj = (ISubSectorLimitParameter)map.get("subSectorLimitObj");
        	IEcoSectorLimitParameter econObj = (IEcoSectorLimitParameter)map.get("econSectorLmtForm");
        
        	List originalEcoLimitList =  (List) sectorLimitObj.getEcoSectorList();
        	
        	IEcoSectorLimitParameter originalEcoSector = (IEcoSectorLimitParameter)originalEcoLimitList.get(Integer.parseInt(indexId));
        	
        	List ecoSectorList = getSectorLimitProxy().listEcoSectorLimit();
        	
        	List editedEcoSectorList = new ArrayList();
        	editedEcoSectorList.addAll(originalEcoLimitList);
        	editedEcoSectorList.remove(Integer.parseInt(indexId));
        	ecoSectorList.addAll(editedEcoSectorList);
        	
        	boolean isDuplicate = false;

        	if (ecoSectorList != null && !originalEcoSector.getSectorCode().equals(econObj.getSectorCode())) {
        		validateDuplicate(econObj, ecoSectorList, exceptionMap);
        	}
        	
        	if (!isDuplicate) {
        		originalEcoLimitList.set(Integer.parseInt(indexId),econObj); 
        	}
        
        	//Collections.sort(originalEcoLimitList, new AlphabeticComparator());

        	sectorLimitObj.setEcoSectorList(originalEcoLimitList);
        
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
	
	class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            IEcoSectorLimitParameter s1 = (IEcoSectorLimitParameter) o1;
            IEcoSectorLimitParameter s2 = (IEcoSectorLimitParameter) o2;
            return s1.getLoanPurposeCode().compareTo(s2.getLoanPurposeCode());
        }
    }
	
	private void validateDuplicate(IEcoSectorLimitParameter originalEcoSector, List ecoSectorList, HashMap exceptionMap) {
		
		for (int jj= 0; jj < ecoSectorList.size(); jj++) {                
            if (originalEcoSector.getSectorCode().equals(((IEcoSectorLimitParameter)ecoSectorList.get(jj)).getSectorCode())){
            	exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                break;
            }
        }     
    }
	
}
