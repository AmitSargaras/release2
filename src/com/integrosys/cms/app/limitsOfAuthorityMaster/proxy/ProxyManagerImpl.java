package com.integrosys.cms.app.limitsOfAuthorityMaster.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.IBusManager;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.LimitsOfAuthorityMasterException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trx.ILimitsOfAuthorityMasterTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trx.OBLimitsOfAuthorityMasterTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class ProxyManagerImpl implements IProxyManager{
	
	private IBusManager busManager;

	private IBusManager stagingBusManager;
	
	private ITrxControllerFactory trxControllerFactory;  

	public IBusManager getBusManager() {
		return busManager;
	}

	public void setBusManager(IBusManager busManager) {
		this.busManager = busManager;
	}

	public IBusManager getStagingBusManager() {
		return stagingBusManager;
	}

	public void setStagingBusManager(IBusManager stagingBusManager) {
		this.stagingBusManager = stagingBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerCreate(ITrxContext anITrxContext,
			ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new LimitsOfAuthorityMasterException("The LimitsOfAuthorityMaster to be updated is null !!!");
		}

		ILimitsOfAuthorityMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CREATE_LIMITS_OF_AUTHORITY);
		return operate(trxValue, param);
	}
	
	
	
	private ILimitsOfAuthorityMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ILimitsOfAuthorityMaster obj) {
		ILimitsOfAuthorityMasterTrxValue trxValue = null;
		if (anICMSTrxValue != null) {
			trxValue = new OBLimitsOfAuthorityMasterTrxValue(anICMSTrxValue);
		} else {
			trxValue = new OBLimitsOfAuthorityMasterTrxValue();
		}
		trxValue = formulateTrxValue(anITrxContext,
				(ILimitsOfAuthorityMasterTrxValue) trxValue);
		trxValue.setStaging(obj);
		return trxValue;
	}

	private ILimitsOfAuthorityMasterTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx) {
		trx.setTrxContext(anITrxContext);
		trx.setTransactionType(ICMSConstant.LimitsOfAuthorityMaster.INSTANCE_LIMITS_OF_AUTHORITY_MASTER);
		return trx;
	}

	private ILimitsOfAuthorityMasterTrxValue operate(ILimitsOfAuthorityMasterTrxValue trx,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(trx, anOBCMSTrxParameter);
		return (ILimitsOfAuthorityMasterTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (LimitsOfAuthorityMasterException ex) {
			throw new LimitsOfAuthorityMasterException("ERROR--Cannot Get the LimitsOfAuthorityMaster Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new LimitsOfAuthorityMasterException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new LimitsOfAuthorityMasterException("ERROR--Cannot Get the LimitsOfAuthorityMaster Controller.");
		}
	}
	
	
	public SearchResult getAllActual() throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException {
		try{
			return getBusManager().getAllLimitsOfAuthority();
		}catch (Exception e) {
			throw new LimitsOfAuthorityMasterException("ERROR- Cannot retrive list from database.");
			
		}
    }

	public ILimitsOfAuthorityMasterTrxValue getByTrxID(String aTrxID)
			throws LimitsOfAuthorityMasterException, TransactionException, CommandProcessingException {
		ILimitsOfAuthorityMasterTrxValue trxValue = new OBLimitsOfAuthorityMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.LimitsOfAuthorityMaster.INSTANCE_LIMITS_OF_AUTHORITY_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_READ_LIMITS_OF_AUTHORITY_ID);
		return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue getTrxValue(long id) throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new LimitsOfAuthorityMasterException("Invalid Id");
       }
	   
	   ILimitsOfAuthorityMasterTrxValue trxValue = new OBLimitsOfAuthorityMasterTrxValue();
       trxValue.setReferenceID(String.valueOf(id));
       trxValue.setTransactionType(ICMSConstant.LimitsOfAuthorityMaster.INSTANCE_LIMITS_OF_AUTHORITY_MASTER);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_READ_LIMITS_OF_AUTHORITY);
       return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue checkerApprove(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (trx == null) {
			throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
		}
		trx = formulateTrxValue(anITrxContext, trx);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_CHECKER_APPROVE_LIMITS_OF_AUTHORITY);
		return operate(trx, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerUpdateSaveUpdate(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx, ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
		}
		ILimitsOfAuthorityMasterTrxValue trxValue = formulateTrxValue(anITrxContext, trx,
				obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_SAVE_UPDATE_LIMITS_OF_AUTHORITY);
		return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerSave(ITrxContext anITrxContext,
			ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
		}

		ILimitsOfAuthorityMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_SAVE_LIMITS_OF_AUTHORITY);
		return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerUpdate(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx, ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
		}
		ILimitsOfAuthorityMasterTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_UPDATE_LIMITS_OF_AUTHORITY);
		return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue checkerReject(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
	     }
	     if (trx == null) {
	    	 throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
	     }
	     trx = formulateTrxValue(anITrxContext, trx);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_CHECKER_REJECT_LIMITS_OF_AUTHORITY);
	     return operate(trx, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerEditRejected(
			ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx, ILimitsOfAuthorityMaster obj)
					throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_EDIT_REJECTED_LIMITS_OF_AUTHORITY);
        return operate(trx, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerDelete(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx, ILimitsOfAuthorityMaster obj) 
		 	throws RelationshipMgrException,TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_DELETE_LIMITS_OF_AUTHORITY); 
        return operate(trx, param);
		}
	
	public ILimitsOfAuthorityMasterTrxValue makerCloseRejected(
			ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx)
			throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CLOSE_REJECTED_LIMITS_OF_AUTHORITY);
        return operate(trx, param);
	}	

	public ILimitsOfAuthorityMasterTrxValue makerUpdateSaveCreate(ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx, ILimitsOfAuthorityMaster obj)
			throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMaster to be updated is null !!!");
		}
		ILimitsOfAuthorityMasterTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CREATE_LIMITS_OF_AUTHORITY);
		return operate(trxValue, param);
	}
	
	public ILimitsOfAuthorityMasterTrxValue makerCloseDraft(
			ITrxContext anITrxContext,
			ILimitsOfAuthorityMasterTrxValue trx)
			throws LimitsOfAuthorityMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new LimitsOfAuthorityMasterException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new LimitsOfAuthorityMasterException("The ILimitsOfAuthorityMasterTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CLOSE_DRAFT_LIMITS_OF_AUTHORITY);
        return operate(trx, param);
	}
}