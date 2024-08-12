package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

import java.rmi.RemoteException;


public class ReadSectorLimitParameterByIdOperation extends AbstractSectorLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_ID;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

        try {
			ICMSTrxValue trxValue = super.getCMSTrxValue (value);

            trxValue = getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER);
            
            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();
             
            ISectorLimitParameterTrxValue returnValue = new OBSectorLimitParameterTrxValue(trxValue);

            if (actualRef != null) {
               IMainSectorLimitParameter limitList =  getSectorLimitParameterBusManager().getLimitById(Long.parseLong(actualRef));
               returnValue.setActualMainSectorLimitParameter(limitList);
            }

            if (stagingRef != null) {
                IMainSectorLimitParameter limitList =  getStagingSectorLimitParameterBusManager().getLimitById(Long.parseLong(stagingRef));
                returnValue.setStagingMainSectorLimitParameter(limitList);
            }
            return returnValue;

        } catch (SectorLimitException e) {
            throw new TransactionException(e);
        } catch (RemoteException e) {
            throw new TransactionException(e);
        }
    }
}
