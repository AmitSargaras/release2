package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public abstract class AbstractRegionTrxOperation  extends CMSTrxOperation implements ITrxRouteOperation {
	
	private IRegionBusManager regionBusManager;

	private IRegionBusManager stagingRegionBusManager;
	
	private IRegionBusManager stagingRegionFileMapperIdBusManager;
    
    private IRegionBusManager regionFileMapperIdBusManager;
    
	/**
	 * @return the stagingRegionFileMapperIdBusManager
	 */
	public IRegionBusManager getStagingRegionFileMapperIdBusManager() {
		return stagingRegionFileMapperIdBusManager;
	}
	/**
	 * @param stagingRegionFileMapperIdBusManager the stagingRegionFileMapperIdBusManager to set
	 */
	public void setStagingRegionFileMapperIdBusManager(
			IRegionBusManager stagingRegionFileMapperIdBusManager) {
		this.stagingRegionFileMapperIdBusManager = stagingRegionFileMapperIdBusManager;
	}
	
	/**
	 * @return the regionFileMapperIdBusManager
	 */
	public IRegionBusManager getRegionFileMapperIdBusManager() {
		return regionFileMapperIdBusManager;
	}
	/**
	 * @param regionFileMapperIdBusManager the regionFileMapperIdBusManager to set
	 */
	public void setRegionFileMapperIdBusManager(
			IRegionBusManager regionFileMapperIdBusManager) {
		this.regionFileMapperIdBusManager = regionFileMapperIdBusManager;
	}
	public IRegionBusManager getRegionBusManager() {
		return regionBusManager;
	}
	public void setRegionBusManager(IRegionBusManager regionBusManager) {
		this.regionBusManager = regionBusManager;
	}
	public IRegionBusManager getStagingRegionBusManager() {
		return stagingRegionBusManager;
	}
	public void setStagingRegionBusManager(IRegionBusManager stagingRegionBusManager) {
		this.stagingRegionBusManager = stagingRegionBusManager;
	}
	
	protected IRegionTrxValue prepareTrxValue(IRegionTrxValue region) {
	        if (region != null) {
	            IRegion actual = region.getActualRegion();
	            IRegion staging = region.getStagingRegion();
	            if (actual != null) {
	            	region.setReferenceID(String.valueOf(actual.getIdRegion()));
	            } else {
	            	region.setReferenceID(null);
	            }
	            if (staging != null) {
	            	region.setStagingReferenceID(String.valueOf(staging.getIdRegion()));
	            } else {
	            	region.setStagingReferenceID(null);
	            }
	            return region;
	        }else{
	        	throw new  NoSuchGeographyException("ERROR-- Region is null");
	        }
	    }
	    /**
	     * 
	     * @param regionTrxValue
	     * @return IRegionTrxValue
	     * @throws TrxOperationException
	     */

    protected IRegionTrxValue updateRegionTrx(IRegionTrxValue regionTrxValue) throws TrxOperationException {
        try {
        	regionTrxValue = prepareTrxValue(regionTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(regionTrxValue);
            OBRegionTrxValue newValue = new OBRegionTrxValue(tempValue);
            newValue.setActualRegion(regionTrxValue.getActualRegion());
            newValue.setStagingRegion(regionTrxValue.getStagingRegion());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new NoSuchGeographyException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param IRegionTrxValue
     * @return IRegionTrxValue
     * @throws TrxOperationException
     */
    protected IRegionTrxValue createStagingRegion(IRegionTrxValue regionTrxValue) throws TrxOperationException {
        try {
            IRegion region = getStagingRegionBusManager().createRegion(regionTrxValue.getStagingRegion());
            regionTrxValue.setStagingRegion(region);
            regionTrxValue.setStagingReferenceID(String.valueOf(region.getIdRegion()));
            return regionTrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IRegionTrxValue
     * @throws TrxOperationException
     */

    protected IRegionTrxValue getRegionTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IRegionTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("The ITrxValue is not of type OBCRegionTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IRegionTrxValue
     * @throws TrxOperationException
     */

    protected IRegion mergeRegion(IRegion anOriginal, IRegion aCopy) throws TrxOperationException {
        aCopy.setIdRegion(anOriginal.getIdRegion());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IRegionTrxValue
     */

    protected ITrxResult prepareResult(IRegionTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IRegionTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IRegionTrxValue createTransaction(IRegionTrxValue value) throws TrxOperationException {
		OBRegionTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBRegionTrxValue(tempValue);
			newValue.setActualRegion(value.getActualRegion());
			newValue.setStagingRegion(value.getStagingRegion());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IRegionTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IRegionTrxValue updateTransaction(IRegionTrxValue value) throws TrxOperationException {
		OBRegionTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBRegionTrxValue(tempValue);
			newValue.setActualRegion(value.getActualRegion());
			newValue.setStagingRegion(value.getStagingRegion());
		}	
		return newValue;
	}
	
//------------------------------------File Insert---------------------------------------------
    
    protected IRegionTrxValue prepareInsertTrxValue(IRegionTrxValue countryTrxValue)throws TrxOperationException {
        if (countryTrxValue != null) {
            IFileMapperId actual = countryTrxValue.getFileMapperID();
            IFileMapperId staging = countryTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	countryTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	countryTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	countryTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	countryTrxValue.setStagingReferenceID(null);
            }
            return countryTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Region is null");
        }
    }
	
    
    
    protected IRegionTrxValue updateMasterInsertTrx(IRegionTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	countryTrxValue = prepareInsertTrxValue(countryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(countryTrxValue);
            OBRegionTrxValue newValue = new OBRegionTrxValue(tempValue);
            newValue.setFileMapperID(countryTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(countryTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IRegionTrxValue createStagingFileId(IRegionTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingRegionFileMapperIdBusManager().createFileId(countryTrxValue.getStagingFileMapperID());
        	countryTrxValue.setStagingFileMapperID(fileMapperID);
        	countryTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IRegionTrxValue insertActualRegion(IRegionTrxValue countryTrxValue) throws TrxOperationException {
        try {
            IRegion country = getStagingRegionFileMapperIdBusManager().insertRegion(countryTrxValue.getStagingRegion());
            countryTrxValue.setActualRegion(country);
            countryTrxValue.setReferenceID(String.valueOf(country.getIdRegion()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
