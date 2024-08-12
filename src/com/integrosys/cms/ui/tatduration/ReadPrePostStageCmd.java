/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatCommand.java,v 1.6 2003/11/11 05:02:19 pooja Exp $
 */

package com.integrosys.cms.ui.tatduration;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamDAO;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import com.integrosys.cms.app.tatduration.proxy.ITatParamProxy;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/11 05:02:19 $ Tag: $Name: $
 */
public class ReadPrePostStageCmd extends TatDurationCommand
{
	/**
	 * Default Constructor
	 */
	public ReadPrePostStageCmd() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{"tatParamId", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
		 {"stagePreDibursmentList", "java.util.List", REQUEST_SCOPE},
		 {"stageDibursmentList", "java.util.List", REQUEST_SCOPE},
		 {"stagePostDibursmentList", "java.util.List", REQUEST_SCOPE},
		 {"tatParamId", "java.lang.String", REQUEST_SCOPE}
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try 
		{
			ITatParamProxy proxy = getTatParamProxy();
			
			Long tatParamId = new Long((String) map.get("tatParamId"));
			ITatParam tatParam = proxy.getTatParam(tatParamId);

			List paramItemList = new ArrayList(tatParam.getTatParamItemList());
			List preDibursment = new ArrayList(), dibursment = new ArrayList(), postDibursment = new ArrayList();
			
			// bubble sort the list
			for(int i=0; i<paramItemList.size()-1; i++) 
			{
				OBTatParamItem item = (OBTatParamItem) paramItemList.get(i);
				OBTatParamItem nextItem = (OBTatParamItem) paramItemList.get(i+1);
				if(item.getSequenceOrder() > nextItem.getSequenceOrder())
				{
					paramItemList.set(i, nextItem);
					paramItemList.set(i+1, item);
					// reset the loop
					i = -1;
				}
			}
			
			for (Iterator iterator = paramItemList.iterator(); iterator.hasNext();) 
			{
				OBTatParamItem item = (OBTatParamItem) iterator.next();
				if("1".equals(item.getStageType()))
					preDibursment.add(item);
				else if("2".equals(item.getStageType()))
					dibursment.add(item);
				else if("3".equals(item.getStageType()))
					postDibursment.add(item);
			}
			
			result.put("stagePreDibursmentList", preDibursment);
			result.put("stageDibursmentList", dibursment);
			result.put("stagePostDibursmentList", postDibursment);
			result.put("tatParamId", tatParamId);

		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
