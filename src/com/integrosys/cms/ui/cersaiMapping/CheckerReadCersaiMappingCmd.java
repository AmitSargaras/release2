package com.integrosys.cms.ui.cersaiMapping;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingJdbcImpl;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;

public class CheckerReadCersaiMappingCmd extends AbstractCommand implements ICommonEventConstant {

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
	public CheckerReadCersaiMappingCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
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
				{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", FORM_SCOPE },
				{"ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue", SERVICE_SCOPE},
				{"ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue", REQUEST_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
				{"masterName", "java.lang.String", SERVICE_SCOPE},
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
				{"masterName", "java.lang.String", REQUEST_SCOPE},
				{ "masterList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
				{ "masterList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "ListOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
				{ "ListOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE }
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICersaiMapping cersaiMapping;
			ICersaiMappingTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			String[] feedenteries=(String[]) map.get("feedenteries");
			// function to get CersaiMapping Trx value
			trxValue = (OBCersaiMappingTrxValue) getCersaiMappingProxy().getCersaiMappingByTrxID(branchCode);
			
			cersaiMapping = (OBCersaiMapping) trxValue.getStagingCersaiMapping();
			//CersaiMappingJdbcImpl cersaiMappingJdbc=new CersaiMappingJdbcImpl();
			CersaiMappingJdbcImpl cersaiMappingJdbc=(CersaiMappingJdbcImpl)BeanHouse.get("cersaiMappingJdbc");
			String stagingRefId=trxValue.getStagingReferenceID();
			ICersaiMapping[] masterValueList =cersaiMappingJdbc.fetchValueList(stagingRefId);
			ICersaiMapping[] masterList=cersaiMappingJdbc.fetchValueList(stagingRefId);
			
			String startIndex =(String) map.get("startIndex");
			if(startIndex == null){
				startIndex ="0"; 
			}
			String masterName=cersaiMapping.getMasterName();
			if(masterName==null) {
				masterName=cersaiMappingJdbc.getMasterName(stagingRefId);
				cersaiMapping.setMasterName(masterName);
			}
			String climVal="";
			String cersaiVal="";
			ICersaiMapping[] masterListVal=null;
			
			ICersaiMapping[] ListOfValue=cersaiMappingJdbc.getMasterListOfValues(masterName);
			for(int i=0;i<ListOfValue.length;i++) {
				climVal=ListOfValue[i].getClimsValue();
				
				for(int j=0;j < masterValueList.length;j++) {
					if(climVal.equalsIgnoreCase(masterValueList[j].getClimsValue())) {
						cersaiVal=masterValueList[j].getCersaiValue();
						ListOfValue[i].setCersaiValue(cersaiVal);
					}
				}
				
			}
			
			
			resultMap.put("masterList", masterList);
			resultMap.put("masterValueList", masterValueList);
			resultMap.put("ListOfValue", ListOfValue);
			resultMap.put("masterName", masterName);
			resultMap.put("ICersaiMappingTrxValue", trxValue);
			resultMap.put("cersaiMappingObj", cersaiMapping);
			resultMap.put("event", event);
		} catch (CersaiMappingException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
}
