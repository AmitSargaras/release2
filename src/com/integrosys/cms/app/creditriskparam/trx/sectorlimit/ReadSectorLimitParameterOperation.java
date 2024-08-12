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
import java.util.List;

/**
 * Author: Syukri
 * Date: Jun 3, 2008
 */
public class ReadSectorLimitParameterOperation extends AbstractSectorLimitParameterTrxOperation implements ITrxReadOperation {
	
	private static final long serialVersionUID = 1L;
	
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER;
    }

    public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

        try {
			ICMSTrxValue trxValue = super.getCMSTrxValue (value);
			
			List actualList = null;
			
			String actualRef = null;
            String stagingRef = null;

            actualList = getSectorLimitParameterBusManager().getAllSectorLimit();

            if (actualList != null && actualList.size() > 0) {
            	
            	actualRef = String.valueOf(((IMainSectorLimitParameter) actualList.get(0)).getId());
            	
                try {
                    trxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRef, ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER);
                    stagingRef = trxValue.getStagingReferenceID();
                } catch (Exception e){
                    // do nothing for very first entry
                }
                
            } else {
                trxValue = getTrxManager().getWorkingTrxByTrxType(ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER);

                if (trxValue == null)
                    return null;

                if (trxValue != null) {
                    stagingRef = trxValue.getStagingReferenceID();
                    DefaultLogger.debug (this, "SectorLimit stagingRef = " + stagingRef);
                }
            }

            ISectorLimitParameterTrxValue returnValue = new OBSectorLimitParameterTrxValue(trxValue);

            if (actualRef != null) {
                IMainSectorLimitParameter limitList =  getSectorLimitParameterBusManager().getLimitById(Long.parseLong(actualRef));
                returnValue.setActualMainSectorLimitParameter(limitList);
            }

            if (stagingRef != null) {
                IMainSectorLimitParameter limit =  getStagingSectorLimitParameterBusManager().getLimitById(Long.parseLong(stagingRef));
                returnValue.setStagingMainSectorLimitParameter(limit);
                
            }

            return returnValue;

        } catch (SectorLimitException e) {
            throw new TransactionException(e);
        } catch (RemoteException e) {
            throw new TransactionException(e);
        }
    }
}
