package com.integrosys.cms.app.newtatmaster.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.ITatMasterBusManager;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class TatmasterProxyManagerImpl implements ITatmasterProxyManager {
	
	
	private ITatMasterBusManager tatMasterBusManager;
	private ITatMasterBusManager stagingtatMasterBusManager;
	
	private ITrxControllerFactory trxControllerFactory;
    
    private ITatMasterBusManager stagingtatMasterFileMapperIdBusManager;
	
	private ITatMasterBusManager tatMasterFileMapperIdBusManager;
	
	
	
	public ITatMasterBusManager getTatMasterBusManager() {
		return tatMasterBusManager;
	}

	public void setTatMasterBusManager(ITatMasterBusManager tatMasterBusManager) {
		this.tatMasterBusManager = tatMasterBusManager;
	}

	public ITatMasterBusManager getStagingtatMasterBusManager() {
		return stagingtatMasterBusManager;
	}

	public void setStagingtatMasterBusManager(ITatMasterBusManager stagingtatMasterBusManager) {
		this.stagingtatMasterBusManager = stagingtatMasterBusManager;
	}
	
	


	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	

	public ITatMasterBusManager getStagingtatMasterFileMapperIdBusManager() {
		return stagingtatMasterFileMapperIdBusManager;
	}

	public void setStagingtatMasterFileMapperIdBusManager(
			ITatMasterBusManager stagingtatMasterFileMapperIdBusManager) {
		this.stagingtatMasterFileMapperIdBusManager = stagingtatMasterFileMapperIdBusManager;
	}

	public ITatMasterBusManager getTatMasterFileMapperIdBusManager() {
		return tatMasterFileMapperIdBusManager;
	}

	public void setTatMasterFileMapperIdBusManager(
			ITatMasterBusManager tatMasterFileMapperIdBusManager) {
		this.tatMasterFileMapperIdBusManager = tatMasterFileMapperIdBusManager;
	}

	public SearchResult getAllTatEvents() throws TatMasterException,TrxParameterException, TransactionException {
		try{


			return getTatMasterBusManager().getAllTatEvents();
		}catch (Exception e) {
			throw new TatMasterException("ERROR- Cannot retrive list from database.");
		}
		
	}
	
	
	public ITatMasterTrxValue getTatMasterTrxValue(long tatCode)throws TatMasterException, TrxParameterException,
			TransactionException {
		if (tatCode == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
		    throw new TatMasterException("Invalid TAT Master Id");
		}
		ITatMasterTrxValue trxValue = new OBTatMasterTrxValue();
		trxValue.setReferenceID(String.valueOf(tatCode));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TAT_MASTER);
		return operate(trxValue, param);
		}
	
	public ITatMasterTrxValue getTatMasterByTrxID(String aTrxID)throws TatMasterException, TransactionException,
	CommandProcessingException {
		ITatMasterTrxValue trxValue = new OBTatMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TAT_MASTER_ID);
		return operate(trxValue, param);
	}

	
	
	public ITatMasterTrxValue makerUpdateTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anICCTatMasterTrxValue, INewTatMaster anICCTatMaster)
			throws TatMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new ComponentException("The ITrxContext is null!!!");
	        }
	        if (anICCTatMaster == null) {
	            throw new ComponentException("The ICCTatMaster to be updated is null !!!");
	        }
	        ITatMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCTatMasterTrxValue, anICCTatMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_TAT_MASTER);
	        return operate(trxValue, param);
	}
	
	
	public ITatMasterTrxValue checkerApproveTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anITatMasterTrxValue)
			throws TatMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new HolidayException("The ITrxContext is null!!!");
        }
        if (anITatMasterTrxValue == null) {
            throw new HolidayException
                    ("The ITatMasterTrxValue to be updated is null!!!");
        }
        anITatMasterTrxValue = formulateTrxValue(anITrxContext, anITatMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_TAT_MASTER);
        return operate(anITatMasterTrxValue, param);
	}
	
	public ITatMasterTrxValue checkerRejectTatMaster(ITrxContext anITrxContext,ITatMasterTrxValue anITatMasterTrxValue) throws ComponentException,
	TrxParameterException, TransactionException {
			if (anITrxContext == null) {
			    throw new ComponentException("The ITrxContext is null!!!");
			}
			if (anITatMasterTrxValue == null) {
			    throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
			}
			anITatMasterTrxValue = formulateTrxValue(anITrxContext, anITatMasterTrxValue);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_TAT_MASTER);
			return operate(anITatMasterTrxValue, param);
}
	
	public ITatMasterTrxValue makerEditRejectedTatMaster(ITrxContext anITrxContext, ITatMasterTrxValue anITatMasterTrxValue,
			INewTatMaster anTatMaster) throws TatMasterException,TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anITatMasterTrxValue == null) {
            throw new ComponentException("The ITatMasterTrxValue to be updated is null!!!");
        }
        if (anTatMaster == null) {
            throw new ComponentException("The INewTatMaster to be updated is null !!!");
        }
        anITatMasterTrxValue = formulateTrxValue(anITrxContext, anITatMasterTrxValue, anTatMaster);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_TAT_MASTER);
        return operate(anITatMasterTrxValue, param);
	}
	
	public ITatMasterTrxValue makerCloseRejectedTatMaster(ITrxContext anITrxContext, ITatMasterTrxValue anITatMasterTrxValue)
			throws TatMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anITatMasterTrxValue == null) {
            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
        }
        anITatMasterTrxValue = formulateTrxValue(anITrxContext, anITatMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TAT_MASTER);
        return operate(anITatMasterTrxValue, param);
	}
	
	
	private ITatMasterTrxValue operate(ITatMasterTrxValue anITatMasterTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws ComponentException,TrxParameterException,TransactionException {
        ICMSTrxResult result = operateForResult(anITatMasterTrxValue, anOBCMSTrxParameter);
        return (ITatMasterTrxValue) result.getTrxValue();
    }
	
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws TatMasterException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (TatMasterException ex) {
			 throw new TatMasterException("ERROR--Cannot Get the Tat Master Controller.");
		 }
		 catch (Exception ex) {
			 throw new TatMasterException("ERROR--Cannot Get the Tat Master Controller.");
		 }
	 }
	 
	 private ITatMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, INewTatMaster anITatMaster) {
		 ITatMasterTrxValue ccTatMasterTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccTatMasterTrxValue = new OBTatMasterTrxValue(anICMSTrxValue);
	        } else {
	        	ccTatMasterTrxValue = new OBTatMasterTrxValue();
	        }
	        ccTatMasterTrxValue = formulateTrxValue(anITrxContext, (ITatMasterTrxValue) ccTatMasterTrxValue);
	        ccTatMasterTrxValue.setStagingtatMaster(anITatMaster);
	        return ccTatMasterTrxValue;
	    }
	 
	 private ITatMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ITatMasterTrxValue anIComponentTrxValue) {
	        anIComponentTrxValue.setTrxContext(anITrxContext);
	        anIComponentTrxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_MASTER);
	        return anIComponentTrxValue;
	    }
 

}
