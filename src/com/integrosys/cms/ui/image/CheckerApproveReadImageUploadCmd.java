/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.image;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

/**
 *$Govind: Sahu $
 *Command for checker to read Image Upload Trx value
 */
public class CheckerApproveReadImageUploadCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IImageUploadProxyManager imageUploadProxyManager;
	
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

	/**
	 * Default Constructor
	 */
	public CheckerApproveReadImageUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "imageUploadObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", REQUEST_SCOPE },
				{"IImageUploadTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", REQUEST_SCOPE},
				{"IImageUploadTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE}
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
		DefaultLogger.debug(this, "Enter in doExecute() method");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IImageUploadAdd imageUpload;
			IImageUploadTrxValue trxValue=null;
			String imgTrxId=(String) (map.get("TrxId"));
			// function to get system bank Trx value
			trxValue = (OBImageUploadTrxValue) getImageUploadProxyManager().getApproveImageUploadByTrxID(imgTrxId);
			//   systemBank = (OBSystemBank) trxValue.getSystemBank();
			// function to get stging value of system bank trx value
			imageUpload = (OBImageUploadAdd) trxValue.getImageUploadAdd();
			DefaultLogger.debug(this, "imageUpload in Checker Read State==========================>"+imageUpload);
			resultMap.put("IImageUploadTrxValue", trxValue);
			resultMap.put("imageUploadObj", imageUpload);
			DefaultLogger.debug(this, "Exit from doExecute() method");
		} catch (SystemBankException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
