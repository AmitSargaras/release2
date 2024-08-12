package com.integrosys.cms.ui.image;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Govind: Sahu $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditImageUploadCmd extends AbstractCommand implements ICommonEventConstant {


	private IImageUploadProxyManager imageUploadProxyManager;




	/**
	 * Default Constructor
	 */
	public CheckerApproveEditImageUploadCmd() {
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
				{"IImageUploadTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE},
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
			IImageUploadTrxValue trxValueIn = (OBImageUploadTrxValue) map.get("IImageUploadTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated image upload Trx
			IImageUploadTrxValue trxValueOut = getImageUploadProxyManager().checkerApproveImageUpload(ctx, trxValueIn);
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

	/**
	 * @return the imageUploadProxyManager
	 */
	public IImageUploadProxyManager getImageUploadProxyManager() {
		return imageUploadProxyManager;
	}

	/**
	 * @param imageUploadProxyManager the imageUploadProxyManager to set
	 */
	public void setImageUploadProxyManager(
			IImageUploadProxyManager imageUploadProxyManager) {
		this.imageUploadProxyManager = imageUploadProxyManager;
	}

	
}



