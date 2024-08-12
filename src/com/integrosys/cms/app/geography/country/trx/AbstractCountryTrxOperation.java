package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryBusManager;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageBusManager;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoverage.trx.OBInsuranceCoverageTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

public abstract class AbstractCountryTrxOperation  extends CMSTrxOperation implements ITrxRouteOperation {
	
	private ICountryBusManager countryBusManager;

	private ICountryBusManager stagingCountryBusManager;
	
	private ICountryBusManager stagingCountryFileMapperIdBusManager;
    
    private ICountryBusManager countryFileMapperIdBusManager;
    
	
	/**
	 * @return the stagingCountryFileMapperIdBusManager
	 */
	public ICountryBusManager getStagingCountryFileMapperIdBusManager() {
		return stagingCountryFileMapperIdBusManager;
	}
	/**
	 * @param stagingCountryFileMapperIdBusManager the stagingCountryFileMapperIdBusManager to set
	 */
	public void setStagingCountryFileMapperIdBusManager(
			ICountryBusManager stagingCountryFileMapperIdBusManager) {
		this.stagingCountryFileMapperIdBusManager = stagingCountryFileMapperIdBusManager;
	}
	/**
	 * @return the countryFileMapperIdBusManager
	 */
	public ICountryBusManager getCountryFileMapperIdBusManager() {
		return countryFileMapperIdBusManager;
	}
	/**
	 * @param countryFileMapperIdBusManager the countryFileMapperIdBusManager to set
	 */
	public void setCountryFileMapperIdBusManager(
			ICountryBusManager countryFileMapperIdBusManager) {
		this.countryFileMapperIdBusManager = countryFileMapperIdBusManager;
	}
	public ICountryBusManager getCountryBusManager() {
		return countryBusManager;
	}
	public void setCountryBusManager(ICountryBusManager countryBusManager) {
		this.countryBusManager = countryBusManager;
	}
	public ICountryBusManager getStagingCountryBusManager() {
		return stagingCountryBusManager;
	}
	public void setStagingCountryBusManager(
			ICountryBusManager stagingCountryBusManager) {
		this.stagingCountryBusManager = stagingCountryBusManager;
	}
	protected ICountryTrxValue prepareTrxValue(ICountryTrxValue country) {
	        if (country != null) {
	            ICountry actual = country.getActualCountry();
	            ICountry staging = country.getStagingCountry();
	            if (actual != null) {
	            	country.setReferenceID(String.valueOf(actual.getIdCountry()));
	            } else {
	            	country.setReferenceID(null);
	            }
	            if (staging != null) {
	            	country.setStagingReferenceID(String.valueOf(staging.getIdCountry()));
	            } else {
	            	country.setStagingReferenceID(null);
	            }
	            return country;
	        }else{
	        	throw new  NoSuchGeographyException("ERROR-- Other Bank is null");
	        }
	    }
	    /**
	     * 
	     * @param countryTrxValue
	     * @return ICountryTrxValue
	     * @throws TrxOperationException
	     */

    protected ICountryTrxValue updateCountryTrx(ICountryTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	countryTrxValue = prepareTrxValue(countryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(countryTrxValue);
            OBCountryTrxValue newValue = new OBCountryTrxValue(tempValue);
            newValue.setActualCountry(countryTrxValue.getActualCountry());
            newValue.setStagingCountry(countryTrxValue.getStagingCountry());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new NoSuchGeographyException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param RelationshipTrxTrxValue
     * @return ICountryTrxValue
     * @throws TrxOperationException
     */
    protected ICountryTrxValue createStagingCountry(ICountryTrxValue countryTrxValue) throws TrxOperationException {
        try {
            ICountry country = getStagingCountryBusManager().createCountry(countryTrxValue.getStagingCountry());
            countryTrxValue.setStagingCountry(country);
            countryTrxValue.setStagingReferenceID(String.valueOf(country.getIdCountry()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ICountryTrxValue
     * @throws TrxOperationException
     */

    protected ICountryTrxValue getCountryTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICountryTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("The ITrxValue is not of type OBCCountryTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return ICountryTrxValue
     * @throws TrxOperationException
     */

    protected ICountry mergeCountry(ICountry anOriginal, ICountry aCopy) throws TrxOperationException {
        aCopy.setIdCountry(anOriginal.getIdCountry());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ICountryTrxValue
     */

    protected ITrxResult prepareResult(ICountryTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICountryTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICountryTrxValue createTransaction(ICountryTrxValue value) throws TrxOperationException {
		OBCountryTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBCountryTrxValue(tempValue);
			newValue.setActualCountry(value.getActualCountry());
			newValue.setStagingCountry(value.getStagingCountry());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICountryTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICountryTrxValue updateTransaction(ICountryTrxValue value) throws TrxOperationException {
		OBCountryTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBCountryTrxValue(tempValue);
			newValue.setActualCountry(value.getActualCountry());
			newValue.setStagingCountry(value.getStagingCountry());
		}	
		return newValue;
	}
	
//------------------------------------File Insert---------------------------------------------
    
    protected ICountryTrxValue prepareInsertTrxValue(ICountryTrxValue countryTrxValue)throws TrxOperationException {
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
        	throw new  TrxOperationException("ERROR-- Country is null");
        }
    }
	
    
    
    protected ICountryTrxValue updateMasterInsertTrx(ICountryTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	countryTrxValue = prepareInsertTrxValue(countryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(countryTrxValue);
            OBCountryTrxValue newValue = new OBCountryTrxValue(tempValue);
            newValue.setFileMapperID(countryTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(countryTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected ICountryTrxValue createStagingFileId(ICountryTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingCountryFileMapperIdBusManager().createFileId(countryTrxValue.getStagingFileMapperID());
        	countryTrxValue.setStagingFileMapperID(fileMapperID);
        	countryTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected ICountryTrxValue insertActualCountry(ICountryTrxValue countryTrxValue) throws TrxOperationException {
        try {
            ICountry country = getStagingCountryFileMapperIdBusManager().insertCountry(countryTrxValue.getStagingCountry());
            countryTrxValue.setActualCountry(country);
            countryTrxValue.setReferenceID(String.valueOf(country.getIdCountry()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
