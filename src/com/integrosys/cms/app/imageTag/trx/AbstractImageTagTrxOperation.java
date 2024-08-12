package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.imageTag.bus.IImageTagBusManager;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract Image Tag Operation 
 */

public abstract class AbstractImageTagTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IImageTagBusManager imageTagBusManager;

    private IImageTagBusManager stagingImageTagBusManager;

 

	public IImageTagBusManager getImageTagBusManager() {
		return imageTagBusManager;
	}
	public void setImageTagBusManager(IImageTagBusManager imageTagBusManager) {
		this.imageTagBusManager = imageTagBusManager;
	}
	public IImageTagBusManager getStagingImageTagBusManager() {
		return stagingImageTagBusManager;
	}
	public void setStagingImageTagBusManager(
			IImageTagBusManager stagingImageTagBusManager) {
		this.stagingImageTagBusManager = stagingImageTagBusManager;
	}
	protected IImageTagTrxValue prepareTrxValue(IImageTagTrxValue imageTagTrxValue)throws TrxOperationException {
        if (imageTagTrxValue != null) {
            IImageTagDetails actual = imageTagTrxValue.getImageTagDetails();
            IImageTagDetails staging = imageTagTrxValue.getStagingImageTagDetails();
            if (actual != null) {
            	imageTagTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	imageTagTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	imageTagTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	imageTagTrxValue.setStagingReferenceID(null);
            }
            return imageTagTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- System Bank is null");
        }
    }
	/**
	 * 
	 * @param imageTagTrxValue
	 * @return IImageTagTrxValue
	 * @throws TrxOperationException
	 */

    protected IImageTagTrxValue updateImageTagTrx(IImageTagTrxValue  imageTagTrxValue) throws TrxOperationException {
        try {
        	imageTagTrxValue = prepareTrxValue(imageTagTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(imageTagTrxValue);
            OBImageTagTrxValue newValue = new OBImageTagTrxValue(tempValue);
            newValue.setImageTagDetails(imageTagTrxValue.getImageTagDetails());
            newValue.setStagingImageTagDetails(imageTagTrxValue.getStagingImageTagDetails());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param imageTagTrxValue
     * @return IImageTagTrxValue
     * @throws TrxOperationException
     */

    protected IImageTagTrxValue createStagingImageTagDetails(IImageTagTrxValue imageTagTrxValue) throws TrxOperationException {
        try {
            IImageTagDetails imageTagDetails = getStagingImageTagBusManager().createImageTagDetail(imageTagTrxValue.getStagingImageTagDetails());
            imageTagTrxValue.setStagingImageTagDetails(imageTagDetails);
            imageTagTrxValue.setStagingReferenceID(String.valueOf(imageTagDetails.getId()));
            return imageTagTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IImageTagTrxValue
     * @throws TrxOperationException
     */

    protected IImageTagTrxValue getImageTagTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IImageTagTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBImageTagTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IImageTagTrxValue
     * @throws TrxOperationException
     */

    protected IImageTagDetails mergeImageTag(IImageTagDetails anOriginal, IImageTagDetails aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IImageTagTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
