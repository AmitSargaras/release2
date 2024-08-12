package com.integrosys.cms.app.excludedfacility.proxy;

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
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacilityBusManager;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class ExcludedFacilityProxyManagerImpl implements IExcludedFacilityProxyManager {

	private IExcludedFacilityBusManager excludedFacilityBusManager;

	private IExcludedFacilityBusManager stagingExcludedFacilityBusManager;

	public IExcludedFacilityBusManager getExcludedFacilityBusManager() {
		return excludedFacilityBusManager;
	}

	public void setExcludedFacilityBusManager(IExcludedFacilityBusManager excludedFacilityBusManager) {
		this.excludedFacilityBusManager = excludedFacilityBusManager;
	}

	public IExcludedFacilityBusManager getStagingExcludedFacilityBusManager() {
		return stagingExcludedFacilityBusManager;
	}

	public void setStagingExcludedFacilityBusManager(IExcludedFacilityBusManager stagingExcludedFacilityBusManager) {
		this.stagingExcludedFacilityBusManager = stagingExcludedFacilityBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	private ITrxControllerFactory trxControllerFactory;

	/**
	 * @return Maker Create FacilityNewMaster
	 */
	public IExcludedFacilityTrxValue makerCreateExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCExcludedFacility to be updated is null !!!");
		}

		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCExcludedFacility);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}

	private IExcludedFacilityTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IExcludedFacility anIExcludedFacility) {
		IExcludedFacilityTrxValue ccExcludedFacilityTrxValue = null;
		if (anICMSTrxValue != null) {
			ccExcludedFacilityTrxValue = new OBExcludedFacilityTrxValue(anICMSTrxValue);
		} else {
			ccExcludedFacilityTrxValue = new OBExcludedFacilityTrxValue();
		}
		ccExcludedFacilityTrxValue = formulateTrxValue(anITrxContext,
				(IExcludedFacilityTrxValue) ccExcludedFacilityTrxValue);
		ccExcludedFacilityTrxValue.setStagingExcludedFacility(anIExcludedFacility);
		return ccExcludedFacilityTrxValue;
	}

	private IExcludedFacilityTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue) {
		anIExcludedFacilityTrxValue.setTrxContext(anITrxContext);
		anIExcludedFacilityTrxValue.setTransactionType(ICMSConstant.INSTANCE_EXCLUDED_FACILITY);
		return anIExcludedFacilityTrxValue;
	}

	private IExcludedFacilityTrxValue operate(IExcludedFacilityTrxValue anIExcludedFacilityTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIExcludedFacilityTrxValue, anOBCMSTrxParameter);
		return (IExcludedFacilityTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (ExcludedFacilityException ex) {
			throw new ExcludedFacilityException("ERROR--Cannot Get the Excluded Facility Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new ExcludedFacilityException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new ExcludedFacilityException("ERROR--Cannot Get the Excluded Facility Controller.");
		}
	}

	/**
	 * @return ExcludedFacility TRX value according to trxId .
	 */

	public IExcludedFacilityTrxValue getExcludedFacilityByTrxID(String aTrxID)
			throws ExcludedFacilityException, TransactionException, CommandProcessingException {
		IExcludedFacilityTrxValue trxValue = new OBExcludedFacilityTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_EXCLUDED_FACILITY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_EXCLUDED_FACILITY_ID);
		return operate(trxValue, param);
	}

	/**
	 * @return Checker Approve ExcludedFacility according to criteria .
	 */

	public IExcludedFacilityTrxValue checkerApproveExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anIExcludedFacilityTrxValue == null) {
			throw new ExcludedFacilityException("The IExcludedFacilityTrxValue to be updated is null!!!");
		}
		anIExcludedFacilityTrxValue = formulateTrxValue(anITrxContext, anIExcludedFacilityTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_EXCLUDED_FACILITY);
		return operate(anIExcludedFacilityTrxValue, param);
	}

	/**
	 * @return Checker Reject FacilityNewMaster according to criteria .
	 */

	public IExcludedFacilityTrxValue checkerRejectExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anIExcludedFacilityTrxValue == null) {
			throw new ExcludedFacilityException("The IExcludedFacilityTrxValue to be updated is null!!!");
		}
		anIExcludedFacilityTrxValue = formulateTrxValue(anITrxContext, anIExcludedFacilityTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_EXCLUDED_FACILITY);
		return operate(anIExcludedFacilityTrxValue, param);
	}

	/**
	 * @return Maker Save ExcludedFacility
	 */

	public IExcludedFacilityTrxValue makerSaveExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCExcludedFacility to be updated is null !!!");
		}

		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCExcludedFacility);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update ExcludedFacility
	 */

	public IExcludedFacilityTrxValue makerUpdateSaveUpdateExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue, IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCExcludedFacility to be updated is null !!!");
		}
		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, anICCExcludedFacilityTrxValue,
				anICCExcludedFacility);
		// trxValue.setFromState("DRAFT");
		// trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Edit ExcludedFacility
	 */
	public IExcludedFacilityTrxValue makerEditRejectedExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue, IExcludedFacility anExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anIExcludedFacilityTrxValue == null) {
			throw new ExcludedFacilityException("The IExcludedFacilityTrxValue to be updated is null!!!");
		}
		if (anExcludedFacility == null) {
			throw new ExcludedFacilityException("The IExcludedFacility to be updated is null !!!");
		}
		anIExcludedFacilityTrxValue = formulateTrxValue(anITrxContext, anIExcludedFacilityTrxValue, anExcludedFacility);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_EXCLUDED_FACILITY);
		return operate(anIExcludedFacilityTrxValue, param);
	}

	/**
	 * @return Maker Delete ExcludedFacility
	 */

	public IExcludedFacilityTrxValue makerDeleteExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue, IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCPropertyIdx to be updated is null !!!");
		}
		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, anICCExcludedFacilityTrxValue,
				anICCExcludedFacility);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update Draft for create ExcludedFacility
	 */

	public IExcludedFacilityTrxValue makerUpdateSaveCreateExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue, IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCFacilityNewMaster to be updated is null !!!");
		}
		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, anICCExcludedFacilityTrxValue,
				anICCExcludedFacility);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update ExcludedFacility
	 */

	public IExcludedFacilityTrxValue makerUpdateExcludedFacility(ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue, IExcludedFacility anICCExcludedFacility)
			throws ExcludedFacilityException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new ExcludedFacilityException("The ITrxContext is null!!!");
		}
		if (anICCExcludedFacility == null) {
			throw new ExcludedFacilityException("The ICCExcludedFacility to be updated is null !!!");
		}
		IExcludedFacilityTrxValue trxValue = formulateTrxValue(anITrxContext, anICCExcludedFacilityTrxValue,
				anICCExcludedFacility);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_EXCLUDED_FACILITY);
		return operate(trxValue, param);
	}
	
	/**
	 * @return List of all ExcludedFacility
	 */
	
	public SearchResult getAllActualExcludedFacility()throws ExcludedFacilityException,TrxParameterException,TransactionException {
		try{
			return getExcludedFacilityBusManager().getAllExcludedFacility( );
		}catch (Exception e) {
			throw new ExcludedFacilityException("ERROR- Cannot retrive list from database.");
			
		}
    }
	/**
	 * @return  Excluded Facility TRX value  .
	 */
	

	public IExcludedFacilityTrxValue getExcludedFacilityTrxValue(
			Long aExcludedFacilityId) throws ExcludedFacilityException,
			TrxParameterException, TransactionException {
		if (aExcludedFacilityId ==  com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new ExcludedFacilityException("Invalid ExcludedFacilityId");
        }
        IExcludedFacilityTrxValue trxValue = new OBExcludedFacilityTrxValue();
        trxValue.setReferenceID(String.valueOf(aExcludedFacilityId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_EXCLUDED_FACILITY);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_EXCLUDED_FACILITY);
        return operate(trxValue, param);
	}
	/**
	 * @return Maker Close draft ExcludedFacility
	 */
	
	public IExcludedFacilityTrxValue makerCloseDraftExcludedFacility(
			ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue)
			throws ExcludedFacilityException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ExcludedFacilityException("The ITrxContext is null!!!");
        }
        if (anIExcludedFacilityTrxValue == null) {
            throw new ExcludedFacilityException("The IExcludedFacilityTrxValue to be updated is null!!!");
        }
        anIExcludedFacilityTrxValue = formulateTrxValue(anITrxContext, anIExcludedFacilityTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_EXCLUDED_FACILITY);
        return operate(anIExcludedFacilityTrxValue, param);
	}
	/**
	 * @return Maker Close ExcludedFacility.
	 */
	
	public IExcludedFacilityTrxValue makerCloseRejectedExcludedFacility(
			ITrxContext anITrxContext,
			IExcludedFacilityTrxValue anIExcludedFacilityTrxValue)
			throws ExcludedFacilityException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ExcludedFacilityException("The ITrxContext is null!!!");
        }
        if (anIExcludedFacilityTrxValue == null) {
            throw new ExcludedFacilityException("The IExcludedFacilityTrxValue to be updated is null!!!");
        }
        anIExcludedFacilityTrxValue = formulateTrxValue(anITrxContext, anIExcludedFacilityTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_EXCLUDED_FACILITY);
        return operate(anIExcludedFacilityTrxValue, param);
	}
	
	public boolean isExcludedFacilityDescriptionUnique(String excludedFacilityDescription) {
		return getExcludedFacilityBusManager().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);
	}
	
	public SearchResult getSearchedExcludedFacility(String type,String text)throws ExcludedFacilityException,TrxParameterException,TransactionException {
		try{
			return getExcludedFacilityBusManager().getSearchedExcludedFacility(type,text);
		}catch (Exception e) {
			throw new ExcludedFacilityException("ERROR- Cannot retrive list from database.");
		}
    }
    

}
