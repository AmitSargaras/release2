package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionBusManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public abstract class AbstractStateTrxOperation  extends CMSTrxOperation implements ITrxRouteOperation {
	
	private IStateBusManager stateBusManager;

	private IStateBusManager stagingStateBusManager;
	
	private IStateBusManager stagingStateFileMapperIdBusManager;
    
    private IStateBusManager stateFileMapperIdBusManager;
    
	/**
	 * @return the stagingStateFileMapperIdBusManager
	 */
	public IStateBusManager getStagingStateFileMapperIdBusManager() {
		return stagingStateFileMapperIdBusManager;
	}
	/**
	 * @param stagingStateFileMapperIdBusManager the stagingStateFileMapperIdBusManager to set
	 */
	public void setStagingStateFileMapperIdBusManager(
			IStateBusManager stagingStateFileMapperIdBusManager) {
		this.stagingStateFileMapperIdBusManager = stagingStateFileMapperIdBusManager;
	}
	/**
	 * @return the stateFileMapperIdBusManager
	 */
	public IStateBusManager getStateFileMapperIdBusManager() {
		return stateFileMapperIdBusManager;
	}
	/**
	 * @param stateFileMapperIdBusManager the stateFileMapperIdBusManager to set
	 */
	public void setStateFileMapperIdBusManager(
			IStateBusManager stateFileMapperIdBusManager) {
		this.stateFileMapperIdBusManager = stateFileMapperIdBusManager;
	}
	public IStateBusManager getStateBusManager() {
		return stateBusManager;
	}
	public void setStateBusManager(IStateBusManager stateBusManager) {
		this.stateBusManager = stateBusManager;
	}
	public IStateBusManager getStagingStateBusManager() {
		return stagingStateBusManager;
	}
	public void setStagingStateBusManager(IStateBusManager stagingStateBusManager) {
		this.stagingStateBusManager = stagingStateBusManager;
	}
	protected IStateTrxValue prepareTrxValue(IStateTrxValue state) {
	        if (state != null) {
	            IState actual = state.getActualState();
	            IState staging = state.getStagingState();
	            if (actual != null) {
	            	state.setReferenceID(String.valueOf(actual.getIdState()));
	            } else {
	            	state.setReferenceID(null);
	            }
	            if (staging != null) {
	            	state.setStagingReferenceID(String.valueOf(staging.getIdState()));
	            } else {
	            	state.setStagingReferenceID(null);
	            }
	            return state;
	        }else{
	        	throw new  NoSuchGeographyException("ERROR-- State is null");
	        }
	    }
	    /**
	     * 
	     * @param stateTrxValue
	     * @return IStateTrxValue
	     * @throws TrxOperationException
	     */

    protected IStateTrxValue updateStateTrx(IStateTrxValue stateTrxValue) throws TrxOperationException {
        try {
        	stateTrxValue = prepareTrxValue(stateTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(stateTrxValue);
            OBStateTrxValue newValue = new OBStateTrxValue(tempValue);
            newValue.setActualState(stateTrxValue.getActualState());
            newValue.setStagingState(stateTrxValue.getStagingState());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new NoSuchGeographyException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param IStateTrxValue
     * @return IStateTrxValue
     * @throws TrxOperationException
     */
    protected IStateTrxValue createStagingState(IStateTrxValue stateTrxValue) throws TrxOperationException {
        try {
            IState state = getStagingStateBusManager().createState(stateTrxValue.getStagingState());
            stateTrxValue.setStagingState(state);
            stateTrxValue.setStagingReferenceID(String.valueOf(state.getIdState()));
            return stateTrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IStateTrxValue
     * @throws TrxOperationException
     */

    protected IStateTrxValue getStateTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IStateTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchGeographyException("The ITrxValue is not of type OBCStateTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IStateTrxValue
     * @throws TrxOperationException
     */

    protected IState mergeState(IState anOriginal, IState aCopy) throws TrxOperationException {
        aCopy.setIdState(anOriginal.getIdState());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IStateTrxValue
     */

    protected ITrxResult prepareResult(IStateTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IStateTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IStateTrxValue createTransaction(IStateTrxValue value) throws TrxOperationException {
		OBStateTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBStateTrxValue(tempValue);
			newValue.setActualState(value.getActualState());
			newValue.setStagingState(value.getStagingState());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IStateTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IStateTrxValue updateTransaction(IStateTrxValue value) throws TrxOperationException {
		OBStateTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBStateTrxValue(tempValue);
			newValue.setActualState(value.getActualState());
			newValue.setStagingState(value.getStagingState());
		}	
		return newValue;
	}
	
//------------------------------------File Insert---------------------------------------------
    
    protected IStateTrxValue prepareInsertTrxValue(IStateTrxValue countryTrxValue)throws TrxOperationException {
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
        	throw new  TrxOperationException("ERROR-- State is null");
        }
    }
	
    
    
    protected IStateTrxValue updateMasterInsertTrx(IStateTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	countryTrxValue = prepareInsertTrxValue(countryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(countryTrxValue);
            OBStateTrxValue newValue = new OBStateTrxValue(tempValue);
            newValue.setFileMapperID(countryTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(countryTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IStateTrxValue createStagingFileId(IStateTrxValue countryTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingStateFileMapperIdBusManager().createFileId(countryTrxValue.getStagingFileMapperID());
        	countryTrxValue.setStagingFileMapperID(fileMapperID);
        	countryTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IStateTrxValue insertActualState(IStateTrxValue countryTrxValue) throws TrxOperationException {
        try {
            IState country = getStagingStateFileMapperIdBusManager().insertState(countryTrxValue.getStagingState());
            countryTrxValue.setActualState(country);
            countryTrxValue.setReferenceID(String.valueOf(country.getIdState()));
            return countryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
