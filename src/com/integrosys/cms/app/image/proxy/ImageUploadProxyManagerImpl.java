package com.integrosys.cms.app.image.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.IImageUploadBusManager;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.image.bus.IImageUploadDetailsMap;
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.app.image.trx.IImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.OBImageUploadTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.image.IImageUploadAdd;



/**
 * This interface defines the operations that are provided by a image 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02  $ Tag: $Name: $
 */
public class ImageUploadProxyManagerImpl implements IImageUploadProxyManager {

	private IImageUploadBusManager imageUploadBusManager;	
	private IImageUploadBusManager stagingImageUploadBusManager;
    private ITrxControllerFactory trxControllerFactory;
   
	
	public void createImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException {
		getImageUploadBusManager().createImageUploadAdd(imageData);
	}
	
	public void createImageUploadAdd(IImageUploadAdd imageData, boolean isActual) throws ImageUploadException {
		getImageUploadBusManager().createImageUploadAdd(imageData, isActual);
	}

	public List getCustImageList(IImageUploadAdd imageData) throws ImageUploadException {
		return (getImageUploadBusManager()).getCustImageList(imageData);
	}
	
	public List getStoredCustImageList(String custId) throws ImageUploadException {
		return (getImageUploadBusManager()).getStoredCustImageList(custId);
	}
	
	public IImageUploadAdd getImageDetailByTrxID(String trxId) throws ImageUploadException,TrxParameterException,TransactionException
	{
		return (getImageUploadBusManager()).getImageDetailByTrxIDDao(trxId);
		
	}
	
	
	
	
	
	
	public IImageUploadTrxValue createStageImageUploadAdd(ITrxContext anITrxContext,IImageUploadTrxValue imageTrxObj,IImageUploadAdd imageData) throws ImageUploadException, TrxParameterException, TransactionException
	{
	        if (anITrxContext == null) {
	            throw new ImageUploadException("The ITrxContext is null!!!");
	        }
				IImageUploadTrxValue trxValue = formulateTrxValue(anITrxContext, imageTrxObj, imageData);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_IMAGE_UPLOAD);
		       return operate(trxValue, param);
	}

	 public IImageUploadTrxValue makercreateStageImageUploadAdd(ITrxContext anITrxContext, IImageUploadTrxValue imageTrxObj, IImageUploadAdd imageData) throws ImageUploadException,TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new ImageUploadException("The ITrxContext is null!!!");
	        }
	        if (imageTrxObj == null) {
	            throw new ImageUploadException("The ImageUploadException to be updated is null !!!");
	        }
	        IImageUploadTrxValue trxValue = formulateTrxValue(anITrxContext, imageTrxObj, imageData);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_IMAGE_UPLOAD);
	        return operate(trxValue, param);
	    }
	 
	 
	 
	 
	


	/**
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIImageUpload
	  * @return IImageUploadTrxValue 
	  */
	 private IImageUploadTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IImageUploadAdd anImageUpload) {
		 IImageUploadTrxValue ccImageUploadTrxValue = null;
	        if (anICMSTrxValue != null) {
	        	ccImageUploadTrxValue = new OBImageUploadTrxValue(anICMSTrxValue);
	        } else {
	        	ccImageUploadTrxValue = new OBImageUploadTrxValue();
	        }
	        ccImageUploadTrxValue = formulateTrxValue(anITrxContext, (IImageUploadTrxValue) ccImageUploadTrxValue);
	        ccImageUploadTrxValue.setStagingImageUploadAdd(anImageUpload);
	        return ccImageUploadTrxValue;
	    }
	 /**
	  * @param anITrxContext
	  * @param anIImageUploadTrxValue
	  * @return IImageUploadTrxValue
	  */
	 private IImageUploadTrxValue formulateTrxValue(ITrxContext anITrxContext, IImageUploadTrxValue anImageUploadTrxValue) {
		 anImageUploadTrxValue.setTrxContext(anITrxContext);
		 anImageUploadTrxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
		 //comment by govind due to its commn use for all
		 //anImageUploadTrxValue.setTransactionSubType(ICMSConstant.STATE_PENDING_CREATE);
	        return anImageUploadTrxValue;
	    }
	 
	 /**
	  * @param anIImageUploadTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IImageUploadTrxValue
	  * @throws ImageUploadException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IImageUploadTrxValue operate(IImageUploadTrxValue anIImageUploadTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws ImageUploadException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIImageUploadTrxValue, anOBCMSTrxParameter);
	        return (IImageUploadTrxValue) result.getTrxValue();
	    }
	 /**
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws ImageUploadException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws ImageUploadException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (ImageUploadException ex) {
			 throw new ImageUploadException(ex.toString());
		 }
	 }

	 /**
		 * @return the imageUploadBusManager
		 */
		public IImageUploadBusManager getImageUploadBusManager() {
			return imageUploadBusManager;
		}

		/**
		 * @param imageUploadBusManager the imageUploadBusManager to set
		 */
		public void setImageUploadBusManager(
				IImageUploadBusManager imageUploadBusManager) {
			this.imageUploadBusManager = imageUploadBusManager;
		}
		
	 
	/**
	 * @return the trxControllerFactory
	 */
	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	/**
	 * @param trxControllerFactory the trxControllerFactory to set
	 */
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	/**
	 * @return the stagingImageUploadBusManager
	 */
	public IImageUploadBusManager getStagingImageUploadBusManager() {
		return stagingImageUploadBusManager;
	}

	/**
	 * @param stagingImageUploadBusManager the stagingImageUploadBusManager to set
	 */
	public void setStagingImageUploadBusManager(
			IImageUploadBusManager stagingImageUploadBusManager) {
		this.stagingImageUploadBusManager = stagingImageUploadBusManager;
	}

	 /**
	  * @return IImageUpload Value
	  * @param IImageUpload Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	  public IImageUploadTrxValue getImageUploadTrxValue(String aImageUpload) throws ImageUploadException,TrxParameterException,TransactionException {
//       if (aImageUpload == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
//           throw new ImageUploadException("Invalid ImageUploadID");
//       }
       IImageUploadTrxValue trxValue = new OBImageUploadTrxValue();
       trxValue.setReferenceID(aImageUpload);
       trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_IMAGE_UPLOAD);
       return operate(trxValue, param);
   }

	  
	  /**
		  * @return IImageUploadTrxValue Value
		  * @param long value 
		  * This method fetches the Proper trx value
		  * according to the value passed as parameter.
		  * 
		  */
			public IImageUploadTrxValue getImageUploadTrxValue(long aImageId) throws ImageUploadException,TrxParameterException,TransactionException {
	        if (aImageId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
	            throw new ImageUploadException("Invalid imageId");
	        }
	        IImageUploadTrxValue trxValue = new OBImageUploadTrxValue();
	        trxValue.setReferenceID(String.valueOf(aImageId));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_IMAGE_UPLOAD);
	        return operate(trxValue, param);
	    }
			
			 /**This Method is used for get staging trx value by trx id by checker
			  * @return ImageUploadTrxValue
			  * @param String Object 
			  * This method fetches the Proper trx value
			  * according to the Transaction Id passed as parameter.
			  * 
			  */
		  public IImageUploadDetailsTrxValue getImageUploadByTrxID(String trxID) throws ImageUploadException,TransactionException{
			  IImageUploadDetailsTrxValue trxValue = new OBImageUploadDetailsTrxValue();
		        trxValue.setTransactionID(String.valueOf(trxID));
		        trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_READ_IMAGE_UPLOAD);
		        return operate(trxValue, param);
		    }
		  /**This Method is used for get staging trx value by trx id by checker
			  * @return ImageUploadTrxValue
			  * @param String Object 
			  * This method fetches the Proper trx value
			  * according to the Transaction Id passed as parameter.
			  * 
			  */
		  public IImageUploadTrxValue getApproveImageUploadByTrxID(String trxID) throws ImageUploadException,TransactionException{
			  IImageUploadTrxValue trxValue = new OBImageUploadTrxValue();
		        trxValue.setTransactionID(String.valueOf(trxID));
		        trxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_READ_IMAGE_UPLOAD_ID);
		        return operate(trxValue, param);
		    }
		  
		  /**
		   * @return Image Upload Trx Value
		   * @param Trx object, Image Upload object,Image Upload  object
		   * 
		   * This method Approves the Object passed by Maker
		   */
		  
		  public IImageUploadTrxValue checkerApproveImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TransactionException {
		        if (anITrxContext == null) {
		            throw new ImageUploadException("The ITrxContext is null!!!");
		        }
		        if (anIImageUploadTrxValue == null) {
		            throw new ImageUploadException
		                    ("The IImageUploadTrxValue to be updated is null!!!");
		        }
		        anIImageUploadTrxValue = formulateTrxValue(anITrxContext, anIImageUploadTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_IMAGE_UPLOAD);
		        return operate(anIImageUploadTrxValue, param);
		    }
		  
		  /**
		   * @return Image Upload Trx Value
		   * @param Trx object, Image Upload Trx object,Image Upload object
		   * 
		   * This method Rejects the Object passed by Maker
		   */
		  public IImageUploadTrxValue checkerRejectImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new ImageUploadException("The ITrxContext is null!!!");
		        }
		        if (anIImageUploadTrxValue == null) {
		            throw new ImageUploadException("The IImageUploadTrxValue to be updated is null!!!");
		        }
		        anIImageUploadTrxValue = formulateTrxValue(anITrxContext, anIImageUploadTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_IMAGE_UPLOAD);
		        return operate(anIImageUploadTrxValue, param);
		    }
		  
		  /**
		   * @return Image Upload Trx Value
		   * @param Trx object, Image Upload Trx object,Image Upload object
		   * 
		   * This method Close the Object rejected by Checker
		   */
		  public IImageUploadTrxValue makerCloseRejectedImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new ImageUploadException("The ITrxContext is null!!!");
		        }
		        if (anIImageUploadTrxValue == null) {
		            throw new ImageUploadException("The IImageUploadTrxValue to be updated is null!!!");
		        }
		        anIImageUploadTrxValue = formulateTrxValue(anITrxContext, anIImageUploadTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_IMAGE_UPLOAD);
		        return operate(anIImageUploadTrxValue, param);
		    }
		  
		  /**
		   * @return Image Upload Trx Value
		   * @param Trx object, Image Upload object,Image Upload  object
		   * 
		   * This method Approves the Object passed by Maker
		   */
		  
		  public IImageUploadTrxValue checkerApproveCreateImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TransactionException {
		        if (anITrxContext == null) {
		            throw new ImageUploadException("The ITrxContext is null!!!");
		        }
		        if (anIImageUploadTrxValue == null) {
		            throw new ImageUploadException
		                    ("The IImageUploadTrxValue to be updated is null!!!");
		        }
		        anIImageUploadTrxValue = formulateTrxValue(anITrxContext, anIImageUploadTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_IMAGE_UPLOAD);
		        return operate(anIImageUploadTrxValue, param);
		    }
		  
		  
//		  /**This Method is used for create actual image upload check by checker
//			  * @return ImageUploadTrxValue
//			  * @param trxContext Object 
//			  * @param trxValue Object 
//			  * This method create actual image upload
//			  * 
//			  */
//		  public IImageUploadTrxValue checkerApproveCreateImageUpload(ITrxContext trxContext, IImageUploadTrxValue trxValue) throws ImageUploadException, TransactionException,CommandProcessingException 
//			{
//			  trxValue = (IImageUploadTrxValue) mapTrxValue(trxContext, trxValue, null);
//			  return (getImageUploadBusManager()).checkerApproveCreateImageUploadDao(trxValue);
//	}
//		  
//		  	/**
//			 * Prepares map trx context into ICMSTrxValue
//			 */
//			private ITrxValue mapTrxValue(ITrxContext trxContext, ITrxValue value, IImageUploadAdd imageUpload) throws UserException {
//				try {
//					value = TrxOperationHelper.mapTrxContext(trxContext, value);
//					ICMSTrxValue trxValue = (ICMSTrxValue) value;
//					trxValue.setTrxContext(trxContext);
//					OBImageUploadTrxValue ut = (OBImageUploadTrxValue) value;
//					if (imageUpload != null) {
//						ut.setCustomerName(imageUpload.getCustName());
//					}
//					else if (ut.getStagingImageUploadAdd() != null) {
//						ut.setCustomerName((ut.getCustomerName()));
//					}
//					else if (ut.getCustomerName() != null) {
//						ut.setCustomerName(ut.getStagingImageUploadAdd().getCustName());
//					}
//
//					return trxValue;
//				}
//				catch (TransactionException e) {
//					throw new UserException("Failed to map transaction context into transaction value", e);
//				}
//			}

		  
		  public IImageUploadDetailsTrxValue makerCreateImageUploadDetail(
					ITrxContext anITrxContext, IImageUploadDetails anICCImageUploadDetails)
			throws ImageUploadException, TrxParameterException,
			TransactionException {
				if (anITrxContext == null) {
					throw new ImageUploadException("The ITrxContext is null!!!");
				}
				if (anICCImageUploadDetails == null) {
					throw new ImageUploadException(
					"The ICCImageTagDetails to be Created is null !!!");
				}

				IImageUploadDetailsTrxValue trxValue = formulateTrxValue(anITrxContext, null,
						anICCImageUploadDetails);
				trxValue.setFromState("PENDING_CREATE");
				OBCMSTrxParameter param = new OBCMSTrxParameter();
				param.setAction(ICMSConstant.ACTION_MAKER_CREATE_IMAGE_UPLOAD);
				return operate(trxValue, param);
			}
		  
		  private IImageUploadDetailsTrxValue operate(IImageUploadDetailsTrxValue anIImageUploadDetailsTrxValue,
					OBCMSTrxParameter anOBCMSTrxParameter) throws ImageUploadException,
					TrxParameterException, TransactionException {
				ICMSTrxResult result = operateForResult(anIImageUploadDetailsTrxValue,
						anOBCMSTrxParameter);
				return (IImageUploadDetailsTrxValue) result.getTrxValue();
			}
		  private IImageUploadDetailsTrxValue formulateTrxValue(ITrxContext anITrxContext,
					ICMSTrxValue anICMSTrxValue, IImageUploadDetails anImageUploadDetailsDetails) {
			  IImageUploadDetailsTrxValue ccImageUploadDetailsTrxValue = null;
				if (anICMSTrxValue != null) {
					ccImageUploadDetailsTrxValue = new OBImageUploadDetailsTrxValue(anICMSTrxValue);
				} else {
					ccImageUploadDetailsTrxValue = new OBImageUploadDetailsTrxValue();
				}
				ccImageUploadDetailsTrxValue = formulateTrxValue(anITrxContext,
						(IImageUploadDetailsTrxValue) ccImageUploadDetailsTrxValue);
				ccImageUploadDetailsTrxValue.setStagingImageUploadDetails(anImageUploadDetailsDetails);
				return ccImageUploadDetailsTrxValue;
			}
		  

			private IImageUploadDetailsTrxValue formulateTrxValue(ITrxContext anITrxContext,
					IImageUploadDetailsTrxValue anIImageUploadDetailsTrxValue) {
				anIImageUploadDetailsTrxValue.setTrxContext(anITrxContext);
				anIImageUploadDetailsTrxValue.setTransactionType(ICMSConstant.INSTANCE_IMAGE_UPLOAD);
				return anIImageUploadDetailsTrxValue;
			}
			
			
			/**
			 * Method to create Image Upload map
			 */
			public void createImageUploadMap(IImageUploadDetailsMap imageUploadDetailsMap)
			throws ImageUploadException {
				 getImageUploadBusManager().createImageUploadMap(imageUploadDetailsMap);

			}
			
			/**
			 * Method to retrive  Image list 
			 */
			
			public List getUploadImageList(String uploadId) throws ImageUploadException {
				return getImageUploadBusManager().getUploadImageList(uploadId);
			}
			
			
			/**
			 * Method to approve   Image Upload 
			 */

			public IImageUploadDetailsTrxValue checkerApproveImageUploadDetails(ITrxContext anITrxContext,
					IImageUploadDetailsTrxValue anIImageUploadDetailsTrxValue) throws ImageUploadException,
					TrxParameterException, TransactionException {
				if (anITrxContext == null) {
					throw new ImageUploadException("The ITrxContext is null!!!");
				}
				if (anIImageUploadDetailsTrxValue == null) {
					throw new ImageUploadException(
					"The IImageTagTrxValue to be updated is null!!!");
				}
				anIImageUploadDetailsTrxValue = formulateTrxValue(anITrxContext,
						anIImageUploadDetailsTrxValue);
				OBCMSTrxParameter param = new OBCMSTrxParameter();
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_IMAGE_UPLOAD);
				return operate(anIImageUploadDetailsTrxValue, param);
			}

			/**
			 * Method to create Image Upload Map for checker
			 */
			public Long checkerCreateImageUploadDetailsMap(IImageUploadDetailsMap imageUploadDetailsMap)
			throws ImageUploadException {
				return getImageUploadBusManager().checkerCreateImageUploadDetailsMap(imageUploadDetailsMap);

			}

			/* (non-Javadoc)
			 * @see com.integrosys.cms.app.image.proxy.IImageUploadProxyManager#removeUploadedImage(com.integrosys.cms.ui.image.IImageUploadAdd)
			 */
			public void removeUploadedImage(IImageUploadAdd obImage)
					throws ImageUploadException {
				getImageUploadBusManager().removeUploadedImage(obImage);
				
			}
			
			public List listTempImagesUpload() throws ImageUploadException {
				return getImageUploadBusManager().listTempImagesUpload();
			}
		  
			public void updateTempImageUpload(IImageUploadAdd obImages) throws ImageUploadException {
				getImageUploadBusManager().updateTempImageUpload(obImages);
				
			}
		  
			public IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException {
				return getImageUploadBusManager().getTempImageUploadById(ImgId);
			}

			public List getSubFolderNameList(String custID, String catgory)
					throws ImageUploadException {
				return getImageUploadBusManager().getSubFolderNameList(custID,catgory);
			}

			public List getDocumentNameList(String custID, String catgory,
					String subfolderName) throws ImageUploadException {
				return getImageUploadBusManager().getDocumentNameList(custID,catgory,subfolderName);
			}
			
			public ArrayList getCustRemoveImageList(IImageUploadAdd imageData) throws ImageUploadException {
				return (getImageUploadBusManager()).getCustRemoveImageList(imageData);
			}	
			
			public ArrayList getImageIdWithTagList(ArrayList custImageList) throws ImageUploadException {
				return (getImageUploadBusManager()).getImageIdWithTagList(custImageList);
			}
			
			public String getSequenceNo() {
				return getImageUploadBusManager().getSequenceNo();
			}
			public List getFacilityDocumentNameList(String facCode) throws ImageUploadException {
				return getImageUploadBusManager().getFacilityDocumentNameList(facCode);
			}
			
			public List getSecurityDocumentNameList(String secCode) throws ImageUploadException {
				return getImageUploadBusManager().getSecurityDocumentNameList(secCode);
			}
			
}
