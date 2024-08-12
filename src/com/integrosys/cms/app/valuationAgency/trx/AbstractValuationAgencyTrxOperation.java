package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayBusManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgencyBusManager;

/**
 * Purpose: Used for defining attributes for director master
 * 
 * @author $Author:Rajib Aich $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $ Tag : $Name$
 */

public abstract class AbstractValuationAgencyTrxOperation extends
		CMSTrxOperation implements ITrxRouteOperation {

	private IValuationAgencyBusManager valuationAgencyBusManager;

	private IValuationAgencyBusManager stagingValuationAgencyBusManager;
	
	//**********************FOR UPLOAD********************************
	   private IValuationAgencyBusManager stagingValuationAgencyFileMapperIdBusManager;
	    
	    private IValuationAgencyBusManager valuationAgencyFileMapperIdBusManager;

	  //**********************UPLOAD********************************
	 
	    
	    
	public IValuationAgencyBusManager getValuationAgencyBusManager() {
		return valuationAgencyBusManager;
	}

	public void setValuationAgencyBusManager(
			IValuationAgencyBusManager valuationAgencyBusManager) {
		this.valuationAgencyBusManager = valuationAgencyBusManager;
	}

	public IValuationAgencyBusManager getStagingValuationAgencyBusManager() {
		return stagingValuationAgencyBusManager;
	}

	public void setStagingValuationAgencyBusManager(
			IValuationAgencyBusManager stagingValuationAgencyBusManager) {
		this.stagingValuationAgencyBusManager = stagingValuationAgencyBusManager;
	}

	protected IValuationAgencyTrxValue prepareTrxValue(
			IValuationAgencyTrxValue valuationAgencyTrxValue)
			throws TrxOperationException {
		if (valuationAgencyTrxValue != null) {
			IValuationAgency actual = valuationAgencyTrxValue
					.getValuationAgency();
			IValuationAgency staging = valuationAgencyTrxValue
					.getStagingValuationAgency();
			if (actual != null) {
				valuationAgencyTrxValue.setReferenceID(String.valueOf(actual
						.getId()));
			} else {
				valuationAgencyTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				valuationAgencyTrxValue.setStagingReferenceID(String
						.valueOf(staging.getId()));
			} else {
				valuationAgencyTrxValue.setStagingReferenceID(null);
			}
			return valuationAgencyTrxValue;
		} else {
			throw new TrxOperationException("ERROR-- Valuation Agency is null");
		}
	}

	/**
	 * 
	 * @param valuationAgencyTrxValue
	 * @return IValuationAgencyTrxValue
	 * @throws TrxOperationException
	 */

	protected IValuationAgencyTrxValue updateValuationAgencyTrx(
			IValuationAgencyTrxValue valuationAgencyTrxValue)
			throws TrxOperationException {
		try {
			valuationAgencyTrxValue = prepareTrxValue(valuationAgencyTrxValue);
			ICMSTrxValue tempValue = super
					.updateTransaction(valuationAgencyTrxValue);
			OBValuationAgencyTrxValue newValue = new OBValuationAgencyTrxValue(
					tempValue);
			newValue.setValuationAgency(valuationAgencyTrxValue
					.getValuationAgency());
			newValue.setStagingValuationAgency(valuationAgencyTrxValue
					.getStagingValuationAgency());
			return newValue;
		}

		catch (TrxOperationException ex) {
			throw new TrxOperationException("General Exception: "
					+ ex.toString());
		}
	}

	/**
	 * 
	 * @param valuationAgencyTrxValue
	 * @return IValuationAgencyTrxValue
	 * @throws TrxOperationException
	 */

	protected IValuationAgencyTrxValue createStagingValuationAgency(
			IValuationAgencyTrxValue valuationAgencyTrxValue)
			throws TrxOperationException {
		try {
			IValuationAgency valuationAgency = getStagingValuationAgencyBusManager().createValuationAgency(valuationAgencyTrxValue.getStagingValuationAgency());
			valuationAgencyTrxValue.setStagingValuationAgency(valuationAgency);
			valuationAgencyTrxValue.setStagingReferenceID(String
					.valueOf(valuationAgency.getId()));
			return valuationAgencyTrxValue;
		} catch (Exception ex) {

			throw new TrxOperationException(ex);
		}
	}

	/**
	 * 
	 * @param anITrxValue
	 * @return IValuationAgencyTrxValue
	 * @throws TrxOperationException
	 */

	protected IValuationAgencyTrxValue getValuationAgencyTrxValue(
			ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IValuationAgencyTrxValue) anITrxValue;
		} catch (ClassCastException ex) {
			throw new TrxOperationException(
					"The ITrxValue is not of type OBCValuationAgencyTrxValue: "
							+ ex.toString());
		}
	}

	/**
	 * 
	 * @param anOriginal
	 * @param aCopy
	 * @return IValuationAgencyTrxValue
	 * @throws TrxOperationException
	 */

	protected IValuationAgency mergeValuationAgency(
			IValuationAgency anOriginal, IValuationAgency aCopy)
			throws TrxOperationException {
		aCopy.setId(anOriginal.getId());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * 
	 * @param value
	 * @return ITrxResult
	 */

	protected ITrxResult prepareResult(IValuationAgencyTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected IValuationAgencyTrxValue disableStagingValuationAgency(
			IValuationAgencyTrxValue valuationAgencyTrxValue)
			throws TrxOperationException {
		try {
			IValuationAgency valuationAgency = getStagingValuationAgencyBusManager()
					.disableValuationAgency(
							valuationAgencyTrxValue.getStagingValuationAgency());
			valuationAgencyTrxValue.setStagingValuationAgency(valuationAgency);
			valuationAgencyTrxValue.setStagingReferenceID(String
					.valueOf(valuationAgency.getId()));
			return valuationAgencyTrxValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected IValuationAgencyTrxValue enableStagingValuationAgency(
			IValuationAgencyTrxValue valuationAgencyTrxValue)
			throws TrxOperationException {
		try {
			IValuationAgency valuationAgency = getStagingValuationAgencyBusManager()
					.enableValuationAgency(
							valuationAgencyTrxValue.getStagingValuationAgency());
			valuationAgencyTrxValue.setStagingValuationAgency(valuationAgency);
			valuationAgencyTrxValue.setStagingReferenceID(String
					.valueOf(valuationAgency.getId()));
			return valuationAgencyTrxValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}
	
    //------------------------------------File Insert---------------------------------------------
    
    protected IValuationAgencyTrxValue prepareInsertTrxValue(IValuationAgencyTrxValue valuationAgencyTrxValue)throws TrxOperationException {
        if (valuationAgencyTrxValue != null) {
            IFileMapperId actual = valuationAgencyTrxValue.getFileMapperID();
            IFileMapperId staging = valuationAgencyTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	valuationAgencyTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	valuationAgencyTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	valuationAgencyTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	valuationAgencyTrxValue.setStagingReferenceID(null);
            }
            return valuationAgencyTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- ValuationAgency is null");
        }
    }
    
    
    
    protected IValuationAgencyTrxValue createStagingFileId(IValuationAgencyTrxValue valuationAgencyTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingValuationAgencyFileMapperIdBusManager().createFileId(valuationAgencyTrxValue.getStagingFileMapperID());
        	valuationAgencyTrxValue.setStagingFileMapperID(fileMapperID);
        	valuationAgencyTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return valuationAgencyTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IValuationAgencyTrxValue insertActualValuationAgency(IValuationAgencyTrxValue valuationAgencyTrxValue) throws TrxOperationException {
        try {
            IValuationAgency valuationAgency = getStagingValuationAgencyFileMapperIdBusManager().insertValuationAgency(valuationAgencyTrxValue.getStagingValuationAgency());
            valuationAgencyTrxValue.setValuationAgency(valuationAgency);
            valuationAgencyTrxValue.setReferenceID(String.valueOf(valuationAgency.getId()));
            return valuationAgencyTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IValuationAgencyTrxValue updateMasterInsertTrx(IValuationAgencyTrxValue valuationAgencyTrxValue) throws TrxOperationException {
        try {
        	valuationAgencyTrxValue = prepareInsertTrxValue(valuationAgencyTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(valuationAgencyTrxValue);
            OBValuationAgencyTrxValue newValue = new OBValuationAgencyTrxValue(tempValue);
            newValue.setFileMapperID(valuationAgencyTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(valuationAgencyTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }

	public IValuationAgencyBusManager getStagingValuationAgencyFileMapperIdBusManager() {
		return stagingValuationAgencyFileMapperIdBusManager;
	}

	public void setStagingValuationAgencyFileMapperIdBusManager(
			IValuationAgencyBusManager stagingValuationAgencyFileMapperIdBusManager) {
		this.stagingValuationAgencyFileMapperIdBusManager = stagingValuationAgencyFileMapperIdBusManager;
	}

	public IValuationAgencyBusManager getValuationAgencyFileMapperIdBusManager() {
		return valuationAgencyFileMapperIdBusManager;
	}

	public void setValuationAgencyFileMapperIdBusManager(
			IValuationAgencyBusManager valuationAgencyFileMapperIdBusManager) {
		this.valuationAgencyFileMapperIdBusManager = valuationAgencyFileMapperIdBusManager;
	}

	

	
}