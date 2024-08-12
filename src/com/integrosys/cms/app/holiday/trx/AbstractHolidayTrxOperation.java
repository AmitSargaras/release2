package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayBusManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract Holiday Operation 
 */

public abstract class AbstractHolidayTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IHolidayBusManager holidayBusManager;

    private IHolidayBusManager stagingHolidayBusManager;

    private IHolidayBusManager stagingHolidayFileMapperIdBusManager;
    
    private IHolidayBusManager holidayFileMapperIdBusManager;

    public IHolidayBusManager getHolidayBusManager() {
		return holidayBusManager;
	}

	public void setHolidayBusManager(
			IHolidayBusManager holidayBusManager) {
		this.holidayBusManager = holidayBusManager;
	}

	public IHolidayBusManager getStagingHolidayBusManager() {
		return stagingHolidayBusManager;
	}

	public void setStagingHolidayBusManager(
			IHolidayBusManager stagingHolidayBusManager) {
		this.stagingHolidayBusManager = stagingHolidayBusManager;
	}
	
	public IHolidayBusManager getStagingHolidayFileMapperIdBusManager() {
		return stagingHolidayFileMapperIdBusManager;
	}

	public void setStagingHolidayFileMapperIdBusManager(
			IHolidayBusManager stagingHolidayFileMapperIdBusManager) {
		this.stagingHolidayFileMapperIdBusManager = stagingHolidayFileMapperIdBusManager;
	}

	public IHolidayBusManager getHolidayFileMapperIdBusManager() {
		return holidayFileMapperIdBusManager;
	}

	public void setHolidayFileMapperIdBusManager(
			IHolidayBusManager holidayFileMapperIdBusManager) {
		this.holidayFileMapperIdBusManager = holidayFileMapperIdBusManager;
	}

	protected IHolidayTrxValue prepareTrxValue(IHolidayTrxValue holidayTrxValue)throws TrxOperationException {
        if (holidayTrxValue != null) {
            IHoliday actual = holidayTrxValue.getHoliday();
            IHoliday staging = holidayTrxValue.getStagingHoliday();
            if (actual != null) {
            	holidayTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	holidayTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	holidayTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	holidayTrxValue.setStagingReferenceID(null);
            }
            return holidayTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Holiday is null");
        }
    }
	/**
	 * 
	 * @param holidayTrxValue
	 * @return IHolidayTrxValue
	 * @throws TrxOperationException
	 */

    protected IHolidayTrxValue updateHolidayTrx(IHolidayTrxValue holidayTrxValue) throws TrxOperationException {
    	if(holidayTrxValue!=null){
    	try {
        	holidayTrxValue = prepareTrxValue(holidayTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(holidayTrxValue);
            OBHolidayTrxValue newValue = new OBHolidayTrxValue(tempValue);
            newValue.setHoliday(holidayTrxValue.getHoliday());
            newValue.setStagingHoliday(holidayTrxValue.getStagingHoliday());
            return newValue;
        }
        
        catch (HolidayException ex) {
            throw new HolidayException("General Exception: in update holiday  " );
        }
    	}else{
        	throw new HolidayException("Error : Error  while preparing result holiday in abstract trx operation");
        }
    }
    /**
     * 
     * @param holidayTrxValue
     * @return IHolidayTrxValue
     * @throws TrxOperationException
     */

    protected IHolidayTrxValue createStagingHoliday(IHolidayTrxValue holidayTrxValue) throws TrxOperationException {
    	if(holidayTrxValue!=null){
    	try {
            IHoliday holiday = getStagingHolidayBusManager().createHoliday(holidayTrxValue.getStagingHoliday());
            holidayTrxValue.setStagingHoliday(holiday);
            holidayTrxValue.setStagingReferenceID(String.valueOf(holiday.getId()));
            return holidayTrxValue;
        }
        catch (HolidayException e) {
            throw new HolidayException("Error : Error  while creating holiday in abstract trx operation");
        }catch (Exception ex) {
            throw new HolidayException("Error : Error  while creating holiday in abstract trx operation");
        }
    	}else{
        	throw new HolidayException("Error : Error  while preparing result holiday in abstract trx operation");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IHolidayTrxValue
     * @throws TrxOperationException
     */

    protected IHolidayTrxValue getHolidayTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (IHolidayTrxValue) anITrxValue;
        }
        catch (HolidayException e) {
            throw new HolidayException("The ITrxValue is not of type OBCHolidayTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new HolidayException("The ITrxValue is not of type OBCHolidayTrxValue: " + ex.toString());
        }
    	}else{
        	throw new HolidayException("Error : Error  while preparing result holiday in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IHolidayTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new HolidayException("Error : Error  while preparing result holiday in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param holidayTrxValue
     * @return IHolidayTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IHolidayTrxValue prepareInsertTrxValue(IHolidayTrxValue holidayTrxValue)throws TrxOperationException {
        if (holidayTrxValue != null) {
            IFileMapperId actual = holidayTrxValue.getFileMapperID();
            IFileMapperId staging = holidayTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	holidayTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	holidayTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	holidayTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	holidayTrxValue.setStagingReferenceID(null);
            }
            return holidayTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Holiday is null");
        }
    }
	
    
    
    protected IHolidayTrxValue updateMasterInsertTrx(IHolidayTrxValue holidayTrxValue) throws TrxOperationException {
        try {
        	holidayTrxValue = prepareInsertTrxValue(holidayTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(holidayTrxValue);
            OBHolidayTrxValue newValue = new OBHolidayTrxValue(tempValue);
            newValue.setFileMapperID(holidayTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(holidayTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IHolidayTrxValue createStagingFileId(IHolidayTrxValue holidayTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingHolidayFileMapperIdBusManager().createFileId(holidayTrxValue.getStagingFileMapperID());
        	holidayTrxValue.setStagingFileMapperID(fileMapperID);
        	holidayTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return holidayTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IHolidayTrxValue insertActualHoliday(IHolidayTrxValue holidayTrxValue) throws TrxOperationException {
        try {
            IHoliday holiday = getStagingHolidayFileMapperIdBusManager().insertHoliday(holidayTrxValue.getStagingHoliday());
            holidayTrxValue.setHoliday(holiday);
            holidayTrxValue.setReferenceID(String.valueOf(holiday.getId()));
            return holidayTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	
}
