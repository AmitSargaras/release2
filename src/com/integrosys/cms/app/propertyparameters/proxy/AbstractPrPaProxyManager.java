package com.integrosys.cms.app.propertyparameters.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.OBPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.PrPaTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:28:00 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPrPaProxyManager implements IPrPaProxyManager {

	public IPrPaTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws PropertyParametersException {
		if (aTrxID == null) {
			throw new PropertyParametersException("The TrxID is null !!!");
		}
		IPrPaTrxValue trxValue = new OBPrPaTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_AUTO_VAL_PARAM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PRPA);
		return operate(trxValue, param);
	}

	public IPrPaTrxValue getCCDocumentLocationTrxValue(long aDocumentLocationID) throws PropertyParametersException {
		if (aDocumentLocationID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new PropertyParametersException("The DocumentLocationID is invalid !!!");
		}
		IPrPaTrxValue trxValue = new OBPrPaTrxValue();
		trxValue.setReferenceID(String.valueOf(aDocumentLocationID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_AUTO_VAL_PARAM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PRPA_ID);
		return operate(trxValue, param);
	}

	public IPrPaTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			IPropertyParameters anICCDocumentLocation) throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new PropertyParametersException("The ICCDocumentLocation to be updated is null !!!");
		}

		DefaultLogger.debug(this, "4 =========== anICCDocumentLocation is null? " + (anICCDocumentLocation == null));
		IPrPaTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PRPA);
		return operate(trxValue, param);
	}

	public IPrPaTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new PropertyParametersException("The ICCDocumentLocation to be updated is null !!!");
		}
		IPrPaTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue, anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PRPA);
		return operate(trxValue, param);
	}

	public IPrPaTrxValue makerDeleteDocumentLocation(ITrxContext anITrxContext,
			IPrPaTrxValue anICCDocumentLocationTrxValue, IPropertyParameters anICCDocumentLocation)
			throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new PropertyParametersException("The ICCDocumentLocation to be updated is null !!!");
		}
		IPrPaTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue, anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_PRPA);
		return operate(trxValue, param);
	}

	/**
	 * Checker approve the document location
	 * @param anITrxContext of ITrxContext type
	 * @param IPrPaTrxValue of IPrPaTrxValue type
	 * @return IPrPaTrxValue - the generated CC document location trx value
	 * @throws PropertyParametersException on errors
	 */
	public IPrPaTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anIPrPaTrxValue == null) {
			throw new PropertyParametersException("The IPrPaTrxValue to be updated is null!!!");
		}
		anIPrPaTrxValue = formulateTrxValue(anITrxContext, anIPrPaTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PRPA);
		return operate(anIPrPaTrxValue, param);
	}

	public IPrPaTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anIPrPaTrxValue == null) {
			throw new PropertyParametersException("The IPrPaTrxValue to be updated is null!!!");
		}
		anIPrPaTrxValue = formulateTrxValue(anITrxContext, anIPrPaTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PRPA);
		return operate(anIPrPaTrxValue, param);
	}

	public IPrPaTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue,
			IPropertyParameters anIpropertyParameters) throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anIPrPaTrxValue == null) {
			throw new PropertyParametersException("The IPrPaTrxValue to be updated is null!!!");
		}
		if (anIpropertyParameters == null) {
			throw new PropertyParametersException("The IpropertyParameters to be updated is null !!!");
		}
		anIPrPaTrxValue = formulateTrxValue(anITrxContext, anIPrPaTrxValue, anIpropertyParameters);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PRPA);
		return operate(anIPrPaTrxValue, param);
	}

	public IPrPaTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue)
			throws PropertyParametersException {
		if (anITrxContext == null) {
			throw new PropertyParametersException("The ITrxContext is null!!!");
		}
		if (anIPrPaTrxValue == null) {
			throw new PropertyParametersException("The IPrPaTrxValue to be updated is null!!!");
		}
		anIPrPaTrxValue = formulateTrxValue(anITrxContext, anIPrPaTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PRPA);
		return operate(anIPrPaTrxValue, param);
	}

	private IPrPaTrxValue formulateTrxValue(ITrxContext anITrxContext, IPropertyParameters anIpropertyParameters) {
		return formulateTrxValue(anITrxContext, null, anIpropertyParameters);
	}

	private IPrPaTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IPropertyParameters anIpropertyParameters) {
		IPrPaTrxValue ccDocumentLocationTrxValue = null;
		if (anICMSTrxValue != null) {
			ccDocumentLocationTrxValue = new OBPrPaTrxValue(anICMSTrxValue);
		}
		else {
			ccDocumentLocationTrxValue = new OBPrPaTrxValue();
		}
		ccDocumentLocationTrxValue = formulateTrxValue(anITrxContext, (IPrPaTrxValue) ccDocumentLocationTrxValue);
		DefaultLogger.debug(this, "3 =========== anIpropertyParameters is null ??" + (anIpropertyParameters == null));
		ccDocumentLocationTrxValue.setStagingPrPa(anIpropertyParameters);
		return ccDocumentLocationTrxValue;
	}

	private IPrPaTrxValue formulateTrxValue(ITrxContext anITrxContext, IPrPaTrxValue anIPrPaTrxValue) {
		anIPrPaTrxValue.setTrxContext(anITrxContext);
		anIPrPaTrxValue.setTransactionType(ICMSConstant.INSTANCE_AUTO_VAL_PARAM);
		return anIPrPaTrxValue;
	}

	private IPrPaTrxValue operate(IPrPaTrxValue anIPrPaTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws PropertyParametersException {
		ICMSTrxResult result = operateForResult(anIPrPaTrxValue, anOBCMSTrxParameter);
		return (IPrPaTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws PropertyParametersException {
		try {
			ITrxController controller = (new PrPaTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new PropertyParametersException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			// rollback();
			throw new PropertyParametersException(e);
		}
		catch (Exception ex) {
			// rollback();
			throw new PropertyParametersException(ex.toString());
		}
	}

	// protected abstract void rollback();
}
