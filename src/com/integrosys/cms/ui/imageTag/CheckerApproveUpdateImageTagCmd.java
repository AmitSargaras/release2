package com.integrosys.cms.ui.imageTag;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $ Command for checker to approve edit .
 */

public class CheckerApproveUpdateImageTagCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateImageTagCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE }, 
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
		return (new String[][] { { "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Image Tag Trx value
			IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map
					.get("IImageTagTrxValue");
			String imageId = map.get("imageId").toString();
			String[] imageIdArray = commaSeparatedStringToStringArray(imageId);

			IImageTagMap imageTagMapvalues = new OBImageTagMap();
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function to approve updated Image Tag Trx
			IImageTagTrxValue trxValueOut = getImageTagProxyManager().checkerApproveImageTag(ctx, trxValueIn);
			if (trxValueOut.getReferenceID() != null) {
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut
						.getReferenceID()));
			} else {
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut
						.getStagingReferenceID()));
			}
			for (int i = 0; i < imageIdArray.length; i++) {
				if (!(imageIdArray[i].equals(""))) {
					imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
					getImageTagProxyManager().checkerCreateImageTagMap(imageTagMapvalues);
				}
			}

			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (SystemBankException ex) {
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

	private String[] commaSeparatedStringToStringArray(String aString) {
		String[] splittArray = null;
		if (aString != null && !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("\\,");
		}
		return splittArray;
	}
}
