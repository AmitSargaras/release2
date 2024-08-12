package com.integrosys.cms.ui.imageTag;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
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

public class EditImageTagCommand extends AbstractCommand {
	
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
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },						
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkedImagesMap", "java.util.HashMap", SERVICE_SCOPE },
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
		String unCheckId = map.get("unCheckId").toString();
		//String[] imageId2 = commaSeparatedStringToStringArray(imageId);
		HashMap checkedImagesMap = (HashMap) map.get("checkedImagesMap");
		ArrayList imageId1 = new ArrayList();
		String[] imageId3=null;
		String[] unCheckIdArray=null;
		if(checkedImagesMap!=null){
		imageId1.addAll(checkedImagesMap.keySet());
		imageId3 = commaSeparatedStringToStringArray(imageId);
		
		if(imageId3!=null){
		for(int ab=0;ab<imageId3.length;ab++){
			if(!imageId1.contains(imageId3[ab])){
				imageId1.add(imageId3[ab]);
			}
		}
		}
		if(unCheckId!=null){
			unCheckIdArray = commaSeparatedStringToStringArray(unCheckId);	
			if(unCheckIdArray !=null){
			for(int abk=0;abk<unCheckIdArray.length;abk++){
				if(imageId1.contains(unCheckIdArray[abk])){
					imageId1.remove(abk);
				}
			}
			}
			}
		
		}
		
	/*	HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap!=null){
		imageId1.addAll(selectedArrayMap.keySet());
		}*/
		
		
		String[] imageId2 = new String[imageId1.size()] ;
		for(int y=0;y<imageId1.size();y++){
			imageId2[y]=(String) imageId1.get(y);
		}
		IImageTagDetails imageTagDetails = (IImageTagDetails) map
				.get("ImageTagMapObj");
		
		/*String selectedArrayString=imageTagDetails.getSelectedArray();
		if(selectedArrayString!=null){
		String[] selected=selectedArrayString.split("-");
		if(selected!=null){
		for(int k=0;k<selected.length;k++){
		selectedArrayMap.put(selected[k], selected[k]);
		}
		}
		}*/
		IImageTagMap imageTagMapvalues = new OBImageTagMap();
		IImageTagTrxValue trxValueOut = new OBImageTagTrxValue();
		
		IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map.get("IImageTagTrxValue");
		try {
			
			if(trxValueIn.getLimitProfileReferenceNumber()== null){
				String customerId = Long.toString(trxValueIn.getCustomerID());
				String camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(customerId);
				trxValueIn.setLimitProfileReferenceNumber(camId);
			}
			
			trxValueOut = getImageTagProxyManager().makerUpdateRejectedImageTagDetails(ctx,trxValueIn, imageTagDetails);

			imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
			for (int i = 0; i < imageId2.length; i++) {
				if (!(imageId2[i].trim().equals(""))) {
					imageTagMapvalues.setImageId(Long.parseLong(imageId2[i]));
					getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
				}
			}
			/*if(imageId3!=null && imageId3.length>0){
			for (int i = 0; i < imageId3.length; i++) {
				if (!(imageId3[i].trim().equals(""))) {
					imageTagMapvalues.setImageId(Long.parseLong(imageId3[i]));
					getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
				}
			}
			}*/
			
			
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
			splittArray = aString.split("-");
		}
		return splittArray;
	}
}
