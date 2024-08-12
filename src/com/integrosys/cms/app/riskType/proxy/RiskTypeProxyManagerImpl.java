package com.integrosys.cms.app.riskType.proxy;

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
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.IRiskTypeBusManager;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class RiskTypeProxyManagerImpl implements IRiskTypeProxyManager{

	
	private IRiskTypeBusManager riskTypeBusManager;

	private IRiskTypeBusManager stagingRiskTypeBusManager;
	
	private ITrxControllerFactory trxControllerFactory;  

	public IRiskTypeBusManager getRiskTypeBusManager() {
		return riskTypeBusManager;
	}

	public void setRiskTypeBusManager(IRiskTypeBusManager riskTypeBusManager) {
		this.riskTypeBusManager = riskTypeBusManager;
	}

	public IRiskTypeBusManager getStagingRiskTypeBusManager() {
		return stagingRiskTypeBusManager;
	}

	public void setStagingRiskTypeBusManager(IRiskTypeBusManager stagingRiskTypeBusManager) {
		this.stagingRiskTypeBusManager = stagingRiskTypeBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create RiskType
	 */
	public IRiskTypeTrxValue makerCreateRiskType(ITrxContext anITrxContext,
			IRiskType anICCRiskType)
			throws RiskTypeException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new RiskTypeException("The ITrxContext is null!!!");
		}
		if (anICCRiskType == null) {
			throw new RiskTypeException("The ICCRiskType to be updated is null !!!");
		}

		IRiskTypeTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRiskType);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RISK_TYPE);
		return operate(trxValue, param);
	}
	
	
	
	private IRiskTypeTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IRiskType anIRiskType) {
		IRiskTypeTrxValue ccRiskTypeTrxValue = null;
		if (anICMSTrxValue != null) {
			ccRiskTypeTrxValue = new OBRiskTypeTrxValue(anICMSTrxValue);
		} else {
			ccRiskTypeTrxValue = new OBRiskTypeTrxValue();
		}
		ccRiskTypeTrxValue = formulateTrxValue(anITrxContext,
				(IRiskTypeTrxValue) ccRiskTypeTrxValue);
		ccRiskTypeTrxValue.setStagingRiskType(anIRiskType);
		return ccRiskTypeTrxValue;
	}

	private IRiskTypeTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IRiskTypeTrxValue anIRiskTypeTrxValue) {
		anIRiskTypeTrxValue.setTrxContext(anITrxContext);
		anIRiskTypeTrxValue.setTransactionType(ICMSConstant.INSTANCE_RISK_TYPE);
		return anIRiskTypeTrxValue;
	}

	private IRiskTypeTrxValue operate(IRiskTypeTrxValue anIRiskTypeTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws RiskTypeException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIRiskTypeTrxValue, anOBCMSTrxParameter);
		return (IRiskTypeTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws RiskTypeException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (RiskTypeException ex) {
			throw new RiskTypeException("ERROR--Cannot Get the RiskType Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new RiskTypeException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new RiskTypeException("ERROR--Cannot Get the RiskType Controller.");
		}
	}
	
	
	/**
	 * @return List of all RiskType
	 */
	
	public SearchResult getAllActualRiskType()throws RiskTypeException,TrxParameterException,TransactionException {
		try{
			return getRiskTypeBusManager().getAllRiskType( );
		}catch (Exception e) {
			throw new RiskTypeException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return RiskType TRX value according to trxId .
	 */

	public IRiskTypeTrxValue getRiskTypeByTrxID(String aTrxID)
			throws RiskTypeException, TransactionException, CommandProcessingException {
		IRiskTypeTrxValue trxValue = new OBRiskTypeTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_RISK_TYPE);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_RISK_TYPE_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve RiskType according to criteria .
	 */

	public IRiskTypeTrxValue checkerApproveRiskType(ITrxContext anITrxContext,
			IRiskTypeTrxValue anIRiskTypeTrxValue)
			throws RiskTypeException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new RiskTypeException("The ITrxContext is null!!!");
		}
		if (anIRiskTypeTrxValue == null) {
			throw new RiskTypeException("The IRiskTypeTrxValue to be updated is null!!!");
		}
		anIRiskTypeTrxValue = formulateTrxValue(anITrxContext, anIRiskTypeTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_RISK_TYPE);
		return operate(anIRiskTypeTrxValue, param);
	}
	
	/*public boolean isProductCodeUnique(String ProductCode) {
		return getRiskTypeBusManager().isProductCodeUnique(ProductCode);
	}*/
	
	/**
	 * @return Maker Update RiskType
	 */

	public IRiskTypeTrxValue makerUpdateSaveUpdateRiskType(ITrxContext anITrxContext,
			IRiskTypeTrxValue anICCRiskTypeTrxValue, IRiskType anICCRiskType)
			throws RiskTypeException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new RiskTypeException("The ITrxContext is null!!!");
		}
		if (anICCRiskType == null) {
			throw new RiskTypeException("The ICCRiskType to be updated is null !!!");
		}
		IRiskTypeTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue,
				anICCRiskType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_RISK_TYPE);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save RiskType
	 */

	public IRiskTypeTrxValue makerSaveRiskType(ITrxContext anITrxContext,
			IRiskType anICCRiskType)
			throws RiskTypeException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new RiskTypeException("The ITrxContext is null!!!");
		}
		if (anICCRiskType == null) {
			throw new RiskTypeException("The ICCRiskType to be updated is null !!!");
		}

		IRiskTypeTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRiskType);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_RISK_TYPE);
		return operate(trxValue, param);
	}
	
	
	 /**
	 * @return  Product Master TRX value  .
	 */
	public IRiskTypeTrxValue getRiskTypeTrxValue(long productCode) throws RiskTypeException, TrxParameterException, TransactionException {
		if (productCode == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid productCode Id");
       }
		IRiskTypeTrxValue trxValue = new OBRiskTypeTrxValue();
       trxValue.setReferenceID(String.valueOf(productCode));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_RISK_TYPE);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_RISK_TYPE);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update RiskType
	 */

	public IRiskTypeTrxValue makerUpdateRiskType(OBTrxContext anITrxContext,
			IRiskTypeTrxValue anICCRiskTypeTrxValue, OBRiskType anICCRiskType)
			throws RiskTypeException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new RiskTypeException("The ITrxContext is null!!!");
		}
		if (anICCRiskType == null) {
			throw new RiskTypeException("The ICCRiskType to be updated is null !!!");
		}
		IRiskTypeTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue,anICCRiskType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_RISK_TYPE);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IRiskTypeTrxValue checkerRejectRiskType(ITrxContext anITrxContext,
			IRiskTypeTrxValue anICCRiskTypeTrxValue)
			throws RiskTypeException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new RiskTypeException("The ITrxContext is null!!!");
	     }
	     if (anICCRiskTypeTrxValue == null) {
	    	 throw new RiskTypeException("The IRiskTypeTrxValue to be updated is null!!!");
	     }
	     anICCRiskTypeTrxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_RISK_TYPE);
	     return operate(anICCRiskTypeTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public IRiskTypeTrxValue makerEditRejectedRiskType(
				ITrxContext anITrxContext,
				IRiskTypeTrxValue anICCRiskTypeTrxValue, IRiskType anIRiskType)
						throws RiskTypeException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new RiskTypeException("The ITrxContext is null!!!");
	        }
	        if (anICCRiskTypeTrxValue == null) {
	            throw new RiskTypeException("The IRiskTypeTrxValue to be updated is null!!!");
	        }
	        if (anIRiskType == null) {
	            throw new RiskTypeException("The IRiskType to be updated is null !!!");
	        }
	        anICCRiskTypeTrxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue, anIRiskType);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RISK_TYPE);
	        return operate(anICCRiskTypeTrxValue, param);
		}
		 /**
		 * @return Maker Close .
		 */
		
		public IRiskTypeTrxValue makerDeleteRiskType(ITrxContext anITrxContext,
				IRiskTypeTrxValue anICCRiskTypeTrxValue, IRiskType anIRiskType) 
			 	throws RelationshipMgrException,TrxParameterException,TransactionException {
			if (anITrxContext == null) {
	            throw new RiskTypeException("The ITrxContext is null!!!");
	        }
	        if (anICCRiskTypeTrxValue == null) {
	            throw new RiskTypeException("The IRiskTypeTrxValue to be updated is null!!!");
	        }
	        if (anIRiskType == null) {
	            throw new RiskTypeException("The IRiskType to be updated is null !!!");
	        }
	        anICCRiskTypeTrxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue, anIRiskType);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_RISK_TYPE); 
	        return operate(anICCRiskTypeTrxValue, param);
			}
		
		
		
		public IRiskTypeTrxValue makerCloseRejectedRiskType(
				ITrxContext anITrxContext,
				IRiskTypeTrxValue anICCRiskTypeTrxValue)
				throws RiskTypeException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCRiskTypeTrxValue == null) {
	            throw new FCCBranchException("The ICCRiskTypeTrxValue to be updated is null!!!");
	        }
	        anICCRiskTypeTrxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RISK_TYPE);
	        return operate(anICCRiskTypeTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create RiskType
		 */

		public IRiskTypeTrxValue makerUpdateSaveCreateRiskType(ITrxContext anITrxContext,
				IRiskTypeTrxValue anICCRiskTypeTrxValue, IRiskType anICCRiskType)
				throws RiskTypeException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new RiskTypeException("The ITrxContext is null!!!");
			}
			if (anICCRiskType == null) {
				throw new RiskTypeException("The ICCRiskType to be updated is null !!!");
			}
			IRiskTypeTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue,anICCRiskType);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RISK_TYPE);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IRiskTypeTrxValue makerCloseDraftRiskType(
				ITrxContext anITrxContext,
				IRiskTypeTrxValue anICCRiskTypeTrxValue)
				throws RiskTypeException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCRiskTypeTrxValue == null) {
	            throw new FCCBranchException("The ICCRiskTypeTrxValue to be updated is null!!!");
	        }
	        anICCRiskTypeTrxValue = formulateTrxValue(anITrxContext, anICCRiskTypeTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_RISK_TYPE);
	        return operate(anICCRiskTypeTrxValue, param);
		}
		
		/**
		 * @return List of all RiskType
		 */
		
		public SearchResult getAllFilteredActualRiskType(String code,String name)throws RiskTypeException,TrxParameterException,TransactionException {
			try{
				return getRiskTypeBusManager().getAllFilteredRiskType(code,name);
			}catch (Exception e) {
				throw new RiskTypeException("ERROR- Cannot retrive list from database.");
			}
	    }

}
