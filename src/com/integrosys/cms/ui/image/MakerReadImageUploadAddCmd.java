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
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadTrxValue;


/**
 *@author govind.sahu $
 *Command for maker to read image upload Trx
 */
public class MakerReadImageUploadAddCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public MakerReadImageUploadAddCmd() {
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
				 {"imageId", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
				 {"TrxId", "java.lang.String", REQUEST_SCOPE}
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
				{ "imageUploadObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE },
				{ "imageUploadObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
				{"IImageUploadTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE},
				 {"imageId", "java.lang.String", REQUEST_SCOPE},
				 {"TrxId", "java.lang.String", REQUEST_SCOPE}
				
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
		DefaultLogger.debug(this, "Enter in doExecute");
		DefaultLogger.debug(this, "Has Map valu:@@@@@@@@@@@@@"+map);
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IImageUploadAdd imageUploadAdd;
			IImageUploadTrxValue trxValue=null;
			String imageId=(String) (map.get("imageId"));
			String trxID = (String) (map.get("TrxId"));
			//imageUploadAdd = getImageUploadProxyManager().getImageDetailByTrxID(trxID);
			//trxValue = (OBImageUploadTrxValue) getImageUploadProxyManager().getImageUploadTrxValue(Long.parseLong(imageId));
			trxValue = (OBImageUploadTrxValue) getImageUploadProxyManager().getImageUploadTrxValue(Long.parseLong(trxID));
			
			imageUploadAdd = (OBImageUploadAdd) trxValue.getImageUploadAdd();
			resultMap.put("IImageUploadTrxValue", trxValue);
			resultMap.put("imageUploadObj", imageUploadAdd);
		}catch (ImageUploadException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
