package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class ExemptFacilityUIHelper {
	
	public List getSortedExemptFacilityMap (List exemptFacility)
	{
		Map exemptFacilityMap = new HashMap();
		   
	    for (int i=0; i<exemptFacility.size(); i++) {
	    	
	    	if (exemptFacility.get(i) instanceof CompareResult) {
	    		CompareResult item = (CompareResult) exemptFacility.get(i);
	    		exemptFacilityMap.put(item, CommonDataSingleton.getCodeCategoryLabelByValue(ICategoryEntryConstant.FACILITY_DESCRIPTION, ((IExemptFacility)((CompareResult)exemptFacility.get(i)).getObj()).getFacilityCode()));
	    	}
	    	else {
	    		IExemptFacility item = (IExemptFacility)exemptFacility.get(i);
	    		exemptFacilityMap.put(item, CommonDataSingleton.getCodeCategoryLabelByValue(ICategoryEntryConstant.FACILITY_DESCRIPTION, ((IExemptFacility)exemptFacility.get(i)).getFacilityCode()));
	    	}

	    }
	        
	    List mapKeys = new ArrayList(exemptFacilityMap.keySet());
	        
		List mapValues = new ArrayList(exemptFacilityMap.values());
		
	    String[] sortArray = (String[])mapValues.toArray(new String[] {});
	    
	    exemptFacilityMap.clear();
	        
	    Arrays.sort(sortArray);
	    
	    List sortList = new ArrayList(); 
	           
	    for (int j=0; j<sortArray.length; j++)
		{
			sortList.add(mapKeys.get(mapValues.indexOf(sortArray[j])));
		}

		return sortList;
		
	}
	

}
