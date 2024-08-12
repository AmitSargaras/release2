package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Dattatray Thorat $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertRegionCmd extends AbstractCommand implements ICommonEventConstant {


	private IRegionProxyManager regionProxy;
	
	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertRegionCmd() {
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
				{"IRegionTrxValue", "com.integrosys.cms.app.geography.region.trx.IRegionTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// System Bank Trx value
			IRegionTrxValue trxValueIn = (OBRegionTrxValue) map.get("IRegionTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated holiday Trx
			IRegionTrxValue trxValueOut = getRegionProxy().checkerApproveInsertRegion(ctx, trxValueIn);
			
			//--------------getList--------------------
			String tempTrxValue = trxValueOut.getCurrentTrxHistoryID();
	        
	        
			List listId = getRegionProxy().getFileMasterList(trxValueOut.getTransactionID());
			IRegionTrxValue trxValue = null;
			for (int i = 0; i < listId.size(); i++) {
				OBFileMapperMaster mapList = (OBFileMapperMaster) listId.get(i);
    			 String regStage = Long.toString(mapList.getSysId());
    			 IRegion refRegion = getRegionProxy().insertActualRegion(regStage);
    			 
    			 trxValue = getRegionProxy().checkerCreateRegion(ctx, refRegion, regStage);
    			     				
    		}			
			trxValue.setCurrentTrxHistoryID(tempTrxValue);

			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (SystemBankException ex) {
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



