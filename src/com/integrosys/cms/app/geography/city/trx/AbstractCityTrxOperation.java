package com.integrosys.cms.app.geography.city.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityBusManager;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateBusManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public abstract class AbstractCityTrxOperation  extends CMSTrxOperation implements ITrxRouteOperation {
	
	private ICityBusManager cityBusManager;

	private ICityBusManager stagingCityBusManager;
	
	private ICityBusManager stagingCityFileMapperIdBusManager;
    
    private ICityBusManager cityFileMapperIdBusManager;
	
	/**
	 * @return the stagingCityFileMapperIdBusManager
	 */
	public ICityBusManager getStagingCityFileMapperIdBusManager() {
		return stagingCityFileMapperIdBusManager;
	}
	/**
	 * @param stagingCityFileMapperIdBusManager the stagingCityFileMapperIdBusManager to set
	 */
	public void setStagingCityFileMapperIdBusManager(
			ICityBusManager stagingCityFileMapperIdBusManager) {
		this.stagingCityFileMapperIdBusManager = stagingCityFileMapperIdBusManager;
	}
	/**
	 * @return the cityFileMapperIdBusManager
	 */
	public ICityBusManager getCityFileMapperIdBusManager() {
		return cityFileMapperIdBusManager;
	}
	/**
	 * @param cityFileMapperIdBusManager the cityFileMapperIdBusManager to set
	 */
	public void setCityFileMapperIdBusManager(
			ICityBusManager cityFileMapperIdBusManager) {
		this.cityFileMapperIdBusManager = cityFileMapperIdBusManager;
	}
	public ICityBusManager getCityBusManager() {
		return cityBusManager;
	}
	public void setCityBusManager(ICityBusManager cityBusManager) {
		this.cityBusManager = cityBusManager;
	}
	public ICityBusManager getStagingCityBusManager() {
		return stagingCityBusManager;
	}
	public void setStagingCityBusManager(ICityBusManager stagingCityBusManager) {
		this.stagingCityBusManager = stagingCityBusManager;
	}
	protected ICityTrxValue prepareTrxValue(ICityTrxValue city) {
	        if (city != null) {
	            ICity actual = city.getActualCity();
	            ICity staging = city.getStagingCity();
	            if (actual != null) {
	            	city.setReferenceID(String.valueOf(actual.getIdCity()));
	            } else {
	            	city.setReferenceID(null);
	            }
	            if (staging != null) {
	            	city.setStagingReferenceID(String.valueOf(staging.getIdCity()));
	            } else {
	            	city.setStagingReferenceID(null);
	            }
	            return city;
	        }else{
	        	throw new  NoSuchGeographyException("ERROR-- City is null");
	        }
	    }
	    /**
	     * 
	     * @param cityTrxValue
	     * @return ICityTrxValue
	     * @throws TrxOperationException
	     */

    protected ICityTrxValue updateCityTrx(ICityTrxValue cityTrxValue) throws TrxOperationException {
        try {
        	cityTrxValue = prepareTrxValue(cityTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(cityTrxValue);
            OBCityTrxValue newValue = new OBCityTrxValue(tempValue);
            newValue.setActualCity(cityTrxValue.getActualCity());
            newValue.setStagingCity(cityTrxValue.getStagingCity());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new NoSuchGeographyException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param ICityTrxValue
     * @return ICityTrxValue
     * @throws TrxOperationException
     */
    protected ICityTrxValue createStagingCity(ICityTrxValue cityTrxValue) throws TrxOperationException {
        try {
            ICity city = getStagingCityBusManager().createCity(cityTrxValue.getStagingCity());
            cityTrxValue.setStagingCity(city);
            cityTrxValue.setStagingReferenceID(String.valueOf(city.getIdCity()));
            return cityTrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ICityTrxValue
     * @throws TrxOperationException
     */

    protected ICityTrxValue getCityTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICityTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("The ITrxValue is not of type OBCCityTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return ICityTrxValue
     * @throws TrxOperationException
     */

    protected ICity mergeCity(ICity anOriginal, ICity aCopy) throws TrxOperationException {
        aCopy.setIdCity(anOriginal.getIdCity());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ICityTrxValue
     */

    protected ITrxResult prepareResult(ICityTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICityTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICityTrxValue createTransaction(ICityTrxValue value) throws TrxOperationException {
		OBCityTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBCityTrxValue(tempValue);
			newValue.setActualCity(value.getActualCity());
			newValue.setStagingCity(value.getStagingCity());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICityTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICityTrxValue updateTransaction(ICityTrxValue value) throws TrxOperationException {
		OBCityTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBCityTrxValue(tempValue);
			newValue.setActualCity(value.getActualCity());
			newValue.setStagingCity(value.getStagingCity());
		}	
		return newValue;
	}
//------------------------------------File Insert---------------------------------------------
    
    protected ICityTrxValue prepareInsertTrxValue(ICityTrxValue countryTrxValue)throws TrxOperationException {
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
        	throw new  TrxOperationException("ERROR-- City is null");
        }
    }
	
    
    
    protected ICityTrxValue updateMasterInsertTrx(ICityTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	countryTrxValue = prepareInsertTrxValue(countryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(countryTrxValue);
            OBCityTrxValue newValue = new OBCityTrxValue(tempValue);
            newValue.setFileMapperID(countryTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(countryTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected ICityTrxValue createStagingFileId(ICityTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingCityFileMapperIdBusManager().createFileId(countryTrxValue.getStagingFileMapperID());
        	countryTrxValue.setStagingFileMapperID(fileMapperID);
        	countryTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected ICityTrxValue insertActualCity(ICityTrxValue countryTrxValue) throws TrxOperationException {
        try {
            ICity country = getStagingCityFileMapperIdBusManager().insertCity(countryTrxValue.getStagingCity());
            countryTrxValue.setActualCity(country);
            countryTrxValue.setReferenceID(String.valueOf(country.getIdCity()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
