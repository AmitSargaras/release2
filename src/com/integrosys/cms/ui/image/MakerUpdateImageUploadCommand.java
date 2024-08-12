/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.image;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.ImageUploadCommand;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MakerUpdateImageUploadCommand extends ImageUploadCommand{
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "imageTrxObj", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE }
				
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		DefaultLogger.debug(this, "Map:"+map);
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String imageId = map.get("imageId").toString();
			String[] imageId2 = commaSeparatedStringToStringArray(imageId);
			IImageUploadTrxValue imgTrxObj = (IImageUploadTrxValue) map.get("imageTrxObj");
			OBImageUploadAdd oBImageUploadAdd = (OBImageUploadAdd) map.get("imageUploadAddForm");
			List obImageUploadAddList = (java.util.ArrayList) map.get("obImageUploadAddList");
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@imgTrxObj:" + imgTrxObj);
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@obImageUploadAddList:" + obImageUploadAddList.size());
			oBImageUploadAdd = (OBImageUploadAdd) obImageUploadAddList.get(0);
			IImageUploadTrxValue trxValueOut = getImageUploadProxyManager().createStageImageUploadAdd(ctx,imgTrxObj,oBImageUploadAdd);
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@trxValueOut:" + trxValueOut);
			resultMap.put("request.ITrxValue", trxValueOut);
		}
		catch (Exception ex) { 
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Exit from doExecute()");
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
