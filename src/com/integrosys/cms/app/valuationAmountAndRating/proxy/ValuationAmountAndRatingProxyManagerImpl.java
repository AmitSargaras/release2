package com.integrosys.cms.app.valuationAmountAndRating.proxy;

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
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRatingBusManager;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class ValuationAmountAndRatingProxyManagerImpl implements IValuationAmountAndRatingProxyManager{

	
	private IValuationAmountAndRatingBusManager valuationAmountAndRatingBusManager;

	private IValuationAmountAndRatingBusManager stagingValuationAmountAndRatingBusManager;
	
	private ITrxControllerFactory trxControllerFactory;  

	public IValuationAmountAndRatingBusManager getValuationAmountAndRatingBusManager() {
		return valuationAmountAndRatingBusManager;
	}

	public void setValuationAmountAndRatingBusManager(IValuationAmountAndRatingBusManager valuationAmountAndRatingBusManager) {
		this.valuationAmountAndRatingBusManager = valuationAmountAndRatingBusManager;
	}

	public IValuationAmountAndRatingBusManager getStagingValuationAmountAndRatingBusManager() {
		return stagingValuationAmountAndRatingBusManager;
	}

	public void setStagingValuationAmountAndRatingBusManager(IValuationAmountAndRatingBusManager stagingValuationAmountAndRatingBusManager) {
		this.stagingValuationAmountAndRatingBusManager = stagingValuationAmountAndRatingBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create ValuationAmountAndRating
	 */
	public IValuationAmountAndRatingTrxValue makerCreateValuationAmountAndRating(ITrxContext anITrxContext,
			IValuationAmountAndRating anICCValuationAmountAndRating)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
		}
		if (anICCValuationAmountAndRating == null) {
			throw new ValuationAmountAndRatingException("The ICCValuationAmountAndRating to be updated is null !!!");
		}

		IValuationAmountAndRatingTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCValuationAmountAndRating);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AMOUNT_AND_RATING);
		return operate(trxValue, param);
	}
	
	
	
	private IValuationAmountAndRatingTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IValuationAmountAndRating anIValuationAmountAndRating) {
		IValuationAmountAndRatingTrxValue ccValuationAmountAndRatingTrxValue = null;
		if (anICMSTrxValue != null) {
			ccValuationAmountAndRatingTrxValue = new OBValuationAmountAndRatingTrxValue(anICMSTrxValue);
		} else {
			ccValuationAmountAndRatingTrxValue = new OBValuationAmountAndRatingTrxValue();
		}
		ccValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext,
				(IValuationAmountAndRatingTrxValue) ccValuationAmountAndRatingTrxValue);
		ccValuationAmountAndRatingTrxValue.setStagingValuationAmountAndRating(anIValuationAmountAndRating);
		return ccValuationAmountAndRatingTrxValue;
	}

	private IValuationAmountAndRatingTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IValuationAmountAndRatingTrxValue anIValuationAmountAndRatingTrxValue) {
		anIValuationAmountAndRatingTrxValue.setTrxContext(anITrxContext);
		anIValuationAmountAndRatingTrxValue.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AMOUNT_AND_RATING);
		return anIValuationAmountAndRatingTrxValue;
	}

	private IValuationAmountAndRatingTrxValue operate(IValuationAmountAndRatingTrxValue anIValuationAmountAndRatingTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIValuationAmountAndRatingTrxValue, anOBCMSTrxParameter);
		return (IValuationAmountAndRatingTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (ValuationAmountAndRatingException ex) {
			throw new ValuationAmountAndRatingException("ERROR--Cannot Get the ValuationAmountAndRating Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new ValuationAmountAndRatingException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new ValuationAmountAndRatingException("ERROR--Cannot Get the ValuationAmountAndRating Controller.");
		}
	}
	
	
	/**
	 * @return List of all ValuationAmountAndRating
	 */
	
	public SearchResult getAllActualValuationAmountAndRating()throws ValuationAmountAndRatingException,TrxParameterException,TransactionException {
		try{
			return getValuationAmountAndRatingBusManager().getAllValuationAmountAndRating( );
		}catch (Exception e) {
			throw new ValuationAmountAndRatingException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return ValuationAmountAndRating TRX value according to trxId .
	 */

	public IValuationAmountAndRatingTrxValue getValuationAmountAndRatingByTrxID(String aTrxID)
			throws ValuationAmountAndRatingException, TransactionException, CommandProcessingException {
		IValuationAmountAndRatingTrxValue trxValue = new OBValuationAmountAndRatingTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AMOUNT_AND_RATING);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_VALUATION_AMOUNT_AND_RATING_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve ValuationAmountAndRating according to criteria .
	 */

	public IValuationAmountAndRatingTrxValue checkerApproveValuationAmountAndRating(ITrxContext anITrxContext,
			IValuationAmountAndRatingTrxValue anIValuationAmountAndRatingTrxValue)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
		}
		if (anIValuationAmountAndRatingTrxValue == null) {
			throw new ValuationAmountAndRatingException("The IValuationAmountAndRatingTrxValue to be updated is null!!!");
		}
		anIValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anIValuationAmountAndRatingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AMOUNT_AND_RATING);
		return operate(anIValuationAmountAndRatingTrxValue, param);
	}
	
	public boolean isProductCodeUnique(String ProductCode) {
		return getValuationAmountAndRatingBusManager().isProductCodeUnique(ProductCode);
	}
	
	/**
	 * @return Maker Update ValuationAmountAndRating
	 */

	public IValuationAmountAndRatingTrxValue makerUpdateSaveUpdateValuationAmountAndRating(ITrxContext anITrxContext,
			IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue, IValuationAmountAndRating anICCValuationAmountAndRating)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
		}
		if (anICCValuationAmountAndRating == null) {
			throw new ValuationAmountAndRatingException("The ICCValuationAmountAndRating to be updated is null !!!");
		}
		IValuationAmountAndRatingTrxValue trxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue,
				anICCValuationAmountAndRating);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_VALUATION_AMOUNT_AND_RATING);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save ValuationAmountAndRating
	 */

	public IValuationAmountAndRatingTrxValue makerSaveValuationAmountAndRating(ITrxContext anITrxContext,
			IValuationAmountAndRating anICCValuationAmountAndRating)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
		}
		if (anICCValuationAmountAndRating == null) {
			throw new ValuationAmountAndRatingException("The ICCValuationAmountAndRating to be updated is null !!!");
		}

		IValuationAmountAndRatingTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCValuationAmountAndRating);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_VALUATION_AMOUNT_AND_RATING);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  Product Master TRX value  .
	 */
	public IValuationAmountAndRatingTrxValue getValuationAmountAndRatingTrxValue(long productCode) throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (productCode == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid productCode Id");
       }
		IValuationAmountAndRatingTrxValue trxValue = new OBValuationAmountAndRatingTrxValue();
       trxValue.setReferenceID(String.valueOf(productCode));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AMOUNT_AND_RATING);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_VALUATION_AMOUNT_AND_RATING);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update ValuationAmountAndRating
	 */

	public IValuationAmountAndRatingTrxValue makerUpdateValuationAmountAndRating(OBTrxContext anITrxContext,
			IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue, OBValuationAmountAndRating anICCValuationAmountAndRating)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
		}
		if (anICCValuationAmountAndRating == null) {
			throw new ValuationAmountAndRatingException("The ICCValuationAmountAndRating to be updated is null !!!");
		}
		IValuationAmountAndRatingTrxValue trxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue,anICCValuationAmountAndRating);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_VALUATION_AMOUNT_AND_RATING);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IValuationAmountAndRatingTrxValue checkerRejectValuationAmountAndRating(ITrxContext anITrxContext,
			IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue)
			throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
	     }
	     if (anICCValuationAmountAndRatingTrxValue == null) {
	    	 throw new ValuationAmountAndRatingException("The IValuationAmountAndRatingTrxValue to be updated is null!!!");
	     }
	     anICCValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AMOUNT_AND_RATING);
	     return operate(anICCValuationAmountAndRatingTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public IValuationAmountAndRatingTrxValue makerEditRejectedValuationAmountAndRating(
				ITrxContext anITrxContext,
				IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue, IValuationAmountAndRating anIValuationAmountAndRating)
						throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
	        }
	        if (anICCValuationAmountAndRatingTrxValue == null) {
	            throw new ValuationAmountAndRatingException("The IValuationAmountAndRatingTrxValue to be updated is null!!!");
	        }
	        if (anIValuationAmountAndRating == null) {
	            throw new ValuationAmountAndRatingException("The IValuationAmountAndRating to be updated is null !!!");
	        }
	        anICCValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue, anIValuationAmountAndRating);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_VALUATION_AMOUNT_AND_RATING);
	        return operate(anICCValuationAmountAndRatingTrxValue, param);
		}
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IValuationAmountAndRatingTrxValue makerDeleteValuationAmountAndRating(ITrxContext anITrxContext,
				IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue, IValuationAmountAndRating anIValuationAmountAndRating) 
			 	throws RelationshipMgrException,TrxParameterException,TransactionException {
			if (anITrxContext == null) {
	            throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
	        }
	        if (anICCValuationAmountAndRatingTrxValue == null) {
	            throw new ValuationAmountAndRatingException("The IValuationAmountAndRatingTrxValue to be updated is null!!!");
	        }
	        if (anIValuationAmountAndRating == null) {
	            throw new ValuationAmountAndRatingException("The IValuationAmountAndRating to be updated is null !!!");
	        }
	        anICCValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue, anIValuationAmountAndRating);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_VALUATION_AMOUNT_AND_RATING); 
	        return operate(anICCValuationAmountAndRatingTrxValue, param);
			}
		
		
		
		public IValuationAmountAndRatingTrxValue makerCloseRejectedValuationAmountAndRating(
				ITrxContext anITrxContext,
				IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue)
				throws ValuationAmountAndRatingException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCValuationAmountAndRatingTrxValue == null) {
	            throw new FCCBranchException("The ICCValuationAmountAndRatingTrxValue to be updated is null!!!");
	        }
	        anICCValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_VALUATION_AMOUNT_AND_RATING);
	        return operate(anICCValuationAmountAndRatingTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create ValuationAmountAndRating
		 */

		public IValuationAmountAndRatingTrxValue makerUpdateSaveCreateValuationAmountAndRating(ITrxContext anITrxContext,
				IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue, IValuationAmountAndRating anICCValuationAmountAndRating)
				throws ValuationAmountAndRatingException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new ValuationAmountAndRatingException("The ITrxContext is null!!!");
			}
			if (anICCValuationAmountAndRating == null) {
				throw new ValuationAmountAndRatingException("The ICCValuationAmountAndRating to be updated is null !!!");
			}
			IValuationAmountAndRatingTrxValue trxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue,anICCValuationAmountAndRating);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AMOUNT_AND_RATING);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IValuationAmountAndRatingTrxValue makerCloseDraftValuationAmountAndRating(
				ITrxContext anITrxContext,
				IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue)
				throws ValuationAmountAndRatingException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCValuationAmountAndRatingTrxValue == null) {
	            throw new FCCBranchException("The ICCValuationAmountAndRatingTrxValue to be updated is null!!!");
	        }
	        anICCValuationAmountAndRatingTrxValue = formulateTrxValue(anITrxContext, anICCValuationAmountAndRatingTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_VALUATION_AMOUNT_AND_RATING);
	        return operate(anICCValuationAmountAndRatingTrxValue, param);
		}
		
		/**
		 * @return List of all ValuationAmountAndRating
		 */
		
		public SearchResult getAllFilteredActualValuationAmountAndRating(String code,String name)throws ValuationAmountAndRatingException,TrxParameterException,TransactionException {
			try{
				return getValuationAmountAndRatingBusManager().getAllFilteredValuationAmountAndRating(code,name);
			}catch (Exception e) {
				throw new ValuationAmountAndRatingException("ERROR- Cannot retrive list from database.");
			}
	    }

}
