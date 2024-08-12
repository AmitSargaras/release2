package com.integrosys.cms.ui.cersaiMapping;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {

	private ICersaiMappingProxyManager cersaiMappingProxy;

	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMappingProxy;
	}

	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMappingProxy) {
		this.cersaiMappingProxy = cersaiMappingProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public PaginationCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "masterName", "java.util.String", REQUEST_SCOPE },
			{"listOfMasterValue", "java.util.ArrayList", REQUEST_SCOPE},
			{ "ListOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
			{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE }
		});
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{"listOfMasterValue", "java.util.ArrayList", REQUEST_SCOPE},
			{"listOfMasterValue", "java.util.ArrayList", SERVICE_SCOPE},
			{ "masterName", "java.util.String", SERVICE_SCOPE },
			{ "masterName", "java.util.String", REQUEST_SCOPE },
			{ "ListOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
			{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
			{ "ListOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
			{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE }
		});
	}
	
	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try{
			String startIdx = (String) map.get("startIndex");
			SearchResult listOfMasterValue1 = new SearchResult();
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			ICersaiMapping masterValueList1=(ICersaiMapping) map.get("masterValueList");
			ICersaiMapping ListOfValue1=(ICersaiMapping) map.get("ListOfValue");
			String mastername = (String) map.get("masterName");
			ICersaiMapping cf=new OBCersaiMapping();
			String[] cVal=cf.getUpdatedCersaiValue();
			cVal=cf.getUpdatedClimsValue();
			ICersaiMapping cersaiMapping = (ICersaiMapping) map.get("cersaiMappingObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			OBCersaiMapping cersaiObj=(OBCersaiMapping) map.get("cersaiMappingObj");
			
			listOfMasterValue1 = getCersaiMappingProxy().getAllActualCersaiMapping(mastername);
			resultMap.put("listOfMasterValue", listOfMasterValue1);
			resultMap.put("startIndex", startIdx);
			
			
		} catch (CersaiMappingException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
