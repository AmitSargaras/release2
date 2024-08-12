package com.integrosys.cms.ui.cersaiMapping;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class SubmitCersaiMappingCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			 { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", FORM_SCOPE },
			

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
			
			{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", SERVICE_SCOPE },
			{ "masterName", "java.lang.String", SERVICE_SCOPE },
			{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
			
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	
	private ICersaiMappingProxyManager cersaiMappingProxy;
	
	

	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMappingProxy;
	}



	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMappingProxy) {
		this.cersaiMappingProxy = cersaiMappingProxy;
	}

	
	public SubmitCersaiMappingCommand() {
	}


	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
		ICersaiMappingProxyManager proxyMgr = getCersaiMappingProxy();
		ICersaiMapping cersaiMapping = (ICersaiMapping) map.get("cersaiMappingObj");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICersaiMappingJdbc cersaiMappingJdbc=(ICersaiMappingJdbc)BeanHouse.get("cersaiMappingJdbc");
		String masterName =(String)cersaiMapping.getMasterName();
		
		ICersaiMapping[] feedenteries = cersaiMapping.getFeedEntriesArr();
		String name=feedenteries[0].getClimsValue();
		if(masterName == null || masterName == "") {
			masterName=cersaiMappingJdbc.getNameOfMaster(name);
			cersaiMapping.setMasterName(masterName);
		}
		
		ICersaiMappingTrxValue trxValueIn = (OBCersaiMappingTrxValue) map.get("ICersaiMappingTrxValue");
		ICersaiMappingTrxValue trxValueOut = new OBCersaiMappingTrxValue();
		
		trxValueOut = getCersaiMappingProxy().makerSaveCersaiMapping(ctx,cersaiMapping);
		String[] updatedCersaiValue = cersaiMapping.getUpdatedCersaiValue();
		//String[] updatedClimsValue = cersaiMapping.getUpdatedClimsValue();
		ICersaiMapping[] feedEntriesArr = new ICersaiMapping[updatedCersaiValue.length];
		StringBuffer sqlInsertQuery = new StringBuffer();
		
		
//		ICersaiMapping[] masterValueForArray1 = (ICersaiMapping[]) map.get("masterValueOfValue");
//		ICersaiMapping[] masterValueForArray2 = (ICersaiMapping[]) map.get("com.integrosys.cms.ui.cersaiMapping.CersaiMappingAction.masterValueOfValue");
//		
//		int line=masterValueForArray2.length;
//		int line1=masterValueForArray1.length;
//		
//		
//		for(int i=0;i<line;i++) {
//			String st=masterValueForArray1[i].getClimsValue();
//			String st1=masterValueForArray1[i].getCersaiValue();
//		}
		
		for (int i = 0; i < updatedCersaiValue.length; i++) {
			feedenteries[i].getCersaiValue();
			feedenteries[i].getClimsValue();
			
			
			sqlInsertQuery.append("INSERT INTO CMS_SUB_STAGE_CERSAI_MAPPING VALUES("+cersaiMapping.getId()+",'"+feedenteries[i].getClimsValue()+"','"+feedenteries[i].getCersaiValue()
																					+"','"+cersaiMapping.getMasterName()+"','"+cersaiMapping.getStatus()+"',"+cersaiMapping.getVersionTime()
																					+",'"+cersaiMapping.getCreateBy()+"','"+cersaiMapping.getLastUpdateBy()
																					+"')");
			cersaiMappingJdbc.insertDataIntoCersaiStaging(sqlInsertQuery);
			sqlInsertQuery.delete(0, sqlInsertQuery.length());
		}
		
		result.put("masterName", masterName);
		result.put("feedenteries", feedenteries);
		result.put("request.ITrxValue", trxValueOut);
		}catch (CersaiMappingException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		result.put("masterListFlag","Y");
		
	temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}