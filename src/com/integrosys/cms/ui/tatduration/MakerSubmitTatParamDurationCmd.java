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
import java.util.HashSet;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamItem;
import com.integrosys.cms.app.tatduration.proxy.ITatParamProxy;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.systemparameters.propertyindex.PropertyIdxUIHelper;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/11 05:02:19 $ Tag: $Name: $
 */
public class MakerSubmitTatParamDurationCmd extends TatDurationCommand 
{
	/**
	 * Default Constructor
	 */
	public MakerSubmitTatParamDurationCmd() 
	{
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
            {"TatParam", "com.integrosys.cms.app.tatduration.bus.OBTatParam", FORM_SCOPE},
			{"tatParamId", "java.lang.String", REQUEST_SCOPE},
			{"tatParamStageIdString", "java.lang.String", REQUEST_SCOPE},
			{"ITatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE },
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },

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
			PropertyIdxUIHelper helper = new PropertyIdxUIHelper();

			ITatParam tatParam = (ITatParam) map.get("TatParam");
			String tatParamItemIdString = (String) map.get("tatParamStageIdString");
			ITrxContext ctx = (ITrxContext) map.get("theOBTrxContext");
			ITatParamTrxValue trxValue = (ITatParamTrxValue) map.get("ITatParamTrxValue");
			
			if(tatParam == null)
			{
				if(trxValue != null && trxValue.getTatParam() != null)
					tatParam = trxValue.getTatParam();
			}
			
			/*if(!(trxValue != null && trxValue.getStagingTatParam() != null &&
				trxValue.getStagingTatParam().getTatParamItemList() != null &&
				trxValue.getStagingTatParam().getTatParamItemList().size() > 0))
			{
                exceptionMap.put("itemEmptyError", new ActionMessage("error.property.item"));
            }*/
			
			List itemList = new ArrayList(tatParam.getTatParamItemList());
			String paramStageArray[] = tatParamItemIdString.split(";");
			
			for(int n=0; n<itemList.size(); n++) 
			{
				ITatParamItem object = (ITatParamItem) itemList.get(n);
				for (int i=0; i<paramStageArray.length; i++) 
				{
					String value[] = paramStageArray[i].split(",");
					if(String.valueOf(object.getTatParamItemId()).equals(value[2]))
					{
						if(!value[0].equals(String.valueOf(object.getDuration())) ||
								!value[1].equals(String.valueOf(object.getDurationType())))
						{
							object.setDuration(Double.parseDouble(value[0]));
							object.setDurationType(value[1]);
							itemList.set(n, object);
						}
					}
				}
			}
			
			tatParam.setTatParamItemList(new HashSet(itemList));
			
			trxValue = proxy.makerSubmitTatParamDuration(ctx, trxValue, tatParam);
			result.put("request.ITrxValue", trxValue);
			
		}
		catch (Exception e) 
		{
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
