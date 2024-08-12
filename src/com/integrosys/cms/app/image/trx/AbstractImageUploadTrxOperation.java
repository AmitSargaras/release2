/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/AbstractCollateralTrxOperation.java,v 1.25 2006/10/10 08:04:36 jzhan Exp $
 */
package com.integrosys.cms.app.image.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.IImageUploadBusManager;
import com.integrosys.cms.app.image.bus.IImageUploadDao;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.app.image.bus.ImageUploadReplicationUtils;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * Abstract class that contain methods that is common among the set of
 * collateral trx operations.
 * 
 * @author $Govind: Sahu $<br>
 * @version $Revision: 0.0 $
 * @since $Date: 2011/03/17 10:04:36 $ Tag: $Name: $
 */
public abstract class AbstractImageUploadTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = -5494252274529767381L;

	private IImageUploadBusManager imageUploadBusManager;

	private IImageUploadBusManager stagingImageUploadBusManager;

	private IImageUploadDao imageUploadDao;

	
	

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
	 * @return the imageUploadDao
	 */
	public IImageUploadDao getImageUploadDao() {
		return imageUploadDao;
	}

	/**
	 * @param imageUploadDao the imageUploadDao to set
	 */
	public void setImageUploadDao(IImageUploadDao imageUploadDao) {
		this.imageUploadDao = imageUploadDao;
	}

	//public abstract ITrxResult performProcess(ITrxValue value) throws TrxOperationException;//Comment by govind sahu

	/**
	 * Create staging Image Upload record.
	 * 
	 * @param value is of type IImageUploadTrxValue
	 * @return Image Upload transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IImageUploadTrxValue createStagingImageUpload(IImageUploadTrxValue value) throws TrxOperationException {
		try {
			IImageUploadAdd imageUploadAdd = value.getStagingImageUploadAdd();
			if (value.getImageUploadAdd() != null) {
				imageUploadAdd.setImgId(value.getImageUploadAdd().getImgId());
			}

			imageUploadAdd = getStagingImageUploadBusManager().createStageImageUploadAdd(imageUploadAdd);
			value.setStagingImageUploadAdd(imageUploadAdd);			
		}
		catch (ImageUploadException e) {
			throw new TrxOperationException(
					"Failed to create staging image upload using staging image upload", e);
		} catch (TrxParameterException e) {
			throw new TrxOperationException(
					"Failed to create staging image upload using staging image upload", e);
		} catch (TransactionException e) {
			throw new TrxOperationException(
					"Failed to create staging image upload using staging image upload", e);
		}
		return value;
	}
	
	
	/**
	 * Create actual Image Upload record.
	 * 
	 * @param value is of type IImageUploadTrxValue
	 * @return Image Upload transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IImageUploadTrxValue createActualImageUploadAdd(IImageUploadTrxValue trxValue) throws TrxOperationException {
		try {
			 IImageUploadAdd staging = trxValue.getStagingImageUploadAdd();
	         IImageUploadAdd replicatedImageUpload = trxValue.getStagingImageUploadAdd();
	         replicatedImageUpload = ImageUploadReplicationUtils.replicateImageUploadForCreateStagingCopy(staging);
	         replicatedImageUpload = (IImageUploadAdd) getImageUploadBusManager().createActualImageUploadAdd(replicatedImageUpload);
	         trxValue.setStagingImageUploadAdd(staging);	
	         trxValue.setImageUploadAdd(replicatedImageUpload);			
		}
		catch (ImageUploadException e) {
			throw new TrxOperationException(
					"Failed to create staging image upload using staging image upload", e);
		}
		return trxValue;
	}
	
	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IImageUploadTrxValue updateTransaction(IImageUploadTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);

		ICMSTrxValue tempValue = super.updateTransaction(value);
		OBImageUploadTrxValue newValue = new OBImageUploadTrxValue(tempValue);
		newValue.setImageUploadAdd(value.getImageUploadAdd());
		newValue.setStagingImageUploadAdd(value.getStagingImageUploadAdd());
		return newValue;

	}
	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IImageUploadTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IImageUploadTrxValue createTransaction(IImageUploadTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBImageUploadTrxValue newValue = new OBImageUploadTrxValue(tempValue);
		newValue.setImageUploadAdd(value.getImageUploadAdd());
		newValue.setStagingImageUploadAdd(value.getStagingImageUploadAdd());
		return newValue;
	}
	
	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IImageUploadTrxValue
	 * @return imageUpload transaction value
	 */
	private IImageUploadTrxValue prepareTrxValue(IImageUploadTrxValue value) {
		if (value != null) {
			IImageUploadAdd actual = value.getImageUploadAdd();
			IImageUploadAdd staging = value.getStagingImageUploadAdd();

			String actualImageUploadAddId = (actual != null) ? String.valueOf(actual.getImgId()) : null;
			String stagingImageUploadAddId = (staging != null)
					&& (staging.getImgId() != ICMSConstant.LONG_MIN_VALUE) ? String.valueOf(staging
					.getImgId()) : null;

			value.setReferenceID(actualImageUploadAddId);
			value.setStagingReferenceID(stagingImageUploadAddId);
		}
		return value;
	}
	
	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IImageUploadTrxValue value) {
		OBImageUploadAddTrxResult result = new OBImageUploadAddTrxResult();
		result.setTrxValue(value);
		return result;
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

	public IImageUploadAdd getImageUploadById(long id) throws ImageUploadException{
		if(id!=0){
		return  getImageUploadDao().getImageUploadById(id);
		}else{
			throw new ImageUploadException("ERROR-- Key for Object Retrival is null.");
		}
	}
	 /**
     * 
     * @param anITrxValue
     * @return IImageUploadTrxValue
     * @throws TrxOperationException
     */

    protected IImageUploadTrxValue getImageUploadTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IImageUploadTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new SystemBankException("The ITrxValue is not of type OBImageUploadTrxValue: " + ex.toString());
        }
    }
    
    
    protected IImageUploadDetailsTrxValue getImageUploadDetailsTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IImageUploadDetailsTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new SystemBankException("The ITrxValue is not of type OBImageUploadTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param IImageUploadTrxValue
     * @return IImageUploadTrxValue
     * @throws TrxOperationException
     */

    protected IImageUploadTrxValue updateImageUploadTrx(IImageUploadTrxValue imageUploadTrxValue) throws TrxOperationException {
        try {
        	imageUploadTrxValue = prepareTrxValue(imageUploadTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(imageUploadTrxValue);
            OBImageUploadTrxValue newValue = new OBImageUploadTrxValue(tempValue);
            newValue.setImageUploadAdd(imageUploadTrxValue.getImageUploadAdd());
            newValue.setStagingImageUploadAdd((imageUploadTrxValue.getStagingImageUploadAdd()));
            return newValue;
        }
        
        catch (Exception ex) {
            throw new SystemBankException("General Exception: " + ex.toString());
        }
    }
    
    
    protected IImageUploadDetailsTrxValue prepareTrxValue(IImageUploadDetailsTrxValue imageUploadDetailsTrxValue)throws TrxOperationException {
        if (imageUploadDetailsTrxValue != null) {
            IImageUploadDetails actual = imageUploadDetailsTrxValue.getImageUploadDetails();
            IImageUploadDetails staging = imageUploadDetailsTrxValue.getStagingImageUploadDetails();
            if (actual != null) {
            	imageUploadDetailsTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	imageUploadDetailsTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	imageUploadDetailsTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	imageUploadDetailsTrxValue.setStagingReferenceID(null);
            }
            return imageUploadDetailsTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- System Bank is null");
        }
    }
    
    protected ITrxResult prepareResult(IImageUploadDetailsTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    protected IImageUploadDetailsTrxValue createStagingImageUploadDetails(IImageUploadDetailsTrxValue imageUploadTrxValue) throws TrxOperationException {
        try {
            IImageUploadDetails imageUploadDetails = getStagingImageUploadBusManager().createImageUploadDetail(imageUploadTrxValue.getStagingImageUploadDetails());
            imageUploadTrxValue.setStagingImageUploadDetails(imageUploadDetails);
            imageUploadTrxValue.setStagingReferenceID(String.valueOf(imageUploadDetails.getId()));
            return imageUploadTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    protected IImageUploadDetailsTrxValue updateImageUploadDetailsTrx(IImageUploadDetailsTrxValue  imageUploadDetailsTrxValue) throws TrxOperationException {
        try {
        	imageUploadDetailsTrxValue = prepareTrxValue(imageUploadDetailsTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(imageUploadDetailsTrxValue);
            OBImageUploadDetailsTrxValue newValue = new OBImageUploadDetailsTrxValue(tempValue);
            newValue.setImageUploadDetails(imageUploadDetailsTrxValue.getImageUploadDetails());
            newValue.setStagingImageUploadDetails(imageUploadDetailsTrxValue.getStagingImageUploadDetails());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
}