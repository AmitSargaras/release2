/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/PrepareChequeCommand.java,v 1.7 2004/06/04 05:19:56 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/04 05:19:56 $ Tag: $Name: $
 */

public class ValidatePrepareStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",SERVICE_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		//Check for due date dropdown selection 
		String dueDate = (String) map.get("dueDate");
		if(dueDate==null  || "".equals(dueDate)){
			exceptionMap.put("dueDateError", new ActionMessage("label.please.select.option"));
		}
	
		
		//Uma Khot:Cam upload and Dp field calculation CR
		String dpSharePercent = (String) map.get(("dpShare"));
		if(dpSharePercent==null || "".equals(dpSharePercent.trim())){
			exceptionMap.put("dpShareError", new ActionMessage("customer.financialdetail.mandatory"));
		}
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
