package com.integrosys.cms.ui.imageTag;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
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

public class CreateImageUntagCommand extends AbstractCommand {
	
	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}


	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{ "imageTagTrxValue", "com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue", SERVICE_SCOPE },
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "tageedImageList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
				   });

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
		String[] imageIdArray = commaSeparatedStringToStringArray(imageId);
		IImageTagDetails imageTagDetails = (IImageTagDetails) map.get("ImageTagMapObj");
		IImageTagMap imageTagMapvalues = new OBImageTagMap();
		IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map.get("imageTagTrxValue");
		IImageTagTrxValue trxValueOut = new OBImageTagTrxValue();
		ArrayList sesionObImageTagAddList = (ArrayList)map.get("tageedImageList");
		
		
		try {
				trxValueOut = getImageTagProxyManager().makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
				
			imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
			
			//=========================
			//Retriving existing image list
			/*List imageList = new ArrayList();
			imageList = getImageTagProxyManager().getTagImageList(trxValueOut.getReferenceID());
			HashMap existingImages=new HashMap(); 
			if(imageList!=null && imageList.size()>0){
				for (int i = 0; i < imageList.size(); i++) {
					IImageUploadAdd imageDetail = (IImageUploadAdd) imageList.get(i);
					existingImages.put(Long.toString(imageDetail.getImgId()),imageDetail);
				} 
			}
			
			for (int i = 0; i < imageIdArray.length; i++) {
				if (!(imageIdArray[i].equals(""))) {
					if(existingImages.containsKey(imageIdArray[i])){
						imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
						imageTagMapvalues.setUntaggedStatus('Y');
					}else{
						imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
						imageTagMapvalues.setUntaggedStatus('N');
					}
					getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
				}
			}*/
			//=========================
			
		
			for (int i = 0; i < imageIdArray.length; i++) {
				if (!(imageIdArray[i].trim().equals(""))) {
					if(sesionObImageTagAddList!=null){
						int index= Integer.parseInt(imageIdArray[i]);
						OBImageUploadAdd uploadAdd=(OBImageUploadAdd) sesionObImageTagAddList.get(index-1);
						String imgId= String.valueOf(uploadAdd.getImgId());
						imageTagMapvalues.setImageId(uploadAdd.getImgId());
						imageTagMapvalues.setUntaggedStatus("Y");
						getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
						
					}
					
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
		String[] splittArray = null;
		if (aString != null && !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("\\,");
		}
		return splittArray;
	}
}
