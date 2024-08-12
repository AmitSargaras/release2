package com.integrosys.cms.app.imageTag.proxy;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagBusManager;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.ImageTagTrxControllerFactory;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.imageTag.ImageTagException;
/**
*@author abhijit.rudrakshawar
*
*Implication of Interface Image tag Proxy
*/

public class ImageTagProxyManagerImpl implements IImageTagProxyManager {

	private IImageTagBusManager imageTagBusManager;
	private IImageTagBusManager stagingImageTagBusManager;
	private ITrxControllerFactory trxControllerFactory;

	public IImageTagBusManager getStagingImageTagBusManager() {
		return stagingImageTagBusManager;
	}

	public void setStagingImageTagBusManager(
			IImageTagBusManager stagingImageTagBusManager) {
		this.stagingImageTagBusManager = stagingImageTagBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IImageTagBusManager getImageTagBusManager() {
		return imageTagBusManager;
	}

	public void setImageTagBusManager(IImageTagBusManager imageTagBusManager) {
		this.imageTagBusManager = imageTagBusManager;
	}

	/**
	 * Method to create Image Tag Details
	 */
	public Long createImageTagDetails(IImageTagDetails imageTagMap)
	throws ImageTagException {
		return getImageTagBusManager().createImageTagDetails(imageTagMap);

	}
	/**
	 * Method to create Image Tag map
	 */
	public Long createImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException {
		return getImageTagBusManager().createImageTagMap(imageTagMap);

	}
	/**
	 * Method to create Image Tag Map for checker
	 */
	public Long checkerCreateImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException {
		return getImageTagBusManager().checkerCreateImageTagMap(imageTagMap);

	}
	/**
	 * Method to create Image Tag Map for checker
	 */
	public Long checkerApproveUpdateImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException {
		return getImageTagBusManager().checkerApproveUpdateImageTagMap(imageTagMap);
		
	}
	/**
	 * Method to retrive  Image list 
	 */

	public List getCustImageList(String custId,String category) throws ImageTagException {
		return getImageTagBusManager().getCustImageList(custId,category);
	}
	/**
	 * Method to retrive  Image list 
	 */
	
	public List getTagImageList(String tagId,String untaggedFilter) throws ImageTagException {
		return getImageTagBusManager().getTagImageList(tagId,untaggedFilter);
	}
	/**
	 * Method to retrive  Collateral id
	 */
	public List getCollateralId(String key) throws ImageTagException {
		return getImageTagBusManager().getCollateralId(key);
	}

	/**
	 * Method to create  Image Tag Details for maker
	 */
	public IImageTagTrxValue makerCreateImageTagDetails(
			ITrxContext anITrxContext, IImageTagDetails anICCImageTagDetails)
	throws ImageTagException, TrxParameterException,
	TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (anICCImageTagDetails == null) {
			throw new ImageTagException(
			"The ICCImageTagDetails to be Created is null !!!");
		}

		IImageTagTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCImageTagDetails);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_IMAGE_TAG);
		return operate(trxValue, param);
	}

	private IImageTagTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, IImageTagDetails anImageTagDetails) {
		IImageTagTrxValue ccImageTagTrxValue = null;
		if (anICMSTrxValue != null) {
			ccImageTagTrxValue = new OBImageTagTrxValue(anICMSTrxValue);
		} else {
			ccImageTagTrxValue = new OBImageTagTrxValue();
		}
		
		if(ccImageTagTrxValue.getLimitProfileReferenceNumber()== null){
			String cmsCollateralId = null;
			String camId = null;
			if(Long.toString(anITrxContext.getCustomer().getCustomerID()) != null 
					&& !(Long.toString(anITrxContext.getCustomer().getCustomerID())).equals("")){
				cmsCollateralId = Long.toString(anITrxContext.getCustomer().getCustomerID());
				camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(cmsCollateralId);
			}	
			ccImageTagTrxValue.setLimitProfileReferenceNumber(camId);
		}
		
		ccImageTagTrxValue = formulateTrxValue(anITrxContext,
				(IImageTagTrxValue) ccImageTagTrxValue);
		ccImageTagTrxValue.setStagingImageTagDetails(anImageTagDetails);
		return ccImageTagTrxValue;
	}

	private IImageTagTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IImageTagTrxValue anIImageTagTrxValue) {
		anIImageTagTrxValue.setTrxContext(anITrxContext);
		anIImageTagTrxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_TAG);
		return anIImageTagTrxValue;
	}

	private IImageTagTrxValue operate(IImageTagTrxValue anIImageTagTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws ImageTagException,
			TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIImageTagTrxValue,
				anOBCMSTrxParameter);
		return (IImageTagTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws ImageTagException,
			TrxParameterException, TransactionException {
		try {
			ImageTagTrxControllerFactory imgTagimpl = new ImageTagTrxControllerFactory();
			ITrxController controller = null;
			if(getTrxControllerFactory() == null) {
				controller = (ITrxController) imgTagimpl.getController(anICMSTrxValue, anOBCMSTrxParameter);
			}else { 
			controller = getTrxControllerFactory()
			.getController(anICMSTrxValue, anOBCMSTrxParameter);
			}
			Validate
			.notNull(controller,
					"'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue,
					anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}

		catch (Exception ex) {
			throw new ImageTagException(
			"ERROR--Cannot Get the Image Tag Controller.");
		}
	}

	/**
	 * Method to retrive  Image TRX by TRX id 
	 */
	public IImageTagTrxValue getImageTagByTrxID(String aTrxID)
	throws ImageTagException, TransactionException,
	CommandProcessingException {
		IImageTagTrxValue trxValue = new OBImageTagTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_TAG);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_IMAGE_TAG_ID);
		return operate(trxValue, param);
	}
	/**
	 * Method to retrieve  Image TRX by image tag id
	 */
	public IImageTagTrxValue getImageTagTrxByID(String imageTagId)
	throws ImageTagException, TransactionException,
	CommandProcessingException {
		IImageTagTrxValue trxValue = new OBImageTagTrxValue();
		trxValue.setReferenceID(String.valueOf(imageTagId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_TAG);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_IMAGE_TAG);
		return operate(trxValue, param);
	}
	
	/**
	 * Method to approve   Image tag 
	 */

	public IImageTagTrxValue checkerApproveImageTag(ITrxContext anITrxContext,
			IImageTagTrxValue anIImageTagTrxValue) throws ImageTagException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (anIImageTagTrxValue == null) {
			throw new ImageTagException(
			"The IImageTagTrxValue to be updated is null!!!");
		}
		anIImageTagTrxValue = formulateTrxValue(anITrxContext,
				anIImageTagTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_IMAGE_TAG);
		return operate(anIImageTagTrxValue, param);
	}

	/**
	 * Method to reject   Image tag 
	 */
	public IImageTagTrxValue checkerRejectImageTag(ITrxContext anITrxContext,
			IImageTagTrxValue anIImageTagTrxValue) throws ImageTagException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (anIImageTagTrxValue == null) {
			throw new ImageTagException(
			"The IImageTagTrxValue to be updated is null!!!");
		}
		anIImageTagTrxValue = formulateTrxValue(anITrxContext,
				anIImageTagTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_TAG);
		return operate(anIImageTagTrxValue, param);
	}

	/**
	 * Method to close rejected   Image tag 
	 */
	public IImageTagTrxValue makerCloseRejectedImageTag(
			ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue)
	throws ImageTagException, TrxParameterException,
	TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (anIImageTagTrxValue == null) {
			throw new ImageTagException(
			"The IImageTagTrxValue to be updated is null!!!");
		}
		anIImageTagTrxValue = formulateTrxValue(anITrxContext,
				anIImageTagTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_IMAGE_TAG);
		return operate(anIImageTagTrxValue, param);
	}
	/**
	 * Method to create  Image Tag Details for maker
	 */
	public IImageTagTrxValue makerUpdateImageTagDetails(ITrxContext anITrxContext, IImageTagTrxValue trxValueIn,IImageTagDetails imageTagDetails)
	throws ImageTagException, TrxParameterException,
	TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (imageTagDetails == null) {
			throw new ImageTagException(
			"The ICCImageTagDetails to be Created is null !!!");
		}
		
		IImageTagTrxValue trxValue = formulateTrxValue(anITrxContext, trxValueIn,imageTagDetails);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_IMAGE_TAG);
		return operate(trxValue, param);
	}
	/**
	 * Method to create  Image Tag Details for maker
	 */
	public IImageTagTrxValue makerUpdateRejectedImageTagDetails(ITrxContext anITrxContext, IImageTagTrxValue trxValueIn,IImageTagDetails imageTagDetails)
	throws ImageTagException, TrxParameterException,
	TransactionException {
		if (anITrxContext == null) {
			throw new ImageTagException("The ITrxContext is null!!!");
		}
		if (imageTagDetails == null) {
			throw new ImageTagException(
			"The ICCImageTagDetails to be Created is null !!!");
		}

		IImageTagTrxValue trxValue = formulateTrxValue(anITrxContext, trxValueIn,imageTagDetails);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_IMAGE_TAG);
		return operate(trxValue, param);
	}

	public IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails)
			throws ImageTagException {
		return getImageTagBusManager().getExistingImageTag(imageTagDetails);
	}
	
	/**
	 * Method to retrive  Image list 
	 */
	
	public List getStagingTagImageList(String tagId,String untaggedFilter) throws ImageTagException {
		return getStagingImageTagBusManager().getTagImageList(tagId,untaggedFilter);
	}

	public String getFromPageByImageTagMapByTagId(String tagId)
			throws ImageTagException {
		String fromPage=IImageTagConstants.TAG;
			List imageTagMapList = getStagingImageTagBusManager().getImageTagMapList(tagId);
			if(imageTagMapList!=null &&imageTagMapList.size()>0) {
				IImageTagMap obImageTagMap = (OBImageTagMap)imageTagMapList.get(0);
				if("Y".equals(obImageTagMap.getUntaggedStatus())){
					fromPage=IImageTagConstants.UNTAG;
				}
			}
		return fromPage;
	}

	public List getCustImageListByCriteria(IImageTagDetails tagDetails) throws ImageTagException {
		return getImageTagBusManager().getCustImageListByCriteria(tagDetails);
	}
	
	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	public List<String> getTagId(String custId){
		return getImageTagBusManager().getTagId(custId);
	}
	
	public List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList){
		return getImageTagBusManager().getTaggedImageId(imageIdList,tagIdList);
	}
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	public Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId){
		return getImageTagBusManager().getImageIdTaggedStatusMap(checklistDocItemId);
	}
	
	public List getImageIdDocDescMap(String checklistDocItemId,String checkListType){
		return getImageTagBusManager().getImageIdDocDescMap(checklistDocItemId,checkListType);
	}
}
