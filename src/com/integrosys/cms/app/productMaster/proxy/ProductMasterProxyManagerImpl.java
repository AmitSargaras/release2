package com.integrosys.cms.app.productMaster.proxy;

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

public class ProductMasterProxyManagerImpl implements IProductMasterProxyManager{

	
	private IProductMasterBusManager productMasterBusManager;

	private IProductMasterBusManager stagingProductMasterBusManager;
	
	private ITrxControllerFactory trxControllerFactory;

	public IProductMasterBusManager getProductMasterBusManager() {
		return productMasterBusManager;
	}

	public void setProductMasterBusManager(IProductMasterBusManager productMasterBusManager) {
		this.productMasterBusManager = productMasterBusManager;
	}

	public IProductMasterBusManager getStagingProductMasterBusManager() {
		return stagingProductMasterBusManager;
	}

	public void setStagingProductMasterBusManager(IProductMasterBusManager stagingProductMasterBusManager) {
		this.stagingProductMasterBusManager = stagingProductMasterBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create ProductMaster
	 */
	public IProductMasterTrxValue makerCreateProductMaster(ITrxContext anITrxContext,
			IProductMaster anICCProductMaster)
			throws ProductMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ProductMasterException("The ITrxContext is null!!!");
		}
		if (anICCProductMaster == null) {
			throw new ProductMasterException("The ICCProductMaster to be updated is null !!!");
		}

		IProductMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCProductMaster);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_MASTER);
		return operate(trxValue, param);
	}
	
	
	
	private IProductMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IProductMaster anIProductMaster) {
		IProductMasterTrxValue ccProductMasterTrxValue = null;
		if (anICMSTrxValue != null) {
			ccProductMasterTrxValue = new OBProductMasterTrxValue(anICMSTrxValue);
		} else {
			ccProductMasterTrxValue = new OBProductMasterTrxValue();
		}
		ccProductMasterTrxValue = formulateTrxValue(anITrxContext,
				(IProductMasterTrxValue) ccProductMasterTrxValue);
		ccProductMasterTrxValue.setStagingProductMaster(anIProductMaster);
		return ccProductMasterTrxValue;
	}

	private IProductMasterTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IProductMasterTrxValue anIProductMasterTrxValue) {
		anIProductMasterTrxValue.setTrxContext(anITrxContext);
		anIProductMasterTrxValue.setTransactionType(ICMSConstant.INSTANCE_PRODUCT_MASTER);
		return anIProductMasterTrxValue;
	}

	private IProductMasterTrxValue operate(IProductMasterTrxValue anIProductMasterTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ProductMasterException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIProductMasterTrxValue, anOBCMSTrxParameter);
		return (IProductMasterTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ProductMasterException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (ProductMasterException ex) {
			throw new ProductMasterException("ERROR--Cannot Get the ProductMaster Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new ProductMasterException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new ProductMasterException("ERROR--Cannot Get the ProductMaster Controller.");
		}
	}
	
	
	/**
	 * @return List of all ProductMaster
	 */
	
	public SearchResult getAllActualProductMaster()throws ProductMasterException,TrxParameterException,TransactionException {
		try{
			return getProductMasterBusManager().getAllProductMaster( );
		}catch (Exception e) {
			throw new ProductMasterException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return ProductMaster TRX value according to trxId .
	 */

	public IProductMasterTrxValue getProductMasterByTrxID(String aTrxID)
			throws ProductMasterException, TransactionException, CommandProcessingException {
		IProductMasterTrxValue trxValue = new OBProductMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PRODUCT_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PRODUCT_MASTER_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve ProductMaster according to criteria .
	 */

	public IProductMasterTrxValue checkerApproveProductMaster(ITrxContext anITrxContext,
			IProductMasterTrxValue anIProductMasterTrxValue)
			throws ProductMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ProductMasterException("The ITrxContext is null!!!");
		}
		if (anIProductMasterTrxValue == null) {
			throw new ProductMasterException("The IProductMasterTrxValue to be updated is null!!!");
		}
		anIProductMasterTrxValue = formulateTrxValue(anITrxContext, anIProductMasterTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PRODUCT_MASTER);
		return operate(anIProductMasterTrxValue, param);
	}
	
	public boolean isProductCodeUnique(String ProductCode) {
		return getProductMasterBusManager().isProductCodeUnique(ProductCode);
	}
	
	/**
	 * @return Maker Update ProductMaster
	 */

	public IProductMasterTrxValue makerUpdateSaveUpdateProductMaster(ITrxContext anITrxContext,
			IProductMasterTrxValue anICCProductMasterTrxValue, IProductMaster anICCProductMaster)
			throws ProductMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ProductMasterException("The ITrxContext is null!!!");
		}
		if (anICCProductMaster == null) {
			throw new ProductMasterException("The ICCProductMaster to be updated is null !!!");
		}
		IProductMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue,
				anICCProductMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PRODUCT_MASTER);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save ProductMaster
	 */

	public IProductMasterTrxValue makerSaveProductMaster(ITrxContext anITrxContext,
			IProductMaster anICCProductMaster)
			throws ProductMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ProductMasterException("The ITrxContext is null!!!");
		}
		if (anICCProductMaster == null) {
			throw new ProductMasterException("The ICCProductMaster to be updated is null !!!");
		}

		IProductMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCProductMaster);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_PRODUCT_MASTER);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  Product Master TRX value  .
	 */
	public IProductMasterTrxValue getProductMasterTrxValue(long productCode) throws ProductMasterException, TrxParameterException, TransactionException {
		if (productCode == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid productCode Id");
       }
		IProductMasterTrxValue trxValue = new OBProductMasterTrxValue();
       trxValue.setReferenceID(String.valueOf(productCode));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_PRODUCT_MASTER);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_PRODUCT_MASTER);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update ProductMaster
	 */

	public IProductMasterTrxValue makerUpdateProductMaster(OBTrxContext anITrxContext,
			IProductMasterTrxValue anICCProductMasterTrxValue, OBProductMaster anICCProductMaster)
			throws ProductMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ProductMasterException("The ITrxContext is null!!!");
		}
		if (anICCProductMaster == null) {
			throw new ProductMasterException("The ICCProductMaster to be updated is null !!!");
		}
		IProductMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue,anICCProductMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PRODUCT_MASTER);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IProductMasterTrxValue checkerRejectProductMaster(ITrxContext anITrxContext,
			IProductMasterTrxValue anICCProductMasterTrxValue)
			throws ProductMasterException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new ProductMasterException("The ITrxContext is null!!!");
	     }
	     if (anICCProductMasterTrxValue == null) {
	    	 throw new ProductMasterException("The IProductMasterTrxValue to be updated is null!!!");
	     }
	     anICCProductMasterTrxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PRODUCT_MASTER);
	     return operate(anICCProductMasterTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public IProductMasterTrxValue makerEditRejectedProductMaster(
				ITrxContext anITrxContext,
				IProductMasterTrxValue anICCProductMasterTrxValue, IProductMaster anIProductMaster)
						throws ProductMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new ProductMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCProductMasterTrxValue == null) {
	            throw new ProductMasterException("The IProductMasterTrxValue to be updated is null!!!");
	        }
	        if (anIProductMaster == null) {
	            throw new ProductMasterException("The IProductMaster to be updated is null !!!");
	        }
	        anICCProductMasterTrxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue, anIProductMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PRODUCT_MASTER);
	        return operate(anICCProductMasterTrxValue, param);
		}
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IProductMasterTrxValue makerCloseRejectedProductMaster(
				ITrxContext anITrxContext,
				IProductMasterTrxValue anICCProductMasterTrxValue)
				throws ProductMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCProductMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCProductMasterTrxValue to be updated is null!!!");
	        }
	        anICCProductMasterTrxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PRODUCT_MASTER);
	        return operate(anICCProductMasterTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create ProductMaster
		 */

		public IProductMasterTrxValue makerUpdateSaveCreateProductMaster(ITrxContext anITrxContext,
				IProductMasterTrxValue anICCProductMasterTrxValue, IProductMaster anICCProductMaster)
				throws ProductMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new ProductMasterException("The ITrxContext is null!!!");
			}
			if (anICCProductMaster == null) {
				throw new ProductMasterException("The ICCProductMaster to be updated is null !!!");
			}
			IProductMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue,anICCProductMaster);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PRODUCT_MASTER);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IProductMasterTrxValue makerCloseDraftProductMaster(
				ITrxContext anITrxContext,
				IProductMasterTrxValue anICCProductMasterTrxValue)
				throws ProductMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCProductMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCProductMasterTrxValue to be updated is null!!!");
	        }
	        anICCProductMasterTrxValue = formulateTrxValue(anITrxContext, anICCProductMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PRODUCT_MASTER);
	        return operate(anICCProductMasterTrxValue, param);
		}
		
		/**
		 * @return List of all ProductMaster
		 */
		
		public SearchResult getAllFilteredActualProductMaster(String code,String name)throws ProductMasterException,TrxParameterException,TransactionException {
			try{
				return getProductMasterBusManager().getAllFilteredProductMaster(code,name);
			}catch (Exception e) {
				throw new ProductMasterException("ERROR- Cannot retrive list from database.");
			}
	    }
}
