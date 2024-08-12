package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmailBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractSegmentWiseEmailTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private ISegmentWiseEmailBusManager segmentWiseEmailBusManager;
	private ISegmentWiseEmailBusManager stagingSegmentWiseEmailBusManager;
	
	public ISegmentWiseEmailBusManager getSegmentWiseEmailBusManager() {
		return segmentWiseEmailBusManager;
	}
	public void setSegmentWiseEmailBusManager(ISegmentWiseEmailBusManager segmentWiseEmailBusManager) {
		this.segmentWiseEmailBusManager = segmentWiseEmailBusManager;
	}
	public ISegmentWiseEmailBusManager getStagingSegmentWiseEmailBusManager() {
		return stagingSegmentWiseEmailBusManager;
	}
	public void setStagingSegmentWiseEmailBusManager(ISegmentWiseEmailBusManager stagingSegmentWiseEmailBusManager) {
		this.stagingSegmentWiseEmailBusManager = stagingSegmentWiseEmailBusManager;
	}
	
	protected ISegmentWiseEmailTrxValue prepareTrxValue(ISegmentWiseEmailTrxValue segmentWiseEmailTrxValue)throws TrxOperationException {
        if (segmentWiseEmailTrxValue != null) {
        	ISegmentWiseEmail actual = segmentWiseEmailTrxValue.getSegmentWiseEmail();
        	ISegmentWiseEmail staging = segmentWiseEmailTrxValue.getStagingSegmentWiseEmail();
            if (actual != null) {
            	segmentWiseEmailTrxValue.setReferenceID(String.valueOf(actual.getID()));
            } else {
            	segmentWiseEmailTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	segmentWiseEmailTrxValue.setStagingReferenceID(String.valueOf(staging.getID()));
            } else {
            	segmentWiseEmailTrxValue.setStagingReferenceID(null);
            }
            return segmentWiseEmailTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- SegmentWiseEmail is null");
        }
    }
	
	/**
	 * 
	 * @param SegmentWiseEmailTrxValue
	 * @return ISegmentWiseEmailTrxValue
	 * @throws TrxOperationException
	 */

    protected ISegmentWiseEmailTrxValue updateSegmentWiseEmailTrx(ISegmentWiseEmailTrxValue segmentWiseEmailTrxValue) throws TrxOperationException {
        try {
        	segmentWiseEmailTrxValue = prepareTrxValue(segmentWiseEmailTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(segmentWiseEmailTrxValue);
            OBSegmentWiseEmailTrxValue newValue = new OBSegmentWiseEmailTrxValue(tempValue);
            newValue.setSegmentWiseEmail(segmentWiseEmailTrxValue.getSegmentWiseEmail());
            newValue.setStagingSegmentWiseEmail(segmentWiseEmailTrxValue.getStagingSegmentWiseEmail());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param SegmentWiseEmailTrxValue
	 * @return ISegmentWiseEmailTrxValue
	 * @throws TrxOperationException
     */

    protected ISegmentWiseEmailTrxValue createStagingSegmentWiseEmail(ISegmentWiseEmailTrxValue segmentWiseEmailTrxValue) throws TrxOperationException {
        try {
        	ISegmentWiseEmail segmentWiseEmail = getStagingSegmentWiseEmailBusManager().createSegmentWiseEmail(segmentWiseEmailTrxValue.getStagingSegmentWiseEmail());
        	segmentWiseEmailTrxValue.setStagingSegmentWiseEmail(segmentWiseEmail);
        	segmentWiseEmailTrxValue.setStagingReferenceID(String.valueOf(segmentWiseEmail.getID()));
            return segmentWiseEmailTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return ISegmentWiseEmailTrxValue
     * @throws TrxOperationException
     */

    protected ISegmentWiseEmailTrxValue getSegmentWiseEmailTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ISegmentWiseEmailTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBSegmentWiseEmailTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ISegmentWiseEmailTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
