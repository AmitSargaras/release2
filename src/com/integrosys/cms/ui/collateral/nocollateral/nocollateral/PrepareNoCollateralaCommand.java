package com.integrosys.cms.ui.collateral.nocollateral.nocollateral;

import java.util.HashMap;

import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.nocollateral.PrepareNoCollateralCommand;
//import com.integrosys.cms.ui.collateral.others.othersa.PrepareOthersaCommandHelper;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 26, 2007 Time: 5:58:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareNoCollateralaCommand extends PrepareNoCollateralCommand {
	/*
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "collateralLoc", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				});
	}
	*/

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		//String[][] thisDesc = PrepareOthersaCommandHelper.getResultDescriptor();
		//String[][] fromSuper = super.getResultDescriptor();
		return super.getResultDescriptor();		
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		//PrepareOthersaCommandHelper.fillPrepare(map, result, exceptionMap);
		HashMap fromSuper = super.doExecute(map);
		return fromSuper;
	}

}
