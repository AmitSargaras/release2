package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Dattatray Thorat $
 * Command for checker to approve edit .
 */

public class CheckerApproveInsertCityCmd extends AbstractCommand implements ICommonEventConstant {


	private ICityProxyManager cityProxy;
	
	/**
	 * @return the cityProxy
	 */
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	/**
	 * @param cityProxy the cityProxy to set
	 */
	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveInsertCityCmd() {
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
				{"ICityTrxValue", "com.integrosys.cms.app.geography.city.trx.ICityTrxValue", SERVICE_SCOPE},
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
			ICityTrxValue trxValueIn = (OBCityTrxValue) map.get("ICityTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated holiday Trx
			ICityTrxValue trxValueOut = getCityProxy().checkerApproveInsertCity(ctx, trxValueIn);
			
			//--------------getList--------------------
			String tempTrxValue = trxValueOut.getCurrentTrxHistoryID();
	        
	        
			List listId = getCityProxy().getFileMasterList(trxValueOut.getTransactionID());
			ICityTrxValue trxValue = null;
			for (int i = 0; i < listId.size(); i++) {
				OBFileMapperMaster mapList = (OBFileMapperMaster) listId.get(i);
    			 String regStage = Long.toString(mapList.getSysId());
    			 ICity refCity = getCityProxy().insertActualCity(regStage);
    			 
    			 trxValue = getCityProxy().checkerCreateCity(ctx, refCity, regStage);
    			     				
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



