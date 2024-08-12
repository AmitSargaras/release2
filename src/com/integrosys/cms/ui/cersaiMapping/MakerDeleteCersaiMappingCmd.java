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
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteCersaiMappingCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerDeleteCersaiMappingCmd() {
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
				{"ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", FORM_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
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
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICersaiMappingTrxValue trxValueIn = (OBCersaiMappingTrxValue) map.get("ICersaiMappingTrxValue");
		OBCersaiMapping cersaiMapping = (OBCersaiMapping) map.get("cersaiMappingObj");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		ICersaiMappingTrxValue trxValueOut = new OBCersaiMappingTrxValue();

		if( event.equals("maker_confirm_resubmit_delete") ){
			ctx.setRemarks(remarks);
			trxValueOut = getCersaiMappingProxy().makerEditRejectedCersaiMapping(ctx, trxValueIn,cersaiMapping);
			// trxValueOut.getstagingid, feedentries.cersai and climsvalue , stagingcersaimapping.getstatus ,stagingcersaimapping.createdby, stagingcersaimapping.lastupdatedby,mastername.
			ICersaiMappingJdbc cersaiMappingJdbc=(ICersaiMappingJdbc)BeanHouse.get("cersaiMappingJdbc");
			ICersaiMapping[] feedenteries = cersaiMapping.getFeedEntriesArr();
			
			
			
			
			String[] updatedCersaiValue = cersaiMapping.getUpdatedCersaiValue();
			ICersaiMapping[] feedEntriesArr = new ICersaiMapping[updatedCersaiValue.length];
			StringBuffer sqlInsertQuery = new StringBuffer();
			OBCersaiMapping oc=(OBCersaiMapping)trxValueIn.getStagingCersaiMapping();
			String mastername=oc.getMasterName();
			ICersaiMapping[] ValueList=cersaiMappingJdbc.getMasterListOfValues(mastername);
			
			String climVal1="";
			String climVal2="";
			String cersaiVal1="";
			String cersaiVal2="";
			int k=0;
			//ICersaiMapping[] masterListVal=null;
			for(int i=0;i<ValueList.length;i++) {
				
				climVal2=ValueList[i].getClimsValue();
				cersaiVal2=ValueList[i].getCersaiValue();
				
				for(int j=0;j< feedenteries.length;j++) {
					climVal1=feedenteries[j].getClimsValue();
					cersaiVal1=feedenteries[j].getCersaiValue();
				if(climVal2.equalsIgnoreCase(climVal1)) {
				if(!(cersaiVal1.equalsIgnoreCase(cersaiVal2))) {
					ValueList[k].setClimsValue(climVal2);
					ValueList[k].setCersaiValue(cersaiVal1);
					k++;
					}
				}
				}
				}
			int len=k;
			for (int i = 0; i < len; i++) {
				ValueList[i].getCersaiValue();
				ValueList[i].getClimsValue();
			
			sqlInsertQuery.append("INSERT INTO CMS_SUB_STAGE_CERSAI_MAPPING VALUES("+trxValueOut.getStagingReferenceID()+",'"+ValueList[i].getClimsValue()+"','"+ValueList[i].getCersaiValue()
																					+"','"+mastername+"','"+oc.getStatus()+"',"+oc.getVersionTime()
																					+",'"+oc.getCreateBy()+"','"+oc.getLastUpdateBy()
																					+"')");
			cersaiMappingJdbc.insertDataIntoCersaiStaging(sqlInsertQuery);
			sqlInsertQuery.delete(0, sqlInsertQuery.length());
		}

		}
		resultMap.put("request.ITrxValue", trxValueOut);
			
	} catch (CersaiMappingException ex) {
		DefaultLogger.debug(this, "got exception in doExecute" + ex);
		ex.printStackTrace();
		throw (new CommandProcessingException(ex.getMessage()));
	} catch (TransactionException e) {
		DefaultLogger.debug(this, "got exception in doExecute" + e);
		throw (new CommandProcessingException(e.getMessage()));
	} catch (Exception e) {
		DefaultLogger.debug(this, "got exception in doExecute" + e);
		e.printStackTrace();
		throw (new CommandProcessingException(e.getMessage()));
	}
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;
}
}
