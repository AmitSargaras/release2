package com.integrosys.cms.ui.image;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.image.bus.IImageUploadDetailsMap;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.bus.OBImageUploadDetails;
import com.integrosys.cms.app.image.bus.OBImageUploadDetailsMap;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.trx.IImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadDetailsTrxValue;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class MakerCreateImageUploadDetailsCmd extends AbstractCommand implements ICommonEventConstant{
	
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


	public String[][] getParameterDescriptor() {
		return (new String[][] { { "imageTrxObj", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE }
				
		});
	}

	

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
				{
						"ImageTagAddObj",
						"com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",
						FORM_SCOPE },
				{ "imageTagAddForm",
						"com.integrosys.cms.ui.imageTag.ImageTagMapForm",
						FORM_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String imageId = map.get("imageId").toString();
		String[] imageId2 = commaSeparatedStringToStringArray(imageId);
		IImageUploadTrxValue imgTrxObj = (IImageUploadTrxValue) map.get("imageTrxObj");
		OBImageUploadAdd OBImageUploadAdd = (OBImageUploadAdd) map.get("imageUploadAddForm");
		List obImageUploadAddList = (java.util.ArrayList) map.get("obImageUploadAddList");
		OBImageUploadAdd = (OBImageUploadAdd) obImageUploadAddList.get(0);
		IImageUploadDetails imageUploadDetails= new OBImageUploadDetails();
		imageUploadDetails.setCustId(OBImageUploadAdd.getCustId());
		imageUploadDetails.setLegalName(OBImageUploadAdd.getCustName());
		
		IImageUploadDetailsMap imageUploadDetailsMapvalues = new OBImageUploadDetailsMap();
		IImageUploadDetailsTrxValue trxValueOut = new OBImageUploadDetailsTrxValue();
		try {
			trxValueOut = getImageUploadProxyManager().makerCreateImageUploadDetail(
					ctx, imageUploadDetails);
			imageUploadDetailsMapvalues.setUploadId(Long.parseLong(trxValueOut
					.getStagingReferenceID()));
			for (int i = 0; i < imageId2.length; i++) {
				if (!(imageId2[i].equals(""))) {
					imageUploadDetailsMapvalues.setImageId(Long.parseLong(imageId2[i]));
					getImageUploadProxyManager().createImageUploadMap(imageUploadDetailsMapvalues);
				}
			}
			result.put("request.ITrxValue", trxValueOut);
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
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
