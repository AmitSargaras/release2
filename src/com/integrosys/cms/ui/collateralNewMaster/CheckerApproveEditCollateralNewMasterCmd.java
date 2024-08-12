package com.integrosys.cms.ui.collateralNewMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {


	private ICollateralNewMasterProxyManager collateralNewMasterProxy;


	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(
			ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditCollateralNewMasterCmd() {
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
				{"ICollateralNewMasterTrxValue", "com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{"newCollateralDescription", "java.lang.String", REQUEST_SCOPE},
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
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// CollateralNewMaster Trx value
			ICollateralNewMasterTrxValue trxValueIn = (OBCollateralNewMasterTrxValue) map.get("ICollateralNewMasterTrxValue");

			String newCollateralName = trxValueIn.getStagingCollateralNewMaster().getNewCollateralDescription();			
			String oldCollateralName = "";
			
			boolean isCollateralDescUnique = false;
			
			if( trxValueIn.getCollateralNewMaster() != null ){
				oldCollateralName  = trxValueIn.getCollateralNewMaster().getNewCollateralDescription();
			
				if(!newCollateralName.equals(oldCollateralName))
					isCollateralDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollateralName.trim());
				
				if(isCollateralDescUnique != false){
					exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collateral Description"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
			}
			else{			
				isCollateralDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollateralName);				
				if(isCollateralDescUnique != false){
					exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collateral Description"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated CollateralNewMaster Trx
			ICollateralNewMasterTrxValue trxValueOut = getCollateralNewMasterProxy().checkerApproveCollateralNewMaster(ctx, trxValueIn);
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



