package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISectorLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitUtils;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

import java.rmi.RemoteException;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 3, 2008
 */
public abstract class AbstractSectorLimitParameterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	
	private static final long serialVersionUID = 1L;

    ISectorLimitBusManager sectorLimitParameterBusManager;
    ISectorLimitBusManager stagingSectorLimitParameterBusManager;

    public ISectorLimitBusManager getSectorLimitParameterBusManager() {
        return sectorLimitParameterBusManager;
    }

    public void setSectorLimitParameterBusManager(ISectorLimitBusManager sectorLimitParameterBusManager) {
        this.sectorLimitParameterBusManager = sectorLimitParameterBusManager;
    }

    public ISectorLimitBusManager getStagingSectorLimitParameterBusManager() {
        return stagingSectorLimitParameterBusManager;
    }

    public void setStagingSectorLimitParameterBusManager(ISectorLimitBusManager stagingSectorLimitParameterBusManager) {
        this.stagingSectorLimitParameterBusManager = stagingSectorLimitParameterBusManager;
    }

    public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
        return value;
    }
	
	protected ISectorLimitParameterTrxValue createStaging(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) throws TrxOperationException {
		try {
			IMainSectorLimitParameter stagingLimit = sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter();

            IMainSectorLimitParameter replicatedStagingLimit = SectorLimitUtils.replicateMainSectorLimitParameterForCreate(stagingLimit);
            replicatedStagingLimit = getStagingSectorLimitParameterBusManager().createLimit(replicatedStagingLimit);

            sectorLimitParameterTrxValue.setStagingMainSectorLimitParameter(replicatedStagingLimit);
			
			return sectorLimitParameterTrxValue;
		
        } catch (SectorLimitException e) {
            throw new TrxOperationException ("SectorLimitException caught!", e);
        } 
	}

    protected ISectorLimitParameterTrxValue createActual(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) throws TrxOperationException {
        try {
        	IMainSectorLimitParameter stagingLimit = sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter();
            IMainSectorLimitParameter replicatedStagingLimit = SectorLimitUtils.replicateMainSectorLimitParameterForCreate(stagingLimit);

        	replicatedStagingLimit.setStatus(ICMSConstant.STATE_ACTIVE);

            IMainSectorLimitParameter actualLimit = getSectorLimitParameterBusManager().createLimit(replicatedStagingLimit);
            sectorLimitParameterTrxValue.setActualMainSectorLimitParameter(actualLimit);
            return sectorLimitParameterTrxValue;
        } catch (SectorLimitException e) {
            throw new TrxOperationException ("SectorLimitException caught!", e);
        }
    }

    protected ISectorLimitParameterTrxValue updateActual(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) throws TrxOperationException {
        try {
        	IMainSectorLimitParameter actualLimit = sectorLimitParameterTrxValue.getActualMainSectorLimitParameter();
        	IMainSectorLimitParameter stagingLimit = sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter();

            IMainSectorLimitParameter updActual = (IMainSectorLimitParameter)CommonUtil.deepClone(stagingLimit);
            updActual = mergeSectorLimit(actualLimit, updActual);
            
            actualLimit = getSectorLimitParameterBusManager().updateLimit(updActual);
            sectorLimitParameterTrxValue.setActualMainSectorLimitParameter(actualLimit);

            return sectorLimitParameterTrxValue;

        } catch (SectorLimitException e) {
            throw new TrxOperationException ("SectorLimitException caught!", e);
        } catch (RemoteException e) {
            throw new TrxOperationException ("RemoteException caught!", e);
        } catch (Exception ex) {
            throw new TrxOperationException("Exception in updateActual(): " + ex.toString());
        }
    }
    
    protected IMainSectorLimitParameter mergeSectorLimit(IMainSectorLimitParameter anOriginal, IMainSectorLimitParameter aCopy) throws TrxOperationException
    {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }

    protected ISectorLimitParameterTrxValue createTransaction(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) throws TrxOperationException {
        sectorLimitParameterTrxValue = prepareTrxValue(sectorLimitParameterTrxValue);
        ICMSTrxValue tmpVal = super.createTransaction(sectorLimitParameterTrxValue);
        OBSectorLimitParameterTrxValue newVal = new OBSectorLimitParameterTrxValue(tmpVal);
        newVal.setActualMainSectorLimitParameter(sectorLimitParameterTrxValue.getActualMainSectorLimitParameter());
        newVal.setStagingMainSectorLimitParameter(sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter());
        return newVal;
    }

    protected ISectorLimitParameterTrxValue updateTransaction(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) throws TrxOperationException {
        sectorLimitParameterTrxValue = prepareTrxValue(sectorLimitParameterTrxValue);
        ICMSTrxValue tmpVal = super.updateTransaction(sectorLimitParameterTrxValue);
        OBSectorLimitParameterTrxValue newVal = new OBSectorLimitParameterTrxValue(tmpVal);
        newVal.setActualMainSectorLimitParameter(sectorLimitParameterTrxValue.getActualMainSectorLimitParameter());
        newVal.setStagingMainSectorLimitParameter(sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter());
        return newVal;
    }

    protected ISectorLimitParameterTrxValue getTrxValue(ITrxValue iTrxValue) throws TrxOperationException {
        if (iTrxValue instanceof ISectorLimitParameterTrxValue) {
            return (ISectorLimitParameterTrxValue) iTrxValue;
        } else {
            return new OBSectorLimitParameterTrxValue(iTrxValue);
        }
    }

    protected ISectorLimitParameterTrxValue prepareTrxValue(ISectorLimitParameterTrxValue sectorLimitParameterTrxValue) {

        if (sectorLimitParameterTrxValue != null) {
        	IMainSectorLimitParameter actualLimit  = sectorLimitParameterTrxValue.getActualMainSectorLimitParameter();
        	IMainSectorLimitParameter stagingLimit = sectorLimitParameterTrxValue.getStagingMainSectorLimitParameter();

            if (actualLimit != null && actualLimit.getId() != null) {
            	sectorLimitParameterTrxValue.setReferenceID(String.valueOf(actualLimit.getId()));
            } else
                sectorLimitParameterTrxValue.setReferenceID(null);
            

            if (stagingLimit != null && stagingLimit.getId() != null) {
            	sectorLimitParameterTrxValue.setStagingReferenceID(String.valueOf(stagingLimit.getId()));
            } else
                sectorLimitParameterTrxValue.setStagingReferenceID(null);
            
        }
        return sectorLimitParameterTrxValue;
    }

    protected ITrxResult prepareResult(ISectorLimitParameterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }

}
