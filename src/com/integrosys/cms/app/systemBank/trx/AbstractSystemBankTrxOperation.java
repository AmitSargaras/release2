package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankBusManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract System Bank Operation 
 */

public abstract class AbstractSystemBankTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ISystemBankBusManager systemBankBusManager;

    private ISystemBankBusManager stagingSystemBankBusManager;

    public ISystemBankBusManager getSystemBankBusManager() {
        return systemBankBusManager;
    }

    public void setSystemBankBusManager(ISystemBankBusManager systemBankBusManager) {
        this.systemBankBusManager = systemBankBusManager;
    }

    public ISystemBankBusManager getStagingSystemBankBusManager() {
        return stagingSystemBankBusManager;
    }

    public void setStagingSystemBankBusManager(ISystemBankBusManager stagingSystemBankBusManager) {
        this.stagingSystemBankBusManager = stagingSystemBankBusManager;
    }
    /**
     * 
     * @param systemBankTrxValue
     * @return ISystemBankTrxValue
     */

    protected ISystemBankTrxValue prepareTrxValue(ISystemBankTrxValue systemBankTrxValue) {
        if (systemBankTrxValue != null) {
            ISystemBank actual = systemBankTrxValue.getSystemBank();
            ISystemBank staging = systemBankTrxValue.getStagingSystemBank();
            if (actual != null) {
            	systemBankTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	systemBankTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	systemBankTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	systemBankTrxValue.setStagingReferenceID(null);
            }
            return systemBankTrxValue;
        }else{
        	throw new  SystemBankException("ERROR-- System Bank is null");
        }
    }
    /**
     * 
     * @param systemBankTrxValue
     * @return ISystemBankTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankTrxValue updateSystemBankTrx(ISystemBankTrxValue systemBankTrxValue) throws TrxOperationException {
        try {
        	systemBankTrxValue = prepareTrxValue(systemBankTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(systemBankTrxValue);
            OBSystemBankTrxValue newValue = new OBSystemBankTrxValue(tempValue);
            newValue.setSystemBank(systemBankTrxValue.getSystemBank());
            newValue.setStagingSystemBank(systemBankTrxValue.getStagingSystemBank());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new SystemBankException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param systemBankTrxValue
     * @return ISystemBankTrxValue
     * @throws TrxOperationException
     */
    protected ISystemBankTrxValue createStagingSystemBank(ISystemBankTrxValue systemBankTrxValue) throws TrxOperationException {
        try {
            ISystemBank systemBank = getStagingSystemBankBusManager().createSystemBank(systemBankTrxValue.getStagingSystemBank());
            systemBankTrxValue.setStagingSystemBank(systemBank);
            systemBankTrxValue.setStagingReferenceID(String.valueOf(systemBank.getId()));
            return systemBankTrxValue;
        }
        catch (Exception ex) {
            throw new SystemBankException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ISystemBankTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankTrxValue getSystemBankTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ISystemBankTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new SystemBankException("The ITrxValue is not of type OBCSystemBankTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return ISystemBankTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBank mergeSystemBank(ISystemBank anOriginal, ISystemBank aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ISystemBankTrxValue
     */

    protected ITrxResult prepareResult(ISystemBankTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
