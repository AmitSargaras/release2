package com.integrosys.cms.ui.leiDateValidation;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationDao;
import com.integrosys.cms.app.leiDateValidation.proxy.ILeiDateValidationProxyManager;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerPrepareCreateLeiDateValidationCmd extends AbstractCommand implements
ICommonEventConstant{

	private ILeiDateValidationProxyManager leiDateValidationProxy;

	public ILeiDateValidationProxyManager getLeiDateValidationProxy() {
		return leiDateValidationProxy;
	}

	public void setLeiDateValidationProxy(ILeiDateValidationProxyManager leiDateValidationProxy) {
		this.leiDateValidationProxy = leiDateValidationProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateLeiDateValidationCmd() {
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
			{"ILeiDateValidationTrxValue", "com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue", SERVICE_SCOPE},
			{"LeiDateValidationPeriod", "java.lang.String", REQUEST_SCOPE},
			{"new", "java.lang.String", REQUEST_SCOPE},
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
		
		OBTrxContext obTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		
		OBLeiDateValidationTrxValue leiDateValidationTrxValue = new OBLeiDateValidationTrxValue();
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
//		Integer leiDateValidationPeriod = leiDateValidationDao.getLeiDateValidationPeriod(obTrxContext.getLimitProfile().getLimitProfileID()+"");
		String leiDateValidationPeriod = generalParamDao.getGenralParamValues("LEI_Period")+"";
		resultMap.put("ILeiDateValidationTrxValue", leiDateValidationTrxValue);
		resultMap.put("LeiDateValidationPeriod", leiDateValidationPeriod);
		resultMap.put("new", true);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
}
