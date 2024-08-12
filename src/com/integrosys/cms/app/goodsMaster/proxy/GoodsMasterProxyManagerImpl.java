package com.integrosys.cms.app.goodsMaster.proxy;

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
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterBusManager;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class GoodsMasterProxyManagerImpl implements IGoodsMasterProxyManager{

	
	private IGoodsMasterBusManager goodsMasterBusManager;

	private IGoodsMasterBusManager stagingGoodsMasterBusManager;
	
	private ITrxControllerFactory trxControllerFactory;

	public IGoodsMasterBusManager getGoodsMasterBusManager() {
		return goodsMasterBusManager;
	}

	public void setGoodsMasterBusManager(IGoodsMasterBusManager goodsMasterBusManager) {
		this.goodsMasterBusManager = goodsMasterBusManager;
	}

	public IGoodsMasterBusManager getStagingGoodsMasterBusManager() {
		return stagingGoodsMasterBusManager;
	}

	public void setStagingGoodsMasterBusManager(IGoodsMasterBusManager stagingGoodsMasterBusManager) {
		this.stagingGoodsMasterBusManager = stagingGoodsMasterBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create GoodsMaster
	 */
	public IGoodsMasterTrxValue makerCreateGoodsMaster(ITrxContext anITrxContext,
			IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new GoodsMasterException("The ITrxContext is null!!!");
		}
		if (anICCGoodsMaster == null) {
			throw new GoodsMasterException("The ICCGoodsMaster to be updated is null !!!");
		}

		IGoodsMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCGoodsMaster);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_GOODS_MASTER);
		return operate(trxValue, param);
	}
	
	
	
	private IGoodsMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IGoodsMaster anIGoodsMaster) {
		IGoodsMasterTrxValue ccGoodsMasterTrxValue = null;
		if (anICMSTrxValue != null) {
			ccGoodsMasterTrxValue = new OBGoodsMasterTrxValue(anICMSTrxValue);
		} else {
			ccGoodsMasterTrxValue = new OBGoodsMasterTrxValue();
		}
		ccGoodsMasterTrxValue = formulateTrxValue(anITrxContext,
				(IGoodsMasterTrxValue) ccGoodsMasterTrxValue);
		ccGoodsMasterTrxValue.setStagingGoodsMaster(anIGoodsMaster);
		return ccGoodsMasterTrxValue;
	}

	private IGoodsMasterTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IGoodsMasterTrxValue anIGoodsMasterTrxValue) {
		anIGoodsMasterTrxValue.setTrxContext(anITrxContext);
		anIGoodsMasterTrxValue.setTransactionType(ICMSConstant.INSTANCE_GOODS_MASTER);
		return anIGoodsMasterTrxValue;
	}

	private IGoodsMasterTrxValue operate(IGoodsMasterTrxValue anIGoodsMasterTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIGoodsMasterTrxValue, anOBCMSTrxParameter);
		return (IGoodsMasterTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (GoodsMasterException e) {
			e.printStackTrace();
			throw new GoodsMasterException("ERROR--Cannot Get the GoodsMaster Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new GoodsMasterException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new GoodsMasterException("ERROR--Cannot Get the GoodsMaster Controller.");
		}
	}
	
	
	/**
	 * @return List of all GoodsMaster
	 */
	
	public SearchResult getAllActualGoodsMaster()throws GoodsMasterException,TrxParameterException,TransactionException {
		try{
			return getGoodsMasterBusManager().getAllGoodsMaster( );
		}catch (Exception e) {
			throw new GoodsMasterException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return GoodsMaster TRX value according to trxId .
	 */

	public IGoodsMasterTrxValue getGoodsMasterByTrxID(String aTrxID)
			throws GoodsMasterException, TransactionException, CommandProcessingException {
		IGoodsMasterTrxValue trxValue = new OBGoodsMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_GOODS_MASTER);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_GOODS_MASTER_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve GoodsMaster according to criteria .
	 */

	public IGoodsMasterTrxValue checkerApproveGoodsMaster(ITrxContext anITrxContext,
			IGoodsMasterTrxValue anIGoodsMasterTrxValue)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new GoodsMasterException("The ITrxContext is null!!!");
		}
		if (anIGoodsMasterTrxValue == null) {
			throw new GoodsMasterException("The IGoodsMasterTrxValue to be updated is null!!!");
		}
		anIGoodsMasterTrxValue = formulateTrxValue(anITrxContext, anIGoodsMasterTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GOODS_MASTER);
		return operate(anIGoodsMasterTrxValue, param);
	}
	
	public boolean isGoodsCodeUnique(String GoodsCode) {
		return getGoodsMasterBusManager().isGoodsCodeUnique(GoodsCode);
	}
	
	/**
	 * @return Maker Update GoodsMaster
	 */

	public IGoodsMasterTrxValue makerUpdateSaveUpdateGoodsMaster(ITrxContext anITrxContext,
			IGoodsMasterTrxValue anICCGoodsMasterTrxValue, IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new GoodsMasterException("The ITrxContext is null!!!");
		}
		if (anICCGoodsMaster == null) {
			throw new GoodsMasterException("The ICCGoodsMaster to be updated is null !!!");
		}
		IGoodsMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue,
				anICCGoodsMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_GOODS_MASTER);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save GoodsMaster
	 */

	public IGoodsMasterTrxValue makerSaveGoodsMaster(ITrxContext anITrxContext,
			IGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new GoodsMasterException("The ITrxContext is null!!!");
		}
		if (anICCGoodsMaster == null) {
			throw new GoodsMasterException("The ICCGoodsMaster to be updated is null !!!");
		}

		IGoodsMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCGoodsMaster);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_GOODS_MASTER);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  Goods Master TRX value  .
	 */
	public IGoodsMasterTrxValue getGoodsMasterTrxValue(String goodsCode) throws GoodsMasterException, TrxParameterException, TransactionException {
		if (goodsCode == com.integrosys.cms.app.common.constant.ICMSConstant.NOT_AVAILABLE_VALUE) {
           throw new FCCBranchException("Invalid goodsCode Id");
       }
		IGoodsMasterTrxValue trxValue = new OBGoodsMasterTrxValue();
       trxValue.setReferenceID(String.valueOf(goodsCode));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_GOODS_MASTER);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_GOODS_MASTER);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update GoodsMaster
	 */

	public IGoodsMasterTrxValue makerUpdateGoodsMaster(OBTrxContext anITrxContext,
			IGoodsMasterTrxValue anICCGoodsMasterTrxValue, OBGoodsMaster anICCGoodsMaster)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new GoodsMasterException("The ITrxContext is null!!!");
		}
		if (anICCGoodsMaster == null) {
			throw new GoodsMasterException("The ICCGoodsMaster to be updated is null !!!");
		}
		IGoodsMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue,anICCGoodsMaster);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_GOODS_MASTER);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IGoodsMasterTrxValue checkerRejectGoodsMaster(ITrxContext anITrxContext,
			IGoodsMasterTrxValue anICCGoodsMasterTrxValue)
			throws GoodsMasterException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new GoodsMasterException("The ITrxContext is null!!!");
	     }
	     if (anICCGoodsMasterTrxValue == null) {
	    	 throw new GoodsMasterException("The IGoodsMasterTrxValue to be updated is null!!!");
	     }
	     anICCGoodsMasterTrxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GOODS_MASTER);
	     return operate(anICCGoodsMasterTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public IGoodsMasterTrxValue makerEditRejectedGoodsMaster(
				ITrxContext anITrxContext,
				IGoodsMasterTrxValue anICCGoodsMasterTrxValue, IGoodsMaster anIGoodsMaster)
						throws GoodsMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new GoodsMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCGoodsMasterTrxValue == null) {
	            throw new GoodsMasterException("The IGoodsMasterTrxValue to be updated is null!!!");
	        }
	        if (anIGoodsMaster == null) {
	            throw new GoodsMasterException("The IGoodsMaster to be updated is null !!!");
	        }
	        anICCGoodsMasterTrxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue, anIGoodsMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GOODS_MASTER);
	        return operate(anICCGoodsMasterTrxValue, param);
		}
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IGoodsMasterTrxValue makerCloseRejectedGoodsMaster(
				ITrxContext anITrxContext,
				IGoodsMasterTrxValue anICCGoodsMasterTrxValue)
				throws GoodsMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCGoodsMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCGoodsMasterTrxValue to be updated is null!!!");
	        }
	        anICCGoodsMasterTrxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GOODS_MASTER);
	        return operate(anICCGoodsMasterTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create GoodsMaster
		 */

		public IGoodsMasterTrxValue makerUpdateSaveCreateGoodsMaster(ITrxContext anITrxContext,
				IGoodsMasterTrxValue anICCGoodsMasterTrxValue, IGoodsMaster anICCGoodsMaster)
				throws GoodsMasterException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new GoodsMasterException("The ITrxContext is null!!!");
			}
			if (anICCGoodsMaster == null) {
				throw new GoodsMasterException("The ICCGoodsMaster to be updated is null !!!");
			}
			IGoodsMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue,anICCGoodsMaster);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_GOODS_MASTER);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IGoodsMasterTrxValue makerCloseDraftGoodsMaster(
				ITrxContext anITrxContext,
				IGoodsMasterTrxValue anICCGoodsMasterTrxValue)
				throws GoodsMasterException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCGoodsMasterTrxValue == null) {
	            throw new FCCBranchException("The ICCGoodsMasterTrxValue to be updated is null!!!");
	        }
	        anICCGoodsMasterTrxValue = formulateTrxValue(anITrxContext, anICCGoodsMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOODS_MASTER);
	        return operate(anICCGoodsMasterTrxValue, param);
		}
		
		/**
		 * @return List of all GoodsMaster
		 */
		
		public SearchResult getAllFilteredActualGoodsMaster(String code,String name)throws GoodsMasterException,TrxParameterException,TransactionException {
			try{
				return getGoodsMasterBusManager().getAllFilteredGoodsMaster(code,name);
			}catch (Exception e) {
				throw new GoodsMasterException("ERROR- Cannot retrive list from database.");
			}
	    }
}
