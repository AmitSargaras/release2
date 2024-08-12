package com.integrosys.cms.ui.imageTag;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
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

public class CreateImageTagAddCommand extends AbstractCommand {
	
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
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
				{"ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "imageTagAddForm","com.integrosys.cms.ui.imageTag.ImageTagMapForm",FORM_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
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
		String unCheckId = map.get("unCheckId").toString();
		ArrayList sesionObImageTagAddList = (ArrayList)map.get("obImageTagAddList");
		
		
		String[] imageId2=null;
		String[] imageId3=null;
		String[] imageId4=null;
		String[] unCheckIdArray=null;
		//String[] imageId2 = commaSeparatedStringToStringArray(imageId);
		boolean isFirstPage=false;
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		ArrayList imageId1 = new ArrayList();
		if(selectedArrayMap!=null){
			DefaultLogger.debug(this, "selectedArrayMap.size()============CreateImageTagAddCommand===============1=================>"+selectedArrayMap.size());
			if(selectedArrayMap.size()!=0){
				
				
				isFirstPage=true;
			imageId1.addAll(selectedArrayMap.keySet());
			if(imageId1!=null){
				DefaultLogger.debug(this, "imageId1.size()============CreateImageTagAddCommand===============2=================>"+imageId1.size());
			}
			imageId3 = commaSeparatedStringToStringArray(imageId);
			for(int ab=0;ab<imageId3.length;ab++){
				if(sesionObImageTagAddList!=null){
					int index= Integer.parseInt(imageId3[ab]);
					OBImageUploadAdd uploadAdd=(OBImageUploadAdd) sesionObImageTagAddList.get(index-1);
					String imgId= String.valueOf(uploadAdd.getImgId());
				
					if(!imageId1.contains(imageId3[ab])){
					
					imageId1.add(imgId);
					}
				}
			}
			if(unCheckId!=null){
			unCheckIdArray = commaSeparatedStringToStringArray(unCheckId);	
			if(unCheckIdArray !=null){
			for(int abk=0;abk<unCheckIdArray.length;abk++){
				if(sesionObImageTagAddList!=null){
					int index2= Integer.parseInt(unCheckIdArray[abk]);
					
					OBImageUploadAdd uploadAdd2=(OBImageUploadAdd) sesionObImageTagAddList.get(index2-1);
					String imgId2= String.valueOf(uploadAdd2.getImgId());
					if(imageId1.contains(imgId2)){
						imageId1.remove(abk);
					}
				}
				
			}
			}
			}
			if(imageId1.get(0).equals("")){
				isFirstPage=false;
			}
			}
		}
		if(isFirstPage){
			
			DefaultLogger.debug(this, "imageId1.size()============CreateImageTagAddCommand===============3=================>"+imageId1.size());
			 imageId2 = new String[imageId1.size()] ;
			for(int y=0;y<imageId1.size();y++){
				imageId2[y]=(String) imageId1.get(y);
			}	
		}else{
			 imageId4 = commaSeparatedStringToStringArray(imageId);
			 if(imageId4!=null){
			 imageId2 = new String[imageId4.length] ;
			
			 for(int ab=0;ab<imageId4.length;ab++){
					
						if(sesionObImageTagAddList!=null){
							int index= Integer.parseInt(imageId4[ab]);
							OBImageUploadAdd uploadAdd=(OBImageUploadAdd) sesionObImageTagAddList.get(index-1);
							String imgId= String.valueOf(uploadAdd.getImgId());
						imageId2[ab]=imgId;
						}
					
				}
			 }
		}
		
		IImageTagDetails imageTagDetails = (IImageTagDetails) map
				.get("ImageTagMapObj");
		IImageTagMap imageTagMapvalues = new OBImageTagMap();
		IImageTagTrxValue trxValueIn = new OBImageTagTrxValue();
		IImageTagTrxValue trxValueOut = new OBImageTagTrxValue();
		
		try {
			
			if(imageId2!=null){
				DefaultLogger.debug(this, "imageId2.length============CreateImageTagAddCommand===============4====== final size ===========>"+imageId2.length);
			}
			IImageTagDetails existingTagDetails=getImageTagProxyManager().getExistingImageTag(imageTagDetails);
			
			if(existingTagDetails!=null){
				trxValueIn=getImageTagProxyManager().getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
				
				if(trxValueIn.getLimitProfileReferenceNumber()== null){
					String customerId = Long.toString(trxValueIn.getCustomerID());
					String camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(customerId);
					trxValueIn.setLimitProfileReferenceNumber(camId);
				}	
				
				if(!(trxValueIn.getStatus().equals("ACTIVE")))
				{
					result.put("wip", "wip");
				}else{
					trxValueOut = getImageTagProxyManager().makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
					imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
					for (int i = 0; i < imageId2.length; i++) {
						if (!(imageId2[i].trim().equals(""))) {
							imageTagMapvalues.setImageId(Long.parseLong(imageId2[i]));
							getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
						}
					}
					result.put("request.ITrxValue", trxValueOut);
				}

			}else{
				trxValueOut = getImageTagProxyManager().makerCreateImageTagDetails(ctx, imageTagDetails);
				
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
				for (int i = 0; i < imageId2.length; i++) {
					if (!(imageId2[i].trim().equals(""))) {
						imageTagMapvalues.setImageId(Long.parseLong(imageId2[i]));
						getImageTagProxyManager().createImageTagMap(imageTagMapvalues);
					}
				}
				result.put("request.ITrxValue", trxValueOut);
			}
			
			
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
