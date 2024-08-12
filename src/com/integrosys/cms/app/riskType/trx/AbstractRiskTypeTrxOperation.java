package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.IRiskTypeBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractRiskTypeTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IRiskTypeBusManager riskTypeBusManager;

	private IRiskTypeBusManager stagingRiskTypeBusManager;
	
	public IRiskTypeBusManager getRiskTypeBusManager() {
		return riskTypeBusManager;
	}

	public void setRiskTypeBusManager(IRiskTypeBusManager riskTypeBusManager) {
		this.riskTypeBusManager = riskTypeBusManager;
	}

	public IRiskTypeBusManager getStagingRiskTypeBusManager() {
		return stagingRiskTypeBusManager;
	}

	public void setStagingRiskTypeBusManager(IRiskTypeBusManager stagingRiskTypeBusManager) {
		this.stagingRiskTypeBusManager = stagingRiskTypeBusManager;
	}

	protected IRiskTypeTrxValue prepareTrxValue(IRiskTypeTrxValue riskTypeTrxValue)throws TrxOperationException {
        if (riskTypeTrxValue != null) {
        	IRiskType actual = riskTypeTrxValue.getRiskType();
        	IRiskType staging = riskTypeTrxValue.getStagingRiskType();
            if (actual != null) {
            	riskTypeTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	riskTypeTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	riskTypeTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	riskTypeTrxValue.setStagingReferenceID(null);
            }
            return riskTypeTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- RiskType is null");
        }
    }
	
	/**
	 * 
	 * @param RiskTypeTrxValue
	 * @return IRiskTypeTrxValue
	 * @throws TrxOperationException
	 */

    protected IRiskTypeTrxValue updateRiskTypeTrx(IRiskTypeTrxValue riskTypeTrxValue) throws TrxOperationException {
        try {
        	riskTypeTrxValue = prepareTrxValue(riskTypeTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(riskTypeTrxValue);
            OBRiskTypeTrxValue newValue = new OBRiskTypeTrxValue(tempValue);
            newValue.setRiskType(riskTypeTrxValue.getRiskType());
            newValue.setStagingRiskType(riskTypeTrxValue.getStagingRiskType());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param RiskTypeTrxValue
     * @return IRiskTypeTrxValue
     * @throws TrxOperationException
     */

    protected IRiskTypeTrxValue createStagingRiskType(IRiskTypeTrxValue riskTypeTrxValue) throws TrxOperationException {
        try {
        	IRiskType riskType = getStagingRiskTypeBusManager().createRiskType(riskTypeTrxValue.getStagingRiskType());
        	riskTypeTrxValue.setStagingRiskType(riskType);
        	riskTypeTrxValue.setStagingReferenceID(String.valueOf(riskType.getId()));
            return riskTypeTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IRiskTypeTrxValue
     * @throws TrxOperationException
     */

    protected IRiskTypeTrxValue getRiskTypeTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IRiskTypeTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBRiskTypeTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IRiskTypeTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
