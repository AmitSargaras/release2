package com.integrosys.cms.ui.image;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class ManageImageUploadDetailsCmd extends AbstractCommand implements ICommonEventConstant{
	
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
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
					{ "ImageUploadAddObj","com.integrosys.cms.app.image.bus.OBImageUploadAdd",FORM_SCOPE },
					{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE } 
				});
	}

	

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
				{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
				{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "facilityNameFromFCode", "java.lang.String", REQUEST_SCOPE },
				{ "camNumber", "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, "Enter in doExecute()");
		HashMap result = new HashMap();
		HashMap resultSet = new HashMap();
		ImageTagDaoImpl imageTagDaoImpl = new ImageTagDaoImpl();
		String facilityNameFromFCode = "";
		String camNumber = "";
		
		IImageUploadAdd imageUploadAdd = (IImageUploadAdd) map.get("ImageUploadAddObj");
		
		if (imageUploadAdd == null || imageUploadAdd.getCustId() == null) {
			imageUploadAdd = (IImageUploadAdd) map.get("ImageUploadAddObjSession");
		}
		String custId = imageUploadAdd.getCustId();
		DefaultLogger.debug(this, "=================Image Upload=====================");
//		DefaultLogger.debug(this, "==getCustId==================>"+imageUploadAdd.getCustId());
//		DefaultLogger.debug(this, "==getCustName================>"+imageUploadAdd.getCustName());
		DefaultLogger.debug(this, "==getCategory================>"+imageUploadAdd.getCategory());
		DefaultLogger.debug(this, "==getHasSubfolder============>"+imageUploadAdd.getHasSubfolder());
		DefaultLogger.debug(this, "==getSubfolderName===========>"+imageUploadAdd.getSubfolderName());
		DefaultLogger.debug(this, "=================Image Upload=====================");
		
		String imgCAM = ICMSConstant.IMAGE_CATEGORY_CAM;
		
		if(imgCAM.equals(imageUploadAdd.getCategory())) {
//		if("IMG_CATEGORY_CAM".equals(imageUploadAdd.getCategory())) {
			camNumber=imageTagDaoImpl.getCamNumber(imageUploadAdd.getCustId());
			result.put("camNumber", camNumber);
			imageUploadAdd.setHasCam(camNumber);
		}
		if(imageUploadAdd.getFacilityName() != null) {
			facilityNameFromFCode=imageTagDaoImpl.getFacilityNameFromFacilityCode(imageUploadAdd.getFacilityName(),custId);
			result.put("facilityNameFromFCode", facilityNameFromFCode);
		}
		result.put("ImageUploadAddObj", imageUploadAdd);
		result.put("ImageUploadAddObjSession", imageUploadAdd);

		/*try {
			List proxyMgr = (List) getImageUploadProxyManager().getCustImageList(imageUploadAdd);
			IImageUploadTrxValue trxValue=null;
			//trxValue = (OBImageUploadTrxValue) getImageUploadProxyManager().getImageUploadTrxValue(imageUploadAdd.getCustId());
			//DefaultLogger.debug(this, "trxValue@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=" + trxValue);
			result.put("obImageUploadAddList", proxyMgr);
			//result.put("imageTrxObj", trxValue);
			
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			CommandProcessingException cpe = new CommandProcessingException("failed to get customer image list");
			cpe.initCause(e);
			throw cpe;
		}*/
		resultSet.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultSet;
	}

}
