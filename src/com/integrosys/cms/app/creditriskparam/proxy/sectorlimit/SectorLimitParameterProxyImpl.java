package com.integrosys.cms.app.creditriskparam.proxy.sectorlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISectorLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.OBSectorLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.SectorLimitParameterTrxControllerFactory;
import com.integrosys.cms.app.transaction.*;

import java.util.List;
import java.util.Set;

/**
 * Author: Syukri
 * Date: Jun 5, 2008
 */
public class SectorLimitParameterProxyImpl implements ISectorLimitParameterProxy{
    ISectorLimitBusManager sectorLimitParameterBusManager;
    ISectorLimitBusManager stagingSectorLimitParameterBusManager;
    ITrxControllerFactory trxControllerFactory;

    public ITrxControllerFactory getTrxControllerFactory() {
        return trxControllerFactory;
    }

    public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
        this.trxControllerFactory = trxControllerFactory;
    }

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

    public List listSectorLimit() throws SectorLimitException {
        try {
            return getSectorLimitParameterBusManager().getAllSectorLimit();
        } catch (Exception e) {
            throw new SectorLimitException ("RemoteException caught! " + e.toString());
        }
    }
    
    public List listSubSectorLimit() throws SectorLimitException {
        try {
            return getSectorLimitParameterBusManager().getAllSubSectorLimit();
        } catch (Exception e) {
            throw new SectorLimitException ("RemoteException caught! " + e.toString());
        }
    }
    
    public List listEcoSectorLimit() throws SectorLimitException {
        try {
            return getSectorLimitParameterBusManager().getAllEcoSectorLimit();
        } catch (Exception e) {
            throw new SectorLimitException ("RemoteException caught! " + e.toString());
        }
    }
    
    public IMainSectorLimitParameter getSectorLimitById (long id) throws SectorLimitException {
    	try {
    		return getSectorLimitParameterBusManager().getLimitById(id);
    	} catch (Exception e) {
    		throw new SectorLimitException ("RemoteException caught! " + e.toString());
    	}
    }

    public ISectorLimitParameterTrxValue getTrxValueById (long id) throws SectorLimitException {
    	try {
            ISectorLimitParameterTrxValue trxValue = new OBSectorLimitParameterTrxValue();
            trxValue.setReferenceID(String.valueOf(id));
            trxValue.setTransactionType(ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_ID);

            return (ISectorLimitParameterTrxValue) operate(formulateTrxValue(new OBTrxContext(), trxValue), param);
    	} catch (Exception e) {
    		throw new SectorLimitException ("RemoteException caught! " + e.toString());
    	}
    }
    
    public ISectorLimitParameterTrxValue getTrxValue(ITrxContext ctx) throws SectorLimitException {
        try {
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER);
            ISectorLimitParameterTrxValue trxValue = new OBSectorLimitParameterTrxValue();

            return (ISectorLimitParameterTrxValue) operate(formulateTrxValue(ctx, trxValue), param);
        } catch (Exception e) {
            throw new SectorLimitException ("RemoteException caught! " + e.toString());
        }
    }

    public ISectorLimitParameterTrxValue makerUpdateList(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException {
        if (ctx == null)
            throw new SectorLimitException("ITrxContext is NULL");

        if (trxValue == null)
            throw new SectorLimitException("TrxValue is NULL");

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ( trxValue.getStatus().equals(ICMSConstant.STATE_ND) ||
			 trxValue.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction (ICMSConstant.ACTION_MAKER_CREATE_SECTOR_LIMIT_PARAMETER);
		}
		else {
			param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_SECTOR_LIMIT_PARAMETER);
		}

        trxValue = formulateTrxValue(ctx, trxValue);
		return (ISectorLimitParameterTrxValue) operate(trxValue, param);
	}

    public ISectorLimitParameterTrxValue checkerApprove(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SECTOR_LIMIT_PARAMETER);
        trxValue = formulateTrxValue(ctx, trxValue);
        return (ISectorLimitParameterTrxValue) operate(trxValue, param);
    }

    public ISectorLimitParameterTrxValue checkerReject(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SECTOR_LIMIT_PARAMETER);
        trxValue = formulateTrxValue(ctx, trxValue);
        return (ISectorLimitParameterTrxValue) operate(trxValue, param);
    }

    public ISectorLimitParameterTrxValue makerClose(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();

        if (ICMSConstant.STATE_REJECTED_CREATE.equals(trxValue.getStatus()))
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CREATE_SECTOR_LIMIT_PARAMETER);
        else if (ICMSConstant.STATE_REJECTED_UPDATE.equals(trxValue.getStatus()))
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_SECTOR_LIMIT_PARAMETER);
        else
        	 param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DELETE_SECTOR_LIMIT_PARAMETER);

        trxValue = formulateTrxValue(ctx, trxValue);
        return (ISectorLimitParameterTrxValue) operate(trxValue, param);
    }

    public ISectorLimitParameterTrxValue makerDelete(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException {
    	OBCMSTrxParameter param = new OBCMSTrxParameter();

    	param.setAction(ICMSConstant.ACTION_MAKER_DELETE_SECTOR_LIMIT_PARAMETER);

    	trxValue = formulateTrxValue(ctx, trxValue);
    	return (ISectorLimitParameterTrxValue) operate(trxValue, param);
    }

    protected ISectorLimitParameterTrxValue formulateTrxValue(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) {
        trxValue.setTrxContext(ctx);
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SECTOR_LIMIT_PARAMETER);
        return trxValue;
    }

    protected ISectorLimitParameterTrxValue formulateTrxValue(ITrxContext ctx, ICMSTrxValue value, IMainSectorLimitParameter sectorLimit) {
        ISectorLimitParameterTrxValue trxValue = null;

        if (value != null)
            trxValue = new OBSectorLimitParameterTrxValue(value);
        else
            trxValue = new OBSectorLimitParameterTrxValue();

        trxValue = formulateTrxValue(ctx, trxValue);
        trxValue.setStagingMainSectorLimitParameter(sectorLimit);

        return trxValue;
    }

    protected ICMSTrxResult operateForResult(ICMSTrxValue value, OBCMSTrxParameter param) throws SectorLimitException {
        try {
            ITrxController controller = getTrxControllerFactory().getController(value, param);
            if (controller == null)
                throw new SectorLimitException("ITrxController is NULL");

            ITrxResult result = controller.operate(value, param);
            return (ICMSTrxResult) result;
        } catch (TrxParameterException e) {
            throw new SectorLimitException ("TrxParameterException caught! " + e.toString());
        } catch (TransactionException e) {
            throw new SectorLimitException ("TransactionException caught! " + e.toString());
        }
    }

    protected ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws SectorLimitException {
        if (trxVal == null)
            throw new SectorLimitException("ITrxValue is null");

        try {
            ITrxController controller = null;

            if (trxVal instanceof ISectorLimitParameterTrxValue)
                controller = getTrxControllerFactory().getController(trxVal, param);

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue value = result.getTrxValue();
            return value;
        } catch (TrxParameterException e) {
            throw new SectorLimitException("TrxParameterException caught! " + e.toString(), e);
        } catch (TrxControllerException e) {
            throw new SectorLimitException("TrxControllerException caught! " + e.toString(), e);
        } catch (TransactionException e) {
            throw new SectorLimitException("TransactionException caught! " + e.toString(), e);
        }
    }



    public ISectorLimitParameterTrxValue getTrxValueByTrxId(ITrxContext ctx, String trxId) throws SectorLimitException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_TRXID);
        ISectorLimitParameterTrxValue trxValue = new OBSectorLimitParameterTrxValue();
        trxValue.setTransactionID(trxId);

        return (ISectorLimitParameterTrxValue) operate(formulateTrxValue(ctx, trxValue), param);
    }
}
