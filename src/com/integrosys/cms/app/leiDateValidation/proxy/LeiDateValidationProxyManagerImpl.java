package com.integrosys.cms.app.leiDateValidation.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationBusManager;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class LeiDateValidationProxyManagerImpl implements ILeiDateValidationProxyManager {

	private ILeiDateValidationBusManager leiDateValidationBusManager;

	private ILeiDateValidationBusManager stagingLeiDateValidationBusManager;
	
	private ITrxControllerFactory trxControllerFactory;

	public ILeiDateValidationBusManager getLeiDateValidationBusManager() {
		return leiDateValidationBusManager;
	}

	public void setLeiDateValidationBusManager(ILeiDateValidationBusManager leiDateValidationBusManager) {
		this.leiDateValidationBusManager = leiDateValidationBusManager;
	}

	public ILeiDateValidationBusManager getStagingLeiDateValidationBusManager() {
		return stagingLeiDateValidationBusManager;
	}

	public void setStagingLeiDateValidationBusManager(ILeiDateValidationBusManager stagingLeiDateValidationBusManager) {
		this.stagingLeiDateValidationBusManager = stagingLeiDateValidationBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create LeiDateValidation
	 */
	public ILeiDateValidationTrxValue makerCreateLeiDateValidation(ITrxContext anITrxContext,
			ILeiDateValidation anICCLeiDateValidation)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LeiDateValidationException("The ITrxContext is null!!!");
		}
		if (anICCLeiDateValidation == null) {
			throw new LeiDateValidationException("The ICCLeiDateValidation to be updated is null !!!");
		}

		ILeiDateValidationTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCLeiDateValidation);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_LEI_DATE_VALIDATION);
		return operate(trxValue, param);
	}
	
	
	
	private ILeiDateValidationTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ILeiDateValidation anILeiDateValidation) {
		ILeiDateValidationTrxValue ccLeiDateValidationTrxValue = null;
		if (anICMSTrxValue != null) {
			ccLeiDateValidationTrxValue = new OBLeiDateValidationTrxValue(anICMSTrxValue);
		} else {
			ccLeiDateValidationTrxValue = new OBLeiDateValidationTrxValue();
		}
		ccLeiDateValidationTrxValue = formulateTrxValue(anITrxContext,
				(ILeiDateValidationTrxValue) ccLeiDateValidationTrxValue);
		ccLeiDateValidationTrxValue.setStagingLeiDateValidation(anILeiDateValidation);
		return ccLeiDateValidationTrxValue;
	}

	private ILeiDateValidationTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ILeiDateValidationTrxValue anILeiDateValidationTrxValue) {
		anILeiDateValidationTrxValue.setTrxContext(anITrxContext);
		anILeiDateValidationTrxValue.setTransactionType(ICMSConstant.INSTANCE_LEI_DATE_VALIDATION);
		return anILeiDateValidationTrxValue;
	}

	private ILeiDateValidationTrxValue operate(ILeiDateValidationTrxValue anILeiDateValidationTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anILeiDateValidationTrxValue, anOBCMSTrxParameter);
		return (ILeiDateValidationTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (LeiDateValidationException ex) {
			throw new LeiDateValidationException("ERROR--Cannot Get the LeiDateValidation Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new LeiDateValidationException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new LeiDateValidationException("ERROR--Cannot Get the LeiDateValidation Controller.");
		}
	}
	
	
	/**
	 * @return List of all LeiDateValidation
	 */
	
	public SearchResult getAllActualLeiDateValidation()throws LeiDateValidationException,TrxParameterException,TransactionException {
		try{
			return getLeiDateValidationBusManager().getAllLeiDateValidation( );
		}catch (Exception e) {
			throw new LeiDateValidationException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return LeiDateValidation TRX value according to trxId .
	 */

	public ILeiDateValidationTrxValue getLeiDateValidationByTrxID(String aTrxID)
			throws LeiDateValidationException, TransactionException, CommandProcessingException {
		ILeiDateValidationTrxValue trxValue = new OBLeiDateValidationTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_LEI_DATE_VALIDATION);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_LEI_DATE_VALIDATION_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve LeiDateValidation according to criteria .
	 */

	public ILeiDateValidationTrxValue checkerApproveLeiDateValidation(ITrxContext anITrxContext,
			ILeiDateValidationTrxValue anILeiDateValidationTrxValue)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LeiDateValidationException("The ITrxContext is null!!!");
		}
		if (anILeiDateValidationTrxValue == null) {
			throw new LeiDateValidationException("The ILeiDateValidationTrxValue to be updated is null!!!");
		}
		anILeiDateValidationTrxValue = formulateTrxValue(anITrxContext, anILeiDateValidationTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_LEI_DATE_VALIDATION);
		return operate(anILeiDateValidationTrxValue, param);
	}
	
	public boolean isPartyIDUnique(String PartyID) {
		return getLeiDateValidationBusManager().isPartyIDUnique(PartyID);
	}
	
	/**
	 * @return Maker Update LeiDateValidation
	 */

	public ILeiDateValidationTrxValue makerUpdateSaveUpdateLeiDateValidation(ITrxContext anITrxContext,
			ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue, ILeiDateValidation anICCLeiDateValidation)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LeiDateValidationException("The ITrxContext is null!!!");
		}
		if (anICCLeiDateValidation == null) {
			throw new LeiDateValidationException("The ICCLeiDateValidation to be updated is null !!!");
		}
		ILeiDateValidationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue,
				anICCLeiDateValidation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_LEI_DATE_VALIDATION);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save LeiDateValidation
	 */

	public ILeiDateValidationTrxValue makerSaveLeiDateValidation(ITrxContext anITrxContext,
			ILeiDateValidation anICCLeiDateValidation)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LeiDateValidationException("The ITrxContext is null!!!");
		}
		if (anICCLeiDateValidation == null) {
			throw new LeiDateValidationException("The ICCLeiDateValidation to be updated is null !!!");
		}

		ILeiDateValidationTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCLeiDateValidation);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_LEI_DATE_VALIDATION);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  LEI Date Validation Period Master TRX value  .
	 */
	public ILeiDateValidationTrxValue getLeiDateValidationTrxValue(long partyID) throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (partyID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid partyID Id");
       }
		ILeiDateValidationTrxValue trxValue = new OBLeiDateValidationTrxValue();
       trxValue.setReferenceID(String.valueOf(partyID));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_LEI_DATE_VALIDATION);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_LEI_DATE_VALIDATION);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update LeiDateValidation
	 */

	public ILeiDateValidationTrxValue makerUpdateLeiDateValidation(OBTrxContext anITrxContext,
			ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue, OBLeiDateValidation anICCLeiDateValidation)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new LeiDateValidationException("The ITrxContext is null!!!");
		}
		if (anICCLeiDateValidation == null) {
			throw new LeiDateValidationException("The ICCLeiDateValidation to be updated is null !!!");
		}
		ILeiDateValidationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue,anICCLeiDateValidation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_LEI_DATE_VALIDATION);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public ILeiDateValidationTrxValue checkerRejectLeiDateValidation(ITrxContext anITrxContext,
			ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue)
			throws LeiDateValidationException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new LeiDateValidationException("The ITrxContext is null!!!");
	     }
	     if (anICCLeiDateValidationTrxValue == null) {
	    	 throw new LeiDateValidationException("The ILeiDateValidationTrxValue to be updated is null!!!");
	     }
	     anICCLeiDateValidationTrxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_LEI_DATE_VALIDATION);
	     return operate(anICCLeiDateValidationTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public ILeiDateValidationTrxValue makerEditRejectedLeiDateValidation(
				ITrxContext anITrxContext,
				ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue, ILeiDateValidation anILeiDateValidation)
						throws LeiDateValidationException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new LeiDateValidationException("The ITrxContext is null!!!");
	        }
	        if (anICCLeiDateValidationTrxValue == null) {
	            throw new LeiDateValidationException("The ILeiDateValidationTrxValue to be updated is null!!!");
	        }
	        if (anILeiDateValidation == null) {
	            throw new LeiDateValidationException("The ILeiDateValidation to be updated is null !!!");
	        }
	        anICCLeiDateValidationTrxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue, anILeiDateValidation);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_LEI_DATE_VALIDATION);
	        return operate(anICCLeiDateValidationTrxValue, param);
		}
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public ILeiDateValidationTrxValue makerCloseRejectedLeiDateValidation(
				ITrxContext anITrxContext,
				ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue)
				throws LeiDateValidationException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCLeiDateValidationTrxValue == null) {
	            throw new FCCBranchException("The ICCLeiDateValidationTrxValue to be updated is null!!!");
	        }
	        anICCLeiDateValidationTrxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_LEI_DATE_VALIDATION);
	        return operate(anICCLeiDateValidationTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create LeiDateValidation
		 */

		public ILeiDateValidationTrxValue makerUpdateSaveCreateLeiDateValidation(ITrxContext anITrxContext,
				ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue, ILeiDateValidation anICCLeiDateValidation)
				throws LeiDateValidationException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new LeiDateValidationException("The ITrxContext is null!!!");
			}
			if (anICCLeiDateValidation == null) {
				throw new LeiDateValidationException("The ICCLeiDateValidation to be updated is null !!!");
			}
			ILeiDateValidationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue,anICCLeiDateValidation);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_LEI_DATE_VALIDATION);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public ILeiDateValidationTrxValue makerCloseDraftLeiDateValidation(
				ITrxContext anITrxContext,
				ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue)
				throws LeiDateValidationException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCLeiDateValidationTrxValue == null) {
	            throw new FCCBranchException("The ICCLeiDateValidationTrxValue to be updated is null!!!");
	        }
	        anICCLeiDateValidationTrxValue = formulateTrxValue(anITrxContext, anICCLeiDateValidationTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_LEI_DATE_VALIDATION);
	        return operate(anICCLeiDateValidationTrxValue, param);
		}
		
		/**
		 * @return List of all LeiDateValidation
		 */
		
		public SearchResult getAllFilteredActualLeiDateValidation(String code,String name)throws LeiDateValidationException,TrxParameterException,TransactionException {
			try{
				return getLeiDateValidationBusManager().getAllFilteredLeiDateValidation(code,name);
			}catch (Exception e) {
				throw new LeiDateValidationException("ERROR- Cannot retrive list from database.");
			}
	    }
}