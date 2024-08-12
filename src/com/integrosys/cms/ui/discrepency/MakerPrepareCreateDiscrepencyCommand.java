package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 11-04-2011
 * 
 */
public class MakerPrepareCreateDiscrepencyCommand extends AbstractCommand {

		/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
				{ "discType", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] { { "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepencyObj",	"com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
				{ "discType", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this,
				"Inside doExecute() MakerPrepareCreateDiscrepencyCommand");
		String id = (String) map.get("legalCustomerId");
		IDiscrepency discrepency=new OBDiscrepency();
		resultMap.put("startIndexInn", map.get("startIndexInn"));
		ArrayList discrepancyCreateList=(ArrayList)map.get("discrepancyCreateList");
		if(discrepancyCreateList==null||discrepancyCreateList.size()<=0){
		discrepancyCreateList= new ArrayList();
		}
		
		resultMap.put("discrepancyCreateList", discrepancyCreateList);
		resultMap.put("discrepencyObj", discrepency);
		long customerId =0;		
		if( id != null && !(id.equals("")))			
			customerId = Long.parseLong(id);
		resultMap.put("legalCustomerId", String.valueOf(customerId));
		resultMap.put("startIndex", map.get("startIndex"));
		resultMap.put("searchstatus", map.get("searchstatus"));
		resultMap.put("discType",map.get("discType"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
