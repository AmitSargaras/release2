package com.integrosys.cms.ui.collateral.cash.cashrepo;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.cash.PrepareCashCommand;

/**
 * User: Naveen Date: Feb 20, 20073 Time: 3:05:13 PM To change this template use
 * Options | File Templates.
 */
public class PrepareCashRepoCommand extends PrepareCashCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		String[][] thisDesc = PrepareCashRepoCommandHelper.getResultDescriptor();

		/*
		 * new String[][]{ //TODO Please Fill this UP {"_change_key",
		 * "java.util.Collection", REQUEST_SCOPE} });
		 */
		String[][] fromSuper = super.getResultDescriptor();
		return super.mergeResultDescriptor(thisDesc, fromSuper);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		PrepareCashRepoCommandHelper.fillPrepare(map, result, exceptionMap);

		HashMap fromSuper = super.doExecute(map);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, super.mergeResultMap(result, fromSuper));
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, super.mergeExceptionMap(exceptionMap, fromSuper));

		return temp;
	}

}
