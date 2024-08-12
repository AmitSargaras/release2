package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class PrepareListRegionCommand extends AbstractCommand{
	
	public PrepareListRegionCommand() {
	}
	
	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {  });

	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "============ do execute PrepareListRegionCommand ");
		map.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		return map;
	}

}
