package com.integrosys.cms.app.collateralrocandinsurance.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRocBusManager;
import com.integrosys.cms.app.collateralrocandinsurance.trx.ICollateralRocTrxValue;
import com.integrosys.cms.app.collateralrocandinsurance.trx.OBCollateralRocTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class CollateralRocProxyManagerImpl implements ICollateralRocProxyManager {

	private ICollateralRocBusManager collateralRocBusManager;

	private ICollateralRocBusManager stagingCollateralRocBusManager;
	
	private ITrxControllerFactory trxControllerFactory;
	
	public ICollateralRocBusManager getCollateralRocBusManager() {
		return collateralRocBusManager;
	}

	public void setCollateralRocBusManager(ICollateralRocBusManager collateralRocBusManager) {
		this.collateralRocBusManager = collateralRocBusManager;
	}

	public ICollateralRocBusManager getStagingCollateralRocBusManager() {
		return stagingCollateralRocBusManager;
	}

	public void setStagingCollateralRocBusManager(ICollateralRocBusManager stagingCollateralRocBusManager) {
		this.stagingCollateralRocBusManager = stagingCollateralRocBusManager;
	}
	
	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create CollateralRoc
	 */
	public ICollateralRocTrxValue makerCreateCollateralRoc(ITrxContext anITrxContext,
			ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}

		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCollateralRoc);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	private ICollateralRocTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICollateralRoc anICollateralRoc) {
		ICollateralRocTrxValue ccCollateralRocTrxValue = null;
		if (anICMSTrxValue != null) {
			ccCollateralRocTrxValue = new OBCollateralRocTrxValue(anICMSTrxValue);
		} else {
			ccCollateralRocTrxValue = new OBCollateralRocTrxValue();
		}
		ccCollateralRocTrxValue = formulateTrxValue(anITrxContext,
				(ICollateralRocTrxValue) ccCollateralRocTrxValue);
		ccCollateralRocTrxValue.setStagingCollateralRoc(anICollateralRoc);
		return ccCollateralRocTrxValue;
	}
	
	private ICollateralRocTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue) {
		anICollateralRocTrxValue.setTrxContext(anITrxContext);
		anICollateralRocTrxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_ROC);
		return anICollateralRocTrxValue;
	}
	
	private ICollateralRocTrxValue operate(ICollateralRocTrxValue anICollateralRocTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws CollateralRocException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anICollateralRocTrxValue, anOBCMSTrxParameter);
		return (ICollateralRocTrxValue) result.getTrxValue();
	}
	
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CollateralRocException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (CollateralRocException ex) {
			throw new CollateralRocException("ERROR--Cannot Get the CollateralRoc Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new CollateralRocException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new CollateralRocException("ERROR--Cannot Get the CollateralRoc Controller.");
		}
	}
	
	/**
	 * @return CollateralRoc TRX value according to trxId .
	 */

	public ICollateralRocTrxValue getCollateralRocByTrxID(String aTrxID)
			throws CollateralRocException, TransactionException, CommandProcessingException {
		ICollateralRocTrxValue trxValue = new OBCollateralRocTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_ROC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COLLATERAL_ROC_ID);
		return operate(trxValue, param);
	}
	
	
	/**
	 * @return Checker Approve CollateralRoc according to criteria .
	 */

	public ICollateralRocTrxValue checkerApproveCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICollateralRocTrxValue == null) {
			throw new CollateralRocException("The ICollateralRocTrxValue to be updated is null!!!");
		}
		anICollateralRocTrxValue = formulateTrxValue(anITrxContext, anICollateralRocTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_ROC);
		return operate(anICollateralRocTrxValue, param);
	}
	
	/**
	 * @return Checker Reject CollateralRoc according to criteria .
	 */

	public ICollateralRocTrxValue checkerRejectCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICollateralRocTrxValue == null) {
			throw new CollateralRocException("The ICollateralRocTrxValue to be updated is null!!!");
		}
		anICollateralRocTrxValue = formulateTrxValue(anITrxContext, anICollateralRocTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_ROC);
		return operate(anICollateralRocTrxValue, param);
	}
	
	/**
	 * @return List of all CollateralRoc
	 */
	
	public SearchResult getAllActualCollateralRoc()throws CollateralRocException,TrxParameterException,TransactionException {
		try{
			return getCollateralRocBusManager().getAllCollateralRoc();
		}catch (Exception e) {
			throw new CollateralRocException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return CollateralRoc TRX value  .
	 */
	

	public ICollateralRocTrxValue getCollateralRocTrxValue(
			Long aCollateralRocId) throws CollateralRocException,
			TrxParameterException, TransactionException {
		if (aCollateralRocId ==  com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new CollateralRocException("Invalid CollateralRocId");
        }
        ICollateralRocTrxValue trxValue = new OBCollateralRocTrxValue();
        trxValue.setReferenceID(String.valueOf(aCollateralRocId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_ROC);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_COLLATERAL_ROC);
        return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save CollateralRoc
	 */

	public ICollateralRocTrxValue makerSaveCollateralRoc(ITrxContext anITrxContext,
			ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}

		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCollateralRoc);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update CollateralRoc
	 */

	public ICollateralRocTrxValue makerUpdateSaveUpdateCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICCCollateralRocTrxValue, ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}
		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralRocTrxValue,
				anICCCollateralRoc);
		// trxValue.setFromState("DRAFT");
		// trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update Draft for create CollateralRoc
	 */

	public ICollateralRocTrxValue makerUpdateSaveCreateCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICCCollateralRocTrxValue, ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}
		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralRocTrxValue,
				anICCCollateralRoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update CollateralRoc
	 */

	public ICollateralRocTrxValue makerUpdateCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICCCollateralRocTrxValue, ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}
		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralRocTrxValue,
				anICCCollateralRoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Edit CollateralRoc
	 */
	public ICollateralRocTrxValue makerEditRejectedCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue, ICollateralRoc anCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICollateralRocTrxValue == null) {
			throw new CollateralRocException("The ICollateralRocTrxValue to be updated is null!!!");
		}
		if (anCollateralRoc == null) {
			throw new CollateralRocException("The ICollateralRoc to be updated is null !!!");
		}
		anICollateralRocTrxValue = formulateTrxValue(anITrxContext, anICollateralRocTrxValue, anCollateralRoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_ROC);
		return operate(anICollateralRocTrxValue, param);
	}
	
	/**
	 * @return Maker Delete CollateralRoc
	 */

	public ICollateralRocTrxValue makerDeleteCollateralRoc(ITrxContext anITrxContext,
			ICollateralRocTrxValue anICCCollateralRocTrxValue, ICollateralRoc anICCCollateralRoc)
			throws CollateralRocException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CollateralRocException("The ITrxContext is null!!!");
		}
		if (anICCCollateralRoc == null) {
			throw new CollateralRocException("The ICCCollateralRoc to be updated is null !!!");
		}
		ICollateralRocTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralRocTrxValue,
				anICCCollateralRoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COLLATERAL_ROC);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Close draft CollateralRoc
	 */
	
	public ICollateralRocTrxValue makerCloseDraftCollateralRoc(
			ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue)
			throws CollateralRocException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralRocException("The ITrxContext is null!!!");
        }
        if (anICollateralRocTrxValue == null) {
            throw new CollateralRocException("The ICollateralRocTrxValue to be updated is null!!!");
        }
        anICollateralRocTrxValue = formulateTrxValue(anITrxContext, anICollateralRocTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_ROC);
        return operate(anICollateralRocTrxValue, param);
	}
	
	/**
	 * @return Maker Close CollateralRoc.
	 */
	
	public ICollateralRocTrxValue makerCloseRejectedCollateralRoc(
			ITrxContext anITrxContext,
			ICollateralRocTrxValue anICollateralRocTrxValue)
			throws CollateralRocException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralRocException("The ITrxContext is null!!!");
        }
        if (anICollateralRocTrxValue == null) {
            throw new CollateralRocException("The ICollateralRocTrxValue to be updated is null!!!");
        }
        anICollateralRocTrxValue = formulateTrxValue(anITrxContext, anICollateralRocTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_ROC);
        return operate(anICollateralRocTrxValue, param);
	}
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode) {
		return getCollateralRocBusManager().isCollateralRocCategoryUnique(collateralCategory,collateralRocCode);
	}
	public SearchResult getSearchedCollateralRoc(String type,String text)throws ExcludedFacilityException,TrxParameterException,TransactionException {
		try{
			return getCollateralRocBusManager().getSearchedCollateralRoc(type,text);
		}catch (Exception e) {
			throw new CollateralRocException("ERROR- Cannot retrive list from database.");
		}
    }

}
