package com.integrosys.cms.app.recurrent.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.bus.RecurrentSearchResult;
import com.integrosys.cms.app.recurrent.bus.SBRecurrentBusManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the checklist modules
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.105 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class RecurrentProxyManagerImpl implements IRecurrentProxyManager {

	private static final Logger logger = LoggerFactory.getLogger(RecurrentProxyManagerImpl.class);

	private SBRecurrentBusManager recurrentBusManager;

	private SBRecurrentBusManager stagingRecurrentBusManager;

	private ITrxController recurrentTrxController;

	private ITrxController recurrentReadController;
	
	private ITrxController annexureTrxController;
	
	private ITrxController annexureReadController;
	

	/**
	 * @return the annexureReadController
	 */
	public ITrxController getAnnexureReadController() {
		return annexureReadController;
	}

	/**
	 * @param annexureReadController the annexureReadController to set
	 */
	public void setAnnexureReadController(ITrxController annexureReadController) {
		this.annexureReadController = annexureReadController;
	}

	/**
	 * @return the annexureTrxController
	 */
	public ITrxController getAnnexureTrxController() {
		return annexureTrxController;
	}

	/**
	 * @param annexureTrxController the annexureTrxController to set
	 */
	public void setAnnexureTrxController(ITrxController annexureTrxController) {
		this.annexureTrxController = annexureTrxController;
	}

	/**
	 * @return the recurrentBusManager
	 */
	public SBRecurrentBusManager getRecurrentBusManager() {
		return recurrentBusManager;
	}

	/**
	 * @return the stagingRecurrentBusManager
	 */
	public SBRecurrentBusManager getStagingRecurrentBusManager() {
		return stagingRecurrentBusManager;
	}

	/**
	 * @return the recurrentTrxController
	 */
	public ITrxController getRecurrentTrxController() {
		return recurrentTrxController;
	}

	/**
	 * @return the recurrentReadController
	 */
	public ITrxController getRecurrentReadController() {
		return recurrentReadController;
	}

	/**
	 * @param recurrentBusManager the recurrentBusManager to set
	 */
	public void setRecurrentBusManager(SBRecurrentBusManager recurrentBusManager) {
		this.recurrentBusManager = recurrentBusManager;
	}

	/**
	 * @param stagingRecurrentBusManager the stagingRecurrentBusManager to set
	 */
	public void setStagingRecurrentBusManager(SBRecurrentBusManager stagingRecurrentBusManager) {
		this.stagingRecurrentBusManager = stagingRecurrentBusManager;
	}

	/**
	 * @param recurrentTrxController the recurrentTrxController to set
	 */
	public void setRecurrentTrxController(ITrxController recurrentTrxController) {
		this.recurrentTrxController = recurrentTrxController;
	}

	/**
	 * @param recurrentReadController the recurrentReadController to set
	 */
	public void setRecurrentReadController(ITrxController recurrentReadController) {
		this.recurrentReadController = recurrentReadController;
	}

	/**
	 * Get the recurrent checklist based on limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckListTrxValue - the recurrent checklist trx of the
	 *         limit profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListTrx(long aLimitProfileID, long aSubProfileID)
			throws RecurrentException {

		String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
				ICMSConstant.STATE_DRAFT, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_ACTIVE };

		RecurrentSearchResult[] resultList = getRecurrentByLimitProfileIdAndSubProfileIdAndStatusList(aLimitProfileID,
				aSubProfileID, statusList);

		if ((resultList == null) || (resultList.length == 0)) {
			return null;
		}

		return getRecurrentCheckListByTrxID(resultList[0].getTrxID());
	}
	
	/**
	 * Get the recurrent checklist based on limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckListTrxValue - the recurrent checklist trx of the
	 *         limit profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListTrxByAnnexureId(long aLimitProfileID, long aSubProfileID, String annexureId)
			throws RecurrentException {

		String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
				ICMSConstant.STATE_DRAFT, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_ACTIVE };

		RecurrentSearchResult[] resultList = getRecurrentByLimitProfileIdAndSubProfileIdAndStatusListAndAnnexureId(aLimitProfileID,
				aSubProfileID, statusList,annexureId);

		if ((resultList == null) || (resultList.length == 0)) {
			return null;
		}

		return getRecurrentCheckListByTrxID(resultList[0].getTrxID());
	}
	
	/**
	 * Get the recurrent checklist trx by checklist ID
	 * @param aTrxID of long type
	 * @return IRecurrentCheckListTrxValue - the checklist trx value
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListByTrxID(String aTrxID) throws RecurrentException {
		IRecurrentCheckListTrxValue trxValue = new OBRecurrentCheckListTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_RECURRENT_CHECKLIST);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentReadController(), param, trxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * To return false if there is any pending trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return boolean - true if there already exist and false otherwise
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public boolean allowRecurrentCheckListTrx(long aLimitProfileID, long aSubProfileID) throws RecurrentException {
		String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT };

		RecurrentSearchResult[] resultList = getRecurrentByLimitProfileIdAndSubProfileIdAndStatusList(aLimitProfileID,
				aSubProfileID, statusList);

		if ((resultList == null) || (resultList.length == 0)) {
			return true;
		}

		return false;
	}

	public IRecurrentCheckListTrxValue systemCreateCheckList(IRecurrentCheckListTrxValue recTrxVal)
			throws RecurrentException {
		Validate.notNull(recTrxVal, "'Trx Value' must not be null");

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_RECURRENT_CHECKLIST);
		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, RecurrentTransactionHelper
				.formulateTrxValue(recTrxVal.getTrxContext(), recTrxVal, ICMSConstant.CHECKLIST_MAINTAIN));
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker creation of a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCreateCheckList(ITrxContext anITrxContext, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		if (!allowRecurrentCheckListTrxCreation(anICheckList.getLimitProfileID(), anICheckList.getSubProfileID())) {
			throw new RecurrentException("workflow concurrent update detected.", new ConcurrentUpdateException(
					"transaction has been created for limit profile id [" + anICheckList.getLimitProfileID()
							+ "] sub profile id [" + anICheckList.getSubProfileID() + "]"));
		}

		IRecurrentCheckListTrxValue trxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext,
				anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, trxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker creation of a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		if (!allowRecurrentCheckListTrxCreation(anICheckList.getLimitProfileID(), anICheckList.getSubProfileID())) {
			throw new RecurrentException("workflow concurrent update detected.", new ConcurrentUpdateException(
					"transaction has been created for limit profile id [" + anICheckList.getLimitProfileID()
							+ "] sub profile id [" + anICheckList.getSubProfileID() + "]"));
		}

		IRecurrentCheckListTrxValue trxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext,
				anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, trxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Copy Recurrent Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anIRecurrentCheckList of ICheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue copyCheckList(ITrxContext anITrxContext,
			IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anIRecurrentCheckList, "'anIRecurrentCheckList' must not be null");

		IRecurrentCheckListTrxValue trxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext,
				anIRecurrentCheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_COPY_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, trxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Checker approves a recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		anICheckListTrxValue = (IRecurrentCheckListTrxValue) result.getTrxValue();

		createDefaultDueDateEntries(anICheckListTrxValue.getCheckList());

		return anICheckListTrxValue;
	}

	/**
	 * Checker rejects a recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker closes a recurrent checklist trx that has been rejected by the
	 * checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker edits a rejected recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedCheckListTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker updates a recurrent checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @param anICheckList - IRecurrentCheckList
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker save a recurrent checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @param anICheckList - IRecurrentCheckList
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker updates a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");
		// By abhijit
		anICheckListTrxValue.setStagingCheckList(anICheckList);
		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}
	
	/**
	 * Maker updates a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateAnnexureReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");
		// By abhijit
		anICheckListTrxValue.setStagingCheckList(anICheckList);
		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_ANNEXURE);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getAnnexureTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker save a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Checker approves a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}
	
	
	/**
	 * Checker approves a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckListAnnexure(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_ANNEXURE);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_ANNEXURE_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getAnnexureTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Checker rejects a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}
	
	
	/**
	 * Checker rejects a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectAnnexureCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_ANNEXURE);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_ANNEXURE_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getAnnexureTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker closes a recurrent checklist receipt trx that has been rejected by
	 * the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCloseCheckListReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * Maker edits a rejected recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedCheckListReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST_RECEIPT);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}
	
	
	/**
	 * Maker edits a rejected recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedAnnexureReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		Validate.notNull(anITrxContext, "'anITrxContext' must not be null.");
		Validate.notNull(anICheckListTrxValue, "'anICheckListTrxValue' must not be null");
		Validate.notNull(anICheckList, "'anICheckList' must not be null");

		anICheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext, anICheckListTrxValue,
				anICheckList, ICMSConstant.CHECKLIST_ANNEXURE);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ANNEXURE_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getAnnexureTrxController(), param, anICheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * To close the recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anIRecurrentCheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the recurrent checklist trx value
	 *         that is being closed
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anIRecurrentCheckListTrxValue) throws RecurrentException {
		Validate.notNull(anIRecurrentCheckListTrxValue, "'anICheckListTrxValue' must not be null");

		anIRecurrentCheckListTrxValue = RecurrentTransactionHelper.formulateTrxValue(anITrxContext,
				anIRecurrentCheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_RECURRENT_CHECKLIST);

		ICMSTrxResult result = doInTrxController(getRecurrentTrxController(), param, anIRecurrentCheckListTrxValue);
		return (IRecurrentCheckListTrxValue) result.getTrxValue();
	}

	/**
	 * System close recurrent checklist that is under a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public void systemCloseRecurrentCheckList(long aLimitProfileID, long aCustomerID) throws RecurrentException {
		IRecurrentCheckListTrxValue trxValue = getRecurrentCheckListTrx(aLimitProfileID, aCustomerID);

		if (trxValue != null) {
			systemCloseCheckList(null, trxValue);
		}
	}

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(final long anItemReference) throws RecurrentException {

		IRecurrentCheckListItem[] recurrentItem = (IRecurrentCheckListItem[]) executeRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				return (IRecurrentCheckListItem[]) busManager.getRecurrentItemHistory(anItemReference);
			}

		});

		return recurrentItem;
	}

	public void createDueDateEntries(final IRecurrentCheckListItem anItem) throws RecurrentException {
		final List createList = new ArrayList();
		if ((anItem.getRecurrentCheckListSubItemList() == null)
				|| (anItem.getRecurrentCheckListSubItemList().length == 0)) {

			RecurrentItemHelper.createDefaultSet(createList, anItem);
		}
		else {
			RecurrentItemHelper.createNonDefaultSet(createList, anItem);
		}

		if (createList.size() > 0) {
			doCreateRecurrentItemDefaultDueDateEntries(anItem, createList);
		}
	}

	public void createDueDateEntries(final IConvenant anItem) throws RecurrentException {
		final List createList = new ArrayList();
		if ((anItem.getConvenantSubItemList() == null) || (anItem.getConvenantSubItemList().length == 0)) {
			RecurrentItemHelper.createDefaultSet(createList, anItem);
		}
		else {
			RecurrentItemHelper.createNonDefaultSet(createList, anItem);
		}

		if (createList.size() > 0) {
			doCreateCovenantItemDefaultDueDateEntries(anItem, createList);
		}
	}

	public Date recomputeDueDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException {
		return RecurrentItemHelper.recomputeDueDate(aDate, aFreq, aFreqUnit);
	}

	public Date recomputeDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException {
		return RecurrentItemHelper.recomputeDate(aDate, aFreq, aFreqUnit);
	}

	public IRecurrentCheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext,
			IRecurrentCheckList anICheckList) throws RecurrentException {
		IRecurrentCheckListTrxValue trxValue = makerCreateCheckList(anITrxContext, anICheckList);
		return checkerApproveCheckList(anITrxContext, trxValue);
	}

	public IRecurrentCheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException {
		anICheckListTrxValue = makerUpdateCheckList(anITrxContext, anICheckListTrxValue, anICheckList);
		return checkerApproveCheckList(anITrxContext, anICheckListTrxValue);
	}
	//making public to use for dp
	public void createDefaultDueDateEntries(IRecurrentCheckList anICheckList) throws RecurrentException {
		IRecurrentCheckListItem[] itemList = anICheckList.getCheckListItemList();
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				createDefaultDueDateEntries(itemList[ii]);
			}
		}

		IConvenant[] convenantList = anICheckList.getConvenantList();
		if (convenantList != null) {
			for (int i = 0; i < convenantList.length; i++) {
				createDefaultDueDateEntries(convenantList[i]);
			}
		}
	}

	protected void createDefaultDueDateEntries(final IRecurrentCheckListItem anItem) throws RecurrentException {
		final List createList = new ArrayList();
		if ((anItem.getRecurrentCheckListSubItemList() == null)
				|| (anItem.getRecurrentCheckListSubItemList().length == 0)) {
			RecurrentItemHelper.createDefaultSet(createList, anItem);
		}

		if (createList.size() > 0) {
			doCreateRecurrentItemDefaultDueDateEntries(anItem, createList);
		}
	}

	protected void createDefaultDueDateEntries(final IConvenant anItem) throws RecurrentException {
		final List createList = new ArrayList();
		if ((anItem.getConvenantSubItemList() == null) || (anItem.getConvenantSubItemList().length == 0)) {
			DefaultLogger.debug(this, "IN Create Default!!!");
			RecurrentItemHelper.createDefaultSet(createList, anItem);
		}

		if (createList.size() > 0) {
			doCreateCovenantItemDefaultDueDateEntries(anItem, createList);
		}
	}

	protected void doCreateRecurrentItemDefaultDueDateEntries(final IRecurrentCheckListItem anItem,
			final List subItemList) throws RecurrentException {
		final List createdSubItemList = (List) executeStagingRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				IRecurrentCheckListItem item = busManager.getRecurrentCheckListItemByRef(anItem.getCheckListItemRef());
				return busManager.createRecurrentCheckListSubItem(item.getCheckListItemID(), subItemList);

			}
		});

		// use the list of created staging sub Items to create
		// the actual sub items so that the sub item ref in
		// actual is maintained

		executeRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				return busManager.createRecurrentCheckListSubItem(anItem.getCheckListItemID(), createdSubItemList);
			}
		});
	}

	protected void doCreateCovenantItemDefaultDueDateEntries(final IConvenant anItem, final List subItemList)
			throws RecurrentException {
		final List createdSubItemList = (List) executeStagingRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				IConvenant item = busManager.getConvenantByRef(anItem.getConvenantRef());
				return busManager.createConvenantSubItem(item.getConvenantID(), subItemList);
			}

		});

		// use the list of created staging sub Items to create
		// the actual sub items so that the sub item ref in
		// actual is maintained

		executeRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				return busManager.createConvenantSubItem(anItem.getConvenantID(), createdSubItemList);
			}
		});
	}

	protected boolean allowRecurrentCheckListTrxCreation(long aLimitProfileID, long aSubProfileID)
			throws RecurrentException {

		String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_ACTIVE };

		RecurrentSearchResult[] resultList = getRecurrentByLimitProfileIdAndSubProfileIdAndStatusList(aLimitProfileID,
				aSubProfileID, statusList);

		if ((resultList == null) || (resultList.length == 0)) {
			return true;
		}

		return false;
	}

	protected RecurrentSearchResult[] getRecurrentByLimitProfileIdAndSubProfileIdAndStatusList(
			final long aLimitProfileID, final long aSubProfileID, final String[] statusList) throws RecurrentException {

		return (RecurrentSearchResult[]) executeRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				return (RecurrentSearchResult[]) busManager.getRecurrentCheckList(aLimitProfileID, aSubProfileID,
						statusList);
			}

		});
	}
	
	protected RecurrentSearchResult[] getRecurrentByLimitProfileIdAndSubProfileIdAndStatusListAndAnnexureId(
			final long aLimitProfileID, final long aSubProfileID, final String[] statusList, final String annexureId) throws RecurrentException {

		return (RecurrentSearchResult[]) executeRecurrentBusManagerAction(new RecurrentBusManagerAction() {

			public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception {
				return (RecurrentSearchResult[]) busManager.getRecurrentCheckListByAnnexureId(aLimitProfileID, aSubProfileID,
						statusList,annexureId);
			}

		});
	}
	
	protected ICMSTrxResult doInTrxController(ITrxController trxController, ITrxParameter param,
			IRecurrentCheckListTrxValue trxValue) throws RecurrentException {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		try {
			return (ICMSTrxResult) trxController.operate(trxValue, param);
		}
		catch (TransactionException ex) {
			throw new RecurrentException("encounter error when operating using controller [" + trxController.getClass()
					+ "] param action [" + param.getAction() + "] trx value [" + trxValue + "]", ex);
		}
	}

	protected Object executeRecurrentBusManagerAction(RecurrentBusManagerAction action) throws RecurrentException {
		return doExecuteRecurrentBusManagerAction(action, getRecurrentBusManager());
	}

	protected Object executeStagingRecurrentBusManagerAction(RecurrentBusManagerAction action)
			throws RecurrentException {
		return doExecuteRecurrentBusManagerAction(action, getStagingRecurrentBusManager());
	}

	protected Object doExecuteRecurrentBusManagerAction(RecurrentBusManagerAction action,
			SBRecurrentBusManager busManager) throws RecurrentException {
		try {
			return action.doInRecurrentBusManager(busManager);
		}
		catch (SearchDAOException t) {
			logger.error("encounter error when executing action on [" + action + "] to the jdbc", t);
			throw new RecurrentException("encounter error when executing action on [" + action + "] to the jdbc", t);
		}
		catch (Throwable t) {
			logger.error("encounter error when executing action on [" + action + "]", t);
			throw new RecurrentException("encounter error when executing action on [" + action + "]", t);
		}
	}

	interface RecurrentBusManagerAction {
		public Object doInRecurrentBusManager(SBRecurrentBusManager busManager) throws Exception;
	}
	
	public long getRecurrentDocId(long aLimitProfileID, long aSubProfileID)
		throws RecurrentException,RemoteException {

		return getRecurrentBusManager().getRecurrentDocId(aLimitProfileID, aSubProfileID);
	}
	
	public String getBankingType(long aLimitProfileID, long aSubProfileID)
		throws RecurrentException,RemoteException {

		return getRecurrentBusManager().getBankingType(aLimitProfileID, aSubProfileID);
	}
	
	public void insertAnnexures(ILimitProfile aLimitProfile)
		throws RecurrentException,RemoteException {

		getRecurrentBusManager().insertAnnexures(aLimitProfile);
	}

}
