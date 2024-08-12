package com.integrosys.cms.app.bankingArrangementFacExclusion.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.BankingArrangementFacExclusionException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBusManager;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.IBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.OBBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;

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
	
	public IBankingArrangementFacExclusionTrxValue makerCreate(ITrxContext anITrxContext,
			IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new BankingArrangementFacExclusionException("The BankingArrangementFacExclusion to be updated is null !!!");
		}

		IBankingArrangementFacExclusionTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trxValue, param);
	}
	
	
	
	private IBankingArrangementFacExclusionTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IBankingArrangementFacExclusion obj) {
		IBankingArrangementFacExclusionTrxValue trxValue = null;
		if (anICMSTrxValue != null) {
			trxValue = new OBBankingArrangementFacExclusionTrxValue(anICMSTrxValue);
		} else {
			trxValue = new OBBankingArrangementFacExclusionTrxValue();
		}
		trxValue = formulateTrxValue(anITrxContext,
				(IBankingArrangementFacExclusionTrxValue) trxValue);
		trxValue.setStaging(obj);
		return trxValue;
	}

	private IBankingArrangementFacExclusionTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx) {
		trx.setTrxContext(anITrxContext);
		trx.setTransactionType(ICMSConstant.INSTANCE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return trx;
	}

	private IBankingArrangementFacExclusionTrxValue operate(IBankingArrangementFacExclusionTrxValue trx,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(trx, anOBCMSTrxParameter);
		return (IBankingArrangementFacExclusionTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (BankingArrangementFacExclusionException ex) {
			throw new BankingArrangementFacExclusionException("ERROR--Cannot Get the BankingArrangementFacExclusion Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new BankingArrangementFacExclusionException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new BankingArrangementFacExclusionException("ERROR--Cannot Get the BankingArrangementFacExclusion Controller.");
		}
	}
	
	
	public SearchResult getAllActual() throws BankingArrangementFacExclusionException,TrxParameterException,TransactionException {
		try{
			return getBusManager().getAll();
		}catch (Exception e) {
			throw new BankingArrangementFacExclusionException("ERROR- Cannot retrive list from database.");
			
		}
    }

	public IBankingArrangementFacExclusionTrxValue getByTrxID(String aTrxID)
			throws BankingArrangementFacExclusionException, TransactionException, CommandProcessingException {
		IBankingArrangementFacExclusionTrxValue trxValue = new OBBankingArrangementFacExclusionTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BANKING_ARRANGEMENT_FAC_EXCLUSION_ID);
		return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue getTrxValue(long id) throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new BankingArrangementFacExclusionException("Invalid Id");
       }
	   
	   IBankingArrangementFacExclusionTrxValue trxValue = new OBBankingArrangementFacExclusionTrxValue();
       trxValue.setReferenceID(String.valueOf(id));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_BANKING_ARRANGEMENT_FAC_EXCLUSION);
       return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue checkerApprove(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (trx == null) {
			throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
		}
		trx = formulateTrxValue(anITrxContext, trx);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trx, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerUpdateSaveUpdate(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx, IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
		}
		IBankingArrangementFacExclusionTrxValue trxValue = formulateTrxValue(anITrxContext, trx,
				obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerSave(ITrxContext anITrxContext,
			IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
		}

		IBankingArrangementFacExclusionTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerUpdate(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx, IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
		}
		IBankingArrangementFacExclusionTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue checkerReject(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
	     }
	     if (trx == null) {
	    	 throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
	     }
	     trx = formulateTrxValue(anITrxContext, trx);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION);
	     return operate(trx, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerEditRejected(
			ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx, IBankingArrangementFacExclusion obj)
					throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION);
        return operate(trx, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerDelete(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx, IBankingArrangementFacExclusion obj) 
		 	throws RelationshipMgrException,TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION); 
        return operate(trx, param);
		}
	
	public IBankingArrangementFacExclusionTrxValue makerCloseRejected(
			ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx)
			throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION);
        return operate(trx, param);
	}	

	public IBankingArrangementFacExclusionTrxValue makerUpdateSaveCreate(ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx, IBankingArrangementFacExclusion obj)
			throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusion to be updated is null !!!");
		}
		IBankingArrangementFacExclusionTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION);
		return operate(trxValue, param);
	}
	
	public IBankingArrangementFacExclusionTrxValue makerCloseDraft(
			ITrxContext anITrxContext,
			IBankingArrangementFacExclusionTrxValue trx)
			throws BankingArrangementFacExclusionException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new BankingArrangementFacExclusionException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new BankingArrangementFacExclusionException("The IBankingArrangementFacExclusionTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_BANKING_ARRANGEMENT_FAC_EXCLUSION);
        return operate(trx, param);
	}
}