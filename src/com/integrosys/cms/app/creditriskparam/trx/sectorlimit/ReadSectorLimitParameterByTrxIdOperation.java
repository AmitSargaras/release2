package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

import java.rmi.RemoteException;

/**
 * Author: Syukri
 * Date: Jun 16, 2008
 */
public class ReadSectorLimitParameterByTrxIdOperation extends AbstractSectorLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_TRXID;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
        try {
            ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(value.getTransactionID());
            DefaultLogger.debug (this, "SectorLimit >>>>> Transaction: " + cmsTrxValue.toString());
            
            OBSectorLimitParameterTrxValue trxValue = new OBSectorLimitParameterTrxValue(cmsTrxValue);
            DefaultLogger.debug (this, "SectorLimit >>>>> Transaction: " + trxValue.toString());
            String stagingRef = cmsTrxValue.getStagingReferenceID();
            String actualRef = cmsTrxValue.getReferenceID();

            DefaultLogger.debug (this, "SectorLimit >>>>> Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (stagingRef != null) {
              //  IMainSectorLimitParameter limitList = (IMainSectorLimitParameter) getStagingSectorLimitParameterBusManager().getLimitByIdWithoutFilter(Long.parseLong(stagingRef));
                IMainSectorLimitParameter limitList =  getStagingSectorLimitParameterBusManager().getLimitById(Long.parseLong(stagingRef));
                trxValue.setStagingMainSectorLimitParameter(limitList); 
            }
                 
            if (actualRef != null) {
                IMainSectorLimitParameter limitList = getSectorLimitParameterBusManager().getLimitById(Long.parseLong(actualRef));
                trxValue.setActualMainSectorLimitParameter(limitList);  
            }

            DefaultLogger.debug (this, "SectorLimit >>>>> read by trx value: " + trxValue );
            
            return trxValue;
            
        } catch (RemoteException e) {
            throw new TransactionException("RemoteException caught!", e);


        } catch (SectorLimitException e) {
            throw new TransactionException("SectorLimitException caught!", e);


        }
    }
}
