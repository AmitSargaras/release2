package com.integrosys.cms.app.excLineforstpsrm.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IBusManager;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.trx.IExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.excLineforstpsrm.trx.OBExcLineForSTPSRMTrxValue;
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
	
	public IExcLineForSTPSRMTrxValue makerCreate(ITrxContext anITrxContext,
			IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new ExcLineForSTPSRMException("The Exclusion Line for STP for SRM to be updated is null !!!");
		}

		IExcLineForSTPSRMTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_EXC_LINE_FR_STP_SRM);
		return operate(trxValue, param);
	}
	
	
	
	private IExcLineForSTPSRMTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IExcLineForSTPSRM obj) {
		IExcLineForSTPSRMTrxValue trxValue = null;
		if (anICMSTrxValue != null) {
			trxValue = new OBExcLineForSTPSRMTrxValue(anICMSTrxValue);
		} else {
			trxValue = new OBExcLineForSTPSRMTrxValue();
		}
		trxValue = formulateTrxValue(anITrxContext,
				(IExcLineForSTPSRMTrxValue) trxValue);
		trxValue.setStaging(obj);
		return trxValue;
	}

	private IExcLineForSTPSRMTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx) {
		trx.setTrxContext(anITrxContext);
		trx.setTransactionType(ICMSConstant.INSTANCE_EXC_LINE_FR_STP_SRM);
		return trx;
	}

	private IExcLineForSTPSRMTrxValue operate(IExcLineForSTPSRMTrxValue trx,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(trx, anOBCMSTrxParameter);
		return (IExcLineForSTPSRMTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (ExcLineForSTPSRMException ex) {
			throw new ExcLineForSTPSRMException("ERROR--Cannot Get the Exclusion Line for STP for SRM Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new ExcLineForSTPSRMException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new ExcLineForSTPSRMException("ERROR--Cannot Get the Exclusion Line for STP for SRM Controller.");
		}
	}
	
	
	public SearchResult getAllActual() throws ExcLineForSTPSRMException,TrxParameterException,TransactionException {
		try{
			return getBusManager().getAll();
		}catch (Exception e) {
			throw new ExcLineForSTPSRMException("ERROR- Cannot retrive list from database.");
			
		}
    }

	public IExcLineForSTPSRMTrxValue getByTrxID(String aTrxID)
			throws ExcLineForSTPSRMException, TransactionException, CommandProcessingException {
		IExcLineForSTPSRMTrxValue trxValue = new OBExcLineForSTPSRMTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_EXC_LINE_FR_STP_SRM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_EXC_LINE_FR_STP_SRM_ID);
		return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue getTrxValue(long id) throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new ExcLineForSTPSRMException("Invalid Id");
       }
	   
	   IExcLineForSTPSRMTrxValue trxValue = new OBExcLineForSTPSRMTrxValue();
       trxValue.setReferenceID(String.valueOf(id));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_EXC_LINE_FR_STP_SRM);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_EXC_LINE_FR_STP_SRM);
       return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue checkerApprove(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (trx == null) {
			throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
		}
		trx = formulateTrxValue(anITrxContext, trx);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_EXC_LINE_FR_STP_SRM);
		return operate(trx, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerUpdateSaveUpdate(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
		}
		IExcLineForSTPSRMTrxValue trxValue = formulateTrxValue(anITrxContext, trx,
				obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_EXC_LINE_FR_STP_SRM);
		return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerSave(ITrxContext anITrxContext,
			IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
		}

		IExcLineForSTPSRMTrxValue trxValue = formulateTrxValue(anITrxContext, null, obj);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_EXC_LINE_FR_STP_SRM);
		return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerUpdate(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
		}
		IExcLineForSTPSRMTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_EXC_LINE_FR_STP_SRM);
		return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue checkerReject(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
	     }
	     if (trx == null) {
	    	 throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
	     }
	     trx = formulateTrxValue(anITrxContext, trx);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_EXC_LINE_FR_STP_SRM);
	     return operate(trx, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerEditRejected(
			ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj)
					throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_EXC_LINE_FR_STP_SRM);
        return operate(trx, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerDelete(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj) 
		 	throws RelationshipMgrException,TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
        }
        if (obj == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
        }
        trx = formulateTrxValue(anITrxContext, trx, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_EXC_LINE_FR_STP_SRM); 
        return operate(trx, param);
		}
	
	public IExcLineForSTPSRMTrxValue makerCloseRejected(
			ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx)
			throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_EXC_LINE_FR_STP_SRM);
        return operate(trx, param);
	}	

	public IExcLineForSTPSRMTrxValue makerUpdateSaveCreate(ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx, IExcLineForSTPSRM obj)
			throws ExcLineForSTPSRMException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
		}
		if (obj == null) {
			throw new ExcLineForSTPSRMException("The IExcLineForSTPSRM to be updated is null !!!");
		}
		IExcLineForSTPSRMTrxValue trxValue = formulateTrxValue(anITrxContext, trx,obj);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_EXC_LINE_FR_STP_SRM);
		return operate(trxValue, param);
	}
	
	public IExcLineForSTPSRMTrxValue makerCloseDraft(
			ITrxContext anITrxContext,
			IExcLineForSTPSRMTrxValue trx)
			throws ExcLineForSTPSRMException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ExcLineForSTPSRMException("The ITrxContext is null!!!");
        }
        if (trx == null) {
            throw new ExcLineForSTPSRMException("The IExcLineForSTPSRMTrxValue to be updated is null!!!");
        }
        trx = formulateTrxValue(anITrxContext, trx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_EXC_LINE_FR_STP_SRM);
        return operate(trx, param);
	}
}