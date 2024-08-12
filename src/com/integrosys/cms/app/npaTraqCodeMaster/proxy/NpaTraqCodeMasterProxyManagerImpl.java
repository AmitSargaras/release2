package com.integrosys.cms.app.npaTraqCodeMaster.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fccBranch.trx.OBFCCBranchTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMasterBusManager;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.OBNpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterBusManager;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.productMaster.trx.OBProductMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class NpaTraqCodeMasterProxyManagerImpl implements INpaTraqCodeMasterProxyManager{

	
	private ITrxControllerFactory trxControllerFactory;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	private INpaTraqCodeMasterBusManager npaTraqCodeMasterBusManager;
	private INpaTraqCodeMasterBusManager stagingNpaTraqCodeMasterBusManager; 
	
	public INpaTraqCodeMasterBusManager getNpaTraqCodeMasterBusManager() {
		return npaTraqCodeMasterBusManager;
	}

	public void setNpaTraqCodeMasterBusManager(INpaTraqCodeMasterBusManager npaTraqCodeMasterBusManager) {
		this.npaTraqCodeMasterBusManager = npaTraqCodeMasterBusManager;
	}

	public INpaTraqCodeMasterBusManager getStagingNpaTraqCodeMasterBusManager() {
		return stagingNpaTraqCodeMasterBusManager;
	}

	public void setStagingNpaTraqCodeMasterBusManager(INpaTraqCodeMasterBusManager stagingNpaTraqCodeMasterBusManager) {
		this.stagingNpaTraqCodeMasterBusManager = stagingNpaTraqCodeMasterBusManager;
	}

	/**
	 * @return Maker Create Npa Traq Code Master
	 */
	public INpaTraqCodeMasterTrxValue makerCreateNpaTraqCodeMaster(ITrxContext anITrxContext,
			INpaTraqCodeMaster anICCNpaTraqCodeMaster)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
		}
		if (anICCNpaTraqCodeMaster == null) {
			throw new NpaTraqCodeMasterException("The ICCNpaTraqCodeMaster to be updated is null !!!");
		}

		INpaTraqCodeMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCNpaTraqCodeMaster);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_NPA_TRAQ_CODE_MASTER);
		return operate(trxValue, param);
	}
	
	
	
	private INpaTraqCodeMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			INpaTraqCodeMaster anINpaTraqCodeMaster) {
		INpaTraqCodeMasterTrxValue ccNpaTraqCodeMasterTrxValue = null;
		if (anICMSTrxValue != null) {
			ccNpaTraqCodeMasterTrxValue = new OBNpaTraqCodeMasterTrxValue(anICMSTrxValue);
		} else {
			ccNpaTraqCodeMasterTrxValue = new OBNpaTraqCodeMasterTrxValue();
		}
		ccNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext,
				(INpaTraqCodeMasterTrxValue) ccNpaTraqCodeMasterTrxValue);
		ccNpaTraqCodeMasterTrxValue.setStagingNpaTraqCodeMaster(anINpaTraqCodeMaster);
		return ccNpaTraqCodeMasterTrxValue;
	}

	private INpaTraqCodeMasterTrxValue formulateTrxValue(ITrxContext anITrxContext,
			INpaTraqCodeMasterTrxValue anINpaTraqCodeMasterTrxValue) {
		anINpaTraqCodeMasterTrxValue.setTrxContext(anITrxContext);
		anINpaTraqCodeMasterTrxValue.setTransactionType(ICMSConstant.INSTANCE_NPA_TRAQ_CODE_MASTER);
		return anINpaTraqCodeMasterTrxValue;
	}

	private INpaTraqCodeMasterTrxValue operate(INpaTraqCodeMasterTrxValue anINpaTraqCodeMasterTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anINpaTraqCodeMasterTrxValue, anOBCMSTrxParameter);
		return (INpaTraqCodeMasterTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (NpaTraqCodeMasterException ex) {
			throw new NpaTraqCodeMasterException("ERROR--Cannot Get the NpaTraqCodeMaster Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new NpaTraqCodeMasterException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new NpaTraqCodeMasterException("ERROR--Cannot Get the NpaTraqCodeMaster Controller.");
		}
	}
	
	
	/**
	 * @return List of all NpaTraqCodeMaster
	 */
	
	public SearchResult getAllActualNpaTraqCodeMaster()throws NpaTraqCodeMasterException,TrxParameterException,TransactionException {
		try{
			return getNpaTraqCodeMasterBusManager().getAllNpaTraqCodeMaster( );
		}catch (Exception e) {
			throw new NpaTraqCodeMasterException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	public boolean isNpaTraqCodeUniqueJdbc(String securityType, String securitySubType, String propertyTypeDesc) {
		return getNpaTraqCodeMasterBusManager().isNpaTraqCodeUniqueJdbc(securityType,securitySubType,propertyTypeDesc);
	}
	
	/**
	 * @return NpaTraqCodeMaster TRX value according to trxId .
	 */

	public INpaTraqCodeMasterTrxValue getNpaTraqCodeMasterByTrxID(String aTrxID)
			throws NpaTraqCodeMasterException, TransactionException, CommandProcessingException {
		INpaTraqCodeMasterTrxValue trxValue = new OBNpaTraqCodeMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_NPA_TRAQ_CODE_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_NPA_TRAQ_CODE_MASTER_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve NpaTraqCodeMaster according to criteria .
	 */

	public INpaTraqCodeMasterTrxValue checkerApproveNpaTraqCodeMaster(ITrxContext anITrxContext,
			INpaTraqCodeMasterTrxValue anINpaTraqCodeMasterTrxValue)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
		}
		if (anINpaTraqCodeMasterTrxValue == null) {
			throw new NpaTraqCodeMasterException("The INpaTraqCodeMasterTrxValue to be updated is null!!!");
		}
		anINpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anINpaTraqCodeMasterTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_NPA_TRAQ_CODE_MASTER);
		return operate(anINpaTraqCodeMasterTrxValue, param);
	}
	
	public boolean isNpaTraqCodeUnique(String npaTraqCode, String securityType, String securitySubType, String propertyTypeDesc) {
		return getNpaTraqCodeMasterBusManager().isNpaTraqCodeUnique(npaTraqCode,securityType,securitySubType,propertyTypeDesc);
	}
	
	/**
	 * @return Maker Update NpaTraqCodeMaster
	 */

	public INpaTraqCodeMasterTrxValue makerUpdateSaveUpdateNpaTraqCodeMaster(ITrxContext anITrxContext,
			INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue, INpaTraqCodeMaster anICCNpaTraqCodeMaster)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
		}
		if (anICCNpaTraqCodeMaster == null) {
			throw new NpaTraqCodeMasterException("The ICCNpaTraqCodeMaster to be updated is null !!!");
		}
		INpaTraqCodeMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue,
				anICCNpaTraqCodeMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_NPA_TRAQ_CODE_MASTER);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save NpaTraqCodeMaster
	 */

	public INpaTraqCodeMasterTrxValue makerSaveNpaTraqCodeMaster(ITrxContext anITrxContext,
			INpaTraqCodeMaster anICCNpaTraqCodeMaster)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
		}
		if (anICCNpaTraqCodeMaster == null) {
			throw new NpaTraqCodeMasterException("The ICCNpaTraqCodeMaster to be updated is null !!!");
		}

		INpaTraqCodeMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCNpaTraqCodeMaster);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_NPA_TRAQ_CODE_MASTER);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  Product Master TRX value  .
	 */
	public INpaTraqCodeMasterTrxValue getNpaTraqCodeMasterTrxValue(long productCode) throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (productCode == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid productCode Id");
       }
		INpaTraqCodeMasterTrxValue trxValue = new OBNpaTraqCodeMasterTrxValue();
       trxValue.setReferenceID(String.valueOf(productCode));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_NPA_TRAQ_CODE_MASTER);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_NPA_TRAQ_CODE_MASTER);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update NpaTraqCodeMaster
	 */

	public INpaTraqCodeMasterTrxValue makerUpdateNpaTraqCodeMaster(OBTrxContext anITrxContext,
			INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue, OBNpaTraqCodeMaster anICCNpaTraqCodeMaster)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
		}
		if (anICCNpaTraqCodeMaster == null) {
			throw new NpaTraqCodeMasterException("The ICCNpaTraqCodeMaster to be updated is null !!!");
		}
		INpaTraqCodeMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue,anICCNpaTraqCodeMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_NPA_TRAQ_CODE_MASTER);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  NPA_TRAQ_CODE_MASTER according to criteria .
	 */
	
	
	public INpaTraqCodeMasterTrxValue checkerRejectNpaTraqCodeMaster(ITrxContext anITrxContext,
			INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue)
			throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
	     }
	     if (anICCNpaTraqCodeMasterTrxValue == null) {
	    	 throw new NpaTraqCodeMasterException("The INpaTraqCodeMasterTrxValue to be updated is null!!!");
	     }
	     anICCNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_NPA_TRAQ_CODE_MASTER);
	     return operate(anICCNpaTraqCodeMasterTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit NPA_TRAQ_CODE_MASTER
		 */
		public INpaTraqCodeMasterTrxValue makerEditRejectedNpaTraqCodeMaster(
				ITrxContext anITrxContext,
				INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue, INpaTraqCodeMaster anINpaTraqCodeMaster)
						throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCNpaTraqCodeMasterTrxValue == null) {
	            throw new NpaTraqCodeMasterException("The INpaTraqCodeMasterTrxValue to be updated is null!!!");
	        }
	        if (anINpaTraqCodeMaster == null) {
	            throw new NpaTraqCodeMasterException("The INpaTraqCodeMaster to be updated is null !!!");
	        }
	        anICCNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue, anINpaTraqCodeMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_NPA_TRAQ_CODE_MASTER);
	        return operate(anICCNpaTraqCodeMasterTrxValue, param);
		}
		 /**
		 * @return Maker Close NPA_TRAQ_CODE_MASTER.
		 */
		
		public INpaTraqCodeMasterTrxValue makerCloseRejectedNpaTraqCodeMaster(
				ITrxContext anITrxContext,
				INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue)
				throws NpaTraqCodeMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCNpaTraqCodeMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCNpaTraqCodeMasterTrxValue to be updated is null!!!");
	        }
	        anICCNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_NPA_TRAQ_CODE_MASTER);
	        return operate(anICCNpaTraqCodeMasterTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create NpaTraqCodeMaster
		 */

		public INpaTraqCodeMasterTrxValue makerUpdateSaveCreateNpaTraqCodeMaster(ITrxContext anITrxContext,
				INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue, INpaTraqCodeMaster anICCNpaTraqCodeMaster)
				throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
			}
			if (anICCNpaTraqCodeMaster == null) {
				throw new NpaTraqCodeMasterException("The ICCNpaTraqCodeMaster to be updated is null !!!");
			}
			INpaTraqCodeMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue,anICCNpaTraqCodeMaster);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_NPA_TRAQ_CODE_MASTER);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close NPA_TRAQ_CODE_MASTER.
		 */
		
		public INpaTraqCodeMasterTrxValue makerCloseDraftNpaTraqCodeMaster(
				ITrxContext anITrxContext,
				INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue)
				throws NpaTraqCodeMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCNpaTraqCodeMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCNpaTraqCodeMasterTrxValue to be updated is null!!!");
	        }
	        anICCNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_NPA_TRAQ_CODE_MASTER);
	        return operate(anICCNpaTraqCodeMasterTrxValue, param);
		}
		
		public INpaTraqCodeMasterTrxValue makerDeleteNpaTraqCodeMaster(
				ITrxContext anITrxContext,
				INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue, INpaTraqCodeMaster anINpaTraqCodeMaster)
						throws NpaTraqCodeMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new NpaTraqCodeMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCNpaTraqCodeMasterTrxValue == null) {
	            throw new NpaTraqCodeMasterException("The INpaTraqCodeMasterTrxValue to be updated is null!!!");
	        }
	        if (anINpaTraqCodeMaster == null) {
	            throw new NpaTraqCodeMasterException("The INpaTraqCodeMaster to be updated is null !!!");
	        }
	        anICCNpaTraqCodeMasterTrxValue = formulateTrxValue(anITrxContext, anICCNpaTraqCodeMasterTrxValue, anINpaTraqCodeMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_NPA_TRAQ_CODE_MASTER);
	        return operate(anICCNpaTraqCodeMasterTrxValue, param);
		}
		
		/**
		 * @return List of all NpaTraqCodeMaster
		 */
		
		/*public SearchResult getAllFilteredActualProductMaster(String code,String name)throws ProductMasterException,TrxParameterException,TransactionException {
			try{
				return getProductMasterBusManager().getAllFilteredProductMaster(code,name);
			}catch (Exception e) {
				throw new ProductMasterException("ERROR- Cannot retrive list from database.");
			}
	    }*/
}
