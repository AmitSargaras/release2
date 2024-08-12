package com.integrosys.cms.ui.image;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.IImageUploadDetailsMap;
import com.integrosys.cms.app.image.bus.OBImageUploadDetailsMap;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.trx.IImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadTrxValue;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Title: CLIMS
 * Description: for Checker to approve the new created Property Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 30, 2008
 */

public class CheckerApproveCreateImageUploadCmd extends AbstractCommand implements ICommonEventConstant {
	
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
    public CheckerApproveCreateImageUploadCmd() {
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
        		{"IImageUploadDetailsTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadDetailsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
                { "imageId", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
       // HashMap exceptionMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Image Tag Trx value
			IImageUploadDetailsTrxValue trxValueIn = (OBImageUploadDetailsTrxValue) map
					.get("IImageUploadDetailsTrxValue");
			String imageId = map.get("imageId").toString();
			String[] imageId2 = commaSeparatedStringToStringArray(imageId);

			IImageUploadDetailsMap imageUploadDetailsMapvalues = new OBImageUploadDetailsMap();
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function to approve updated Image Tag Trx
			IImageUploadDetailsTrxValue trxValueOut = getImageUploadProxyManager()
					.checkerApproveImageUploadDetails(ctx, trxValueIn);
			if (trxValueOut.getReferenceID() != null) {
				imageUploadDetailsMapvalues.setUploadId(Long.parseLong(trxValueOut
						.getReferenceID()));
			} else {
				imageUploadDetailsMapvalues.setUploadId(Long.parseLong(trxValueOut
						.getStagingReferenceID()));
			}
			for (int i = 0; i < imageId2.length; i++) {
				if (!(imageId2[i].equals(""))) {
					imageUploadDetailsMapvalues.setImageId(Long.parseLong(imageId2[i]));
					Long aftertagId = getImageUploadProxyManager()
							.checkerCreateImageUploadDetailsMap(imageUploadDetailsMapvalues);
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
		String methodName = "commaSeparatedStringToStringArray";
		String[] splittArray = null;
		if (aString != null || !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("\\,");
		}
		return splittArray;
	}
    

}



