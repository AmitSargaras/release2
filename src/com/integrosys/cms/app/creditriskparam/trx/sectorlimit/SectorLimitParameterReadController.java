package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * Author: Syukri
 * Date: Jun 4, 2008
 */
public class SectorLimitParameterReadController extends AbstractTrxController implements ITrxOperationFactory {
	
	private static final long serialVersionUID = 1L;

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    public SectorLimitParameterReadController() {
    }

    public String getInstanceName() {
        return ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER;
    }

    public ITrxResult operate(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException, TrxControllerException, TransactionException {
        if (iTrxValue == null)
            throw new TrxParameterException("ITrxValue is NULL");

        if (iTrxParameter == null)
            throw new TrxParameterException("ITrxParameter is NULL");

        iTrxValue = setInstanceName(iTrxValue);
        ITrxOperation op = getOperation(iTrxValue, iTrxParameter);
        CMSReadTrxManager mgr =  new CMSReadTrxManager();

        ITrxResult result = mgr.operateTransaction(op, iTrxValue);
        return result;
    }


    public ITrxOperation getOperation(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
        if (iTrxParameter == null)
            throw new TrxParameterException("ITrxParameter is NULL");

        String action = iTrxParameter.getAction();

        if (action != null) {
            if (ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadSectorLimitParameterOperation");
            } else if (ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_TRXID.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadSectorLimitParameterByTrxIdOperation");
            } else if (ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_ID.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadSectorLimitParameterByIdOperation");
            }

            throw new TrxParameterException("Unknow Action: " + action + ".");
        }
        
        throw new TrxParameterException("Action is null!");
    }
}
