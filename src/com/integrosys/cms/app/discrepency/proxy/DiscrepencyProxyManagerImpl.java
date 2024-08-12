package com.integrosys.cms.app.discrepency.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/06/2011 06:40:00 $ Tag: $Name: $
 */

public class DiscrepencyProxyManagerImpl implements IDiscrepencyProxyManager {

	private IDiscrepencyBusManager discrepencyBusManager;

	private IDiscrepencyBusManager stagingDiscrepencyBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public IDiscrepencyBusManager getDiscrepencyBusManager() {
		return discrepencyBusManager;
	}

	public void setDiscrepencyBusManager(
			IDiscrepencyBusManager discrepencyBusManager) {
		this.discrepencyBusManager = discrepencyBusManager;
	}

	public IDiscrepencyBusManager getStagingDiscrepencyBusManager() {
		return stagingDiscrepencyBusManager;
	}

	public void setStagingDiscrepencyBusManager(
			IDiscrepencyBusManager stagingDiscrepencyBusManager) {
		this.stagingDiscrepencyBusManager = stagingDiscrepencyBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IDiscrepencyTrxValue checkerApproveDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepencyTrxValue == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be updated is null!!!");
		}
		anIDiscrepencyTrxValue = formulateTrxValue(anITrxContext, anIDiscrepencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_DISCREPENCY);
		return operate(anIDiscrepencyTrxValue, param);
	}

	public IDiscrepencyTrxValue checkerRejectDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepencyTrxValue == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be updated is null!!!");
		}
		anIDiscrepencyTrxValue = formulateTrxValue(anITrxContext, anIDiscrepencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_DISCREPENCY);
		return operate(anIDiscrepencyTrxValue, param);
	}

	public IDiscrepency createDiscrepency(IDiscrepency Discrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		return getDiscrepencyBusManager().createDiscrepency(Discrepency);
	}

	public IDiscrepencyTrxValue getDiscrepencyById(long id) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchDiscrepencyException("Invalid DiscrepencyId");
		}
		IDiscrepencyTrxValue trxValue = new OBDiscrepencyTrxValue();
		trxValue.setReferenceID(String.valueOf(id));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DISCREPENCY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue getDiscrepencyByTrxID(String aTrxID)
			throws NoSuchDiscrepencyException, TransactionException,
			CommandProcessingException {
		IDiscrepencyTrxValue trxValue = new OBDiscrepencyTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DISCREPENCY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DISCREPENCY_ID);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue getDiscrepencyTrxValue(long aDiscrepencyId)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (aDiscrepencyId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchDiscrepencyException("Invalid DiscrepencyId");
		}
		IDiscrepencyTrxValue trxValue = new OBDiscrepencyTrxValue();
		trxValue.setReferenceID(String.valueOf(aDiscrepencyId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DISCREPENCY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue makerCloseRejectedDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepencyTrxValue == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be updated is null!!!");
		}
		anIDiscrepencyTrxValue = formulateTrxValue(anITrxContext, anIDiscrepencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DISCREPENCY);
		return operate(anIDiscrepencyTrxValue, param);
	}

	public IDiscrepencyTrxValue makerDeleteDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue, IDiscrepency anIDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepency to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anIDiscrepencyTrxValue, anIDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue makerActivateDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue, IDiscrepency anIDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepency to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anIDiscrepencyTrxValue, anIDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue makerEditRejectedDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue, IDiscrepency anDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepencyTrxValue == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be updated is null!!!");
		}
		if (anDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepency to be updated is null !!!");
		}
		anIDiscrepencyTrxValue = formulateTrxValue(anITrxContext, anIDiscrepencyTrxValue,
				anDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DISCREPENCY);
		return operate(anIDiscrepencyTrxValue, param);
	}

	public IDiscrepencyTrxValue makerUpdateDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anICCDiscrepencyTrxValue, IDiscrepency anICCDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anICCDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCDiscrepencyTrxValue, anICCDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepency updateDiscrepency(IDiscrepency discrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		if (discrepency != null) {
			return (IDiscrepency) getDiscrepencyBusManager().updateDiscrepency(discrepency);
		} else {
			throw new NoSuchDiscrepencyException("Other Bank Object is null.");
		}
	}

	public SearchResult listDiscrepency(long customerId)
			throws NoSuchDiscrepencyException {
		try {
			return getDiscrepencyBusManager().listDiscrepency(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Error While Listing Discrepency");
		}
	}

	public IDiscrepencyTrxValue makerCreateDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue, IDiscrepency anDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anIDiscrepencyTrxValue == null) {
			throw new NoSuchDiscrepencyException(
					"The IDiscrepencyTrxValue to be created is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anIDiscrepencyTrxValue, anDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue makerUpdateSaveUpdateDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anICCDiscrepencyTrxValue, IDiscrepency anICCDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anICCDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The ICCDiscrepency to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCDiscrepencyTrxValue, anICCDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_DISCREPENCY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update Draft for create Discrepency .
	 */

	public IDiscrepencyTrxValue makerUpdateSaveCreateDiscrepency(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anICCDiscrepencyTrxValue, IDiscrepency anICCDiscrepency)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anICCDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The ICCDiscrepency to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCDiscrepencyTrxValue, anICCDiscrepency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_DISCREPENCY);
		return operate(trxValue, param);
	}

	public IDiscrepencyTrxValue makerSaveDiscrepency(ITrxContext anITrxContext,
			IDiscrepency anICCDiscrepency) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchDiscrepencyException("The ITrxContext is null!!!");
		}
		if (anICCDiscrepency == null) {
			throw new NoSuchDiscrepencyException(
					"The ICCDiscrepency to be updated is null !!!");
		}
		IDiscrepencyTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCDiscrepency);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_DISCREPENCY);
		return operate(trxValue, param);
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anIDiscrepency
	 * @return
	 */

	private IDiscrepencyTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, IDiscrepency anIDiscrepency) {
		IDiscrepencyTrxValue discrepencyTrxValue = null;
		if (anICMSTrxValue != null) {
			discrepencyTrxValue = new OBDiscrepencyTrxValue(anICMSTrxValue);
		} else {
			discrepencyTrxValue = new OBDiscrepencyTrxValue();
		}
		discrepencyTrxValue = formulateTrxValue(anITrxContext,
				(IDiscrepencyTrxValue) discrepencyTrxValue);
		discrepencyTrxValue.setStagingDiscrepency(anIDiscrepency);
		return discrepencyTrxValue;
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anIDiscrepencyTrxValue
	 * @return IDiscrepencyTrxValue
	 */

	private IDiscrepencyTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IDiscrepencyTrxValue anIDiscrepencyTrxValue) {
		anIDiscrepencyTrxValue.setTrxContext(anITrxContext);
		anIDiscrepencyTrxValue.setTransactionType(ICMSConstant.INSTANCE_DISCREPENCY);
		return anIDiscrepencyTrxValue;
	}

	private IDiscrepencyTrxValue operate(IDiscrepencyTrxValue anIDiscrepencyTrxValueTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		ICMSTrxResult result = operateForResult(anIDiscrepencyTrxValueTrxValue,
				anOBCMSTrxParameter);
		return (IDiscrepencyTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchDiscrepencyException, TrxParameterException,
			TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory()
					.getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate
					.notNull(controller,
							"'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue,
					anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}

		catch (NoSuchDiscrepencyException ex) {
			throw new NoSuchDiscrepencyException(ex.toString());
		}
	}
}
