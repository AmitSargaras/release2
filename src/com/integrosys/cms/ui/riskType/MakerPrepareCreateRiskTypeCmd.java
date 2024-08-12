package com.integrosys.cms.ui.riskType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.riskType.bus.RiskTypeDaoImpl;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;

public class MakerPrepareCreateRiskTypeCmd extends AbstractCommand implements
ICommonEventConstant{

	private IRiskTypeProxyManager riskTypeProxy;

	public IRiskTypeProxyManager getRiskTypeProxy() {
		return riskTypeProxy;
	}

	public void setRiskTypeProxy(IRiskTypeProxyManager riskTypeProxy) {
		this.riskTypeProxy = riskTypeProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateRiskTypeCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
		});
	}
	
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
			{"IRiskTypeTrxValue", "com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue", SERVICE_SCOPE},
			{ "riskGrade", "java.util.Set", SERVICE_SCOPE }
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBRiskTypeTrxValue riskTypeTrxValue = new OBRiskTypeTrxValue();
		Set<String> riskGrade=new HashSet<String>();
		RiskTypeDaoImpl daoValuation = new RiskTypeDaoImpl();
		riskGrade = daoValuation.getRiskGrade();
		resultMap.put("riskGrade", riskGrade);
		resultMap.put("IRiskTypeTrxValue", riskTypeTrxValue);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
}
