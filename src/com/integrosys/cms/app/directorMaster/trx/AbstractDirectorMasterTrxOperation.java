package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Purpose: Used for defining attributes for director master
 * 
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public abstract class AbstractDirectorMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IDirectorMasterBusManager directorMasterBusManager;

    private IDirectorMasterBusManager stagingDirectorMasterBusManager;

 

    public IDirectorMasterBusManager getDirectorMasterBusManager() {
		return directorMasterBusManager;
	}

	public void setDirectorMasterBusManager(
			IDirectorMasterBusManager directorMasterBusManager) {
		this.directorMasterBusManager = directorMasterBusManager;
	}

	public IDirectorMasterBusManager getStagingDirectorMasterBusManager() {
		return stagingDirectorMasterBusManager;
	}

	public void setStagingDirectorMasterBusManager(
			IDirectorMasterBusManager stagingDirectorMasterBusManager) {
		this.stagingDirectorMasterBusManager = stagingDirectorMasterBusManager;
	}

	protected IDirectorMasterTrxValue prepareTrxValue(IDirectorMasterTrxValue directorMasterTrxValue)throws TrxOperationException {
        if (directorMasterTrxValue != null) {
            IDirectorMaster actual = directorMasterTrxValue.getDirectorMaster();
            IDirectorMaster staging = directorMasterTrxValue.getStagingDirectorMaster();
            if (actual != null) {
            	directorMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	directorMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	directorMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	directorMasterTrxValue.setStagingReferenceID(null);
            }
            return directorMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Director Master is null");
        }
    }
	/**
	 * 
	 * @param directorMasterTrxValue
	 * @return IDirectorMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected IDirectorMasterTrxValue updateDirectorMasterTrx(IDirectorMasterTrxValue directorMasterTrxValue) throws TrxOperationException {
        try {
        	directorMasterTrxValue = prepareTrxValue(directorMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(directorMasterTrxValue);
            OBDirectorMasterTrxValue newValue = new OBDirectorMasterTrxValue(tempValue);
            newValue.setDirectorMaster(directorMasterTrxValue.getDirectorMaster());
            newValue.setStagingDirectorMaster(directorMasterTrxValue.getStagingDirectorMaster());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param directorMasterTrxValue
     * @return IDirectorMasterTrxValue
     * @throws TrxOperationException
     */

    protected IDirectorMasterTrxValue createStagingDirectorMaster(IDirectorMasterTrxValue directorMasterTrxValue) throws TrxOperationException {
        try {
            IDirectorMaster directorMaster = getStagingDirectorMasterBusManager().createDirectorMaster(directorMasterTrxValue.getStagingDirectorMaster());
            directorMasterTrxValue.setStagingDirectorMaster(directorMaster);
            directorMasterTrxValue.setStagingReferenceID(String.valueOf(directorMaster.getId()));
            return directorMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param directorMasterTrxValue
     * @return IDirectorMasterTrxValue
     * @throws TrxOperationException
     */

    protected IDirectorMasterTrxValue disableStagingDirectorMaster(IDirectorMasterTrxValue directorMasterTrxValue) throws TrxOperationException {
        try {
            IDirectorMaster directorMaster = getStagingDirectorMasterBusManager().disableDirectorMaster(directorMasterTrxValue.getStagingDirectorMaster());
            directorMasterTrxValue.setStagingDirectorMaster(directorMaster);
            directorMasterTrxValue.setStagingReferenceID(String.valueOf(directorMaster.getId()));
            return directorMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param directorMasterTrxValue
     * @return IDirectorMasterTrxValue
     * @throws TrxOperationException
     */
    protected IDirectorMasterTrxValue enableStagingDirectorMaster(IDirectorMasterTrxValue directorMasterTrxValue) throws TrxOperationException {
        try {
            IDirectorMaster directorMaster = getStagingDirectorMasterBusManager().enableDirectorMaster(directorMasterTrxValue.getStagingDirectorMaster());
            directorMasterTrxValue.setStagingDirectorMaster(directorMaster);
            directorMasterTrxValue.setStagingReferenceID(String.valueOf(directorMaster.getId()));
            return directorMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IDirectorMasterTrxValue
     * @throws TrxOperationException
     */
  

    protected IDirectorMasterTrxValue getDirectorMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IDirectorMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCDirectorMasterTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IDirectorMasterTrxValue
     * @throws TrxOperationException
     */

    protected IDirectorMaster mergeDirectorMaster(IDirectorMaster anOriginal, IDirectorMaster aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IDirectorMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
