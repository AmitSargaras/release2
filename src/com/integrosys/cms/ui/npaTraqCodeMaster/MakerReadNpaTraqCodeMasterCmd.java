package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.proxy.INpaTraqCodeMasterProxyManager;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.OBNpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadNpaTraqCodeMasterCmd extends AbstractCommand implements ICommonEventConstant{

	private INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy;
	
	public INpaTraqCodeMasterProxyManager getNpaTraqCodeMasterProxy() {
		return npaTraqCodeMasterProxy;
	}

	public void setNpaTraqCodeMasterProxy(INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy) {
		this.npaTraqCodeMasterProxy = npaTraqCodeMasterProxy;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadNpaTraqCodeMasterCmd() {
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
			 {"npaTraqCode", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 { "TrxId", "java.lang.String", REQUEST_SCOPE },
			 {"event", "java.lang.String", REQUEST_SCOPE},
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
				{ "npaTraqCodeMasterObj", "com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster", FORM_SCOPE },
				{"INpaTraqCodeMasterTrxValue", "com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE}
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			INpaTraqCodeMaster npaTraqCodeMaster;
			INpaTraqCodeMasterTrxValue trxValue=null;
			String npaTraqCode=(String) (map.get("npaTraqCode"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			trxValue = (OBNpaTraqCodeMasterTrxValue) getNpaTraqCodeMasterProxy().getNpaTraqCodeMasterTrxValue(Long.parseLong(npaTraqCode));
			npaTraqCodeMaster = (OBNpaTraqCodeMaster) trxValue.getStagingNpaTraqCodeMaster();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			resultMap.put("INpaTraqCodeMasterTrxValue", trxValue);
			resultMap.put("npaTraqCodeMasterObj", npaTraqCodeMaster);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (NpaTraqCodeMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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
