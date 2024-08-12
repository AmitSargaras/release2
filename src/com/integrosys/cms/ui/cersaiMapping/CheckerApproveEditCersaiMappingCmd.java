package com.integrosys.cms.ui.cersaiMapping;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingJdbcImpl;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditCersaiMappingCmd extends AbstractCommand implements ICommonEventConstant{
	
	private ICersaiMappingProxyManager cersaiMapping;

	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMapping;
	}

	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMapping) {
		this.cersaiMapping = cersaiMapping;
	}

	public CheckerApproveEditCersaiMappingCmd(){
		
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{"masterValueList", "java.util.HashMap", REQUEST_SCOPE },
				{"masterName", "java.lang.String", REQUEST_SCOPE}
		}
		);
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
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
		try {
			Date toDate=new Date();
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			// Component Trx value
			ICersaiMappingTrxValue trxValueIn = (OBCersaiMappingTrxValue) map.get("ICersaiMappingTrxValue");
			
			
			// CheckerApproveEditBaselCmd used as reference
			ICersaiMappingJdbc cersaiMappingJdbc=(ICersaiMappingJdbc)BeanHouse.get("cersaiMappingJdbc");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			ICersaiMappingTrxValue trxValueOut = getCersaiMappingProxy().checkerApproveCersaiMapping(ctx, trxValueIn);
			//ICersaiMapping[] masterValueList=(ICersaiMapping[])map.get("masterValueList");
			String stagingRefId=trxValueOut.getStagingReferenceID();
			ICersaiMapping[] masterValueList =cersaiMappingJdbc.fetchValueList(stagingRefId);
			
			OBCersaiMapping obMapping=(OBCersaiMapping)trxValueIn.getStagingCersaiMapping();
			String masterName=(String)obMapping.getMasterName();
			if(masterName==null) {
				masterName=cersaiMappingJdbc.getMasterName(stagingRefId);
			}
			StringBuffer sqlInsertQuery = new StringBuffer();
			for (int i = 0; i < masterValueList.length; i++) {
				masterValueList[i].getCersaiValue();
				masterValueList[i].getClimsValue();
				//TO_TIMESTAMP('2012-03-28 11:10:00.068','YYYY-MM-DD HH:MI:SS.FF')
				sqlInsertQuery.append("INSERT INTO CMS_SUB_CERSAI_MAPPING VALUES("+trxValueOut.getReferenceID()+",'"+masterValueList[i].getClimsValue()+"','"+masterValueList[i].getCersaiValue()
																						+"','"+masterName+"','"+obMapping.getStatus()+"',"+obMapping.getVersionTime()
																						+",'"+obMapping.getCreateBy()+"',TO_TIMESTAMP('"+obMapping.getCreationDate()+"','YYYY-MM-DD HH24:MI:SS.FF'),'"+obMapping.getLastUpdateBy()
																						+"',TO_TIMESTAMP('"+obMapping.getLastUpdateDate()+"','YYYY-MM-DD HH24:MI:SS.FF'))");
				cersaiMappingJdbc.insertDataIntoCersaiActual(sqlInsertQuery);
				sqlInsertQuery.delete(0, sqlInsertQuery.length());
			}
			
			resultMap.put("request.ITrxValue", trxValueOut);
			
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


}
