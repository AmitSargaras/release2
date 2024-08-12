/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/AbstractCustodianProxyManager.java,v 1.49 2006/09/14 14:20:08 wltan Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.CustodianDAO;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.custodian.trx.CustodianTrxControllerFactory;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This abstract class will contains a business related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.49 $
 * @since $Date: 2006/09/14 14:20:08 $ Tag: $Name: DEV_20061123_B286V1 $
 */
public abstract class AbstractCustodianProxyManager implements ICustodianProxyManager {
	/**
	 * Create a custodian doc as draft
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return OBCustodianDoc - the custodian doc that has been saved as draft
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc createDraftCustodianDoc(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianDoc == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		if (anICustodianDoc.getSubProfileID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The Borrower ID is invalid !!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianDoc);
		return createDraftDoc(custTrxValue).getStagingCustodianDoc();
	}

	/**
	 * Receive the custodian and submit the draft. The user submitting it must
	 * be of the same team
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx containing the draft custodian doc
	 *         submit
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue receiveDraftCustodianDoc(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue) throws CustodianException {
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		if (anICustodianTrxValue.getStagingCustodianDoc().getSubProfileID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The Borrower ID is invalid !!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RECEIVE_DRAFT_CUSTODIAN_DOC);
		return operate(custTrxValue, param);
	}

	/**
	 * Maker Create a borrower custodian doc that is not in a checkList manually
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocMaker(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc, String borrowerTrxId)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianDoc == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		if (anICustodianDoc.getSubProfileID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The Borrower ID is invalid !!!");
		}
		if (borrowerTrxId == null) {
			throw new CustodianException("The Borrower trx ID is null !!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianDoc);
		custTrxValue.setTrxReferenceID(borrowerTrxId);
		return createDocMaker(custTrxValue).getStagingCustodianDoc();
	}

	/**
	 * Checker Approve the creation of a borrower custodian doc that is created
	 * manually
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianTrxValue createDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_CREATE_CUSTODIAN_DOC);
	}

	/**
	 * Create a borrower custodian doc either manually or thru' checkList
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianTrxValue - the trx object containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocByBorrower(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianDoc == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		if (anICustodianDoc.getLimitProfileID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			if (!ICMSConstant.CHECKLIST_NON_BORROWER.equals(anICustodianDoc.getDocSubType())) {
				throw new CustodianException("The Limit profile ID is null !!!");
			}
		}
		if (anICustodianDoc.getCheckListID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The CheckList ID is invalid !!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianDoc);
		return createCustodianTransaction(custTrxValue).getStagingCustodianDoc();
	}

	/**
	 * Create a collateral custodian doc thru' checklist
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the biz interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	public ICustodianDoc createDocByCollateral(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianDoc == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		if (anICustodianDoc.getLimitProfileID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The Limit Profile ID is invalid !!!");
		}
		if (anICustodianDoc.getCollateralID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The Collateral ID is invalid !!!");
		}
		if (anICustodianDoc.getCheckListID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CustodianException("The CheckList ID is invalid !!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianDoc);
		return createCustodianTransaction(custTrxValue).getStagingCustodianDoc();
	}

	/**
	 * Maker Lodge a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be lodged is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_LODGE_CUSTODIAN_DOC);
		return operate(custTrxValue, param).getStagingCustodianDoc();
	}

	/**
	 * Maker Lodge a custodian doc
	 * 
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be lodged is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_LODGE_CUSTODIAN_DOC);
		return operate(custTrxValue, param);
	}

	/**
	 * Maker Relodge a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		return lodgeDocMaker(anITrxContext, anICustodianTrxValue);
	}

	/**
	 * Maker re Lodge a custodian doc
	 * 
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		return lodgeDocMaker(anITrxContext, anICustodianTrxValue, anICustodianDoc);
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue lodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_LODGE_CUSTODIAN_DOC);
	}

	/**
	 * Checker approve relodgement of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue reLodgeDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		return lodgeDocChecker(anITrxContext, anICustodianTrxValue);
	}

	/**
	 * Maker Perm Uplift a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be permUplift is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_PERM_UPLIFT_CUSTODIAN_DOC);
		return operate(custTrxValue, param).getStagingCustodianDoc();
	}

	/**
	 * Maker perm uplift a custodian doc
	 * 
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue permUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be permUplift is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_PERM_UPLIFT_CUSTODIAN_DOC);
		return operate(custTrxValue, param);
	}

	/**
	 * Checker approve perm uplift of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approvePermUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_PERM_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Maker temp uplift a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @return OBCustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianDoc tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be tempUplift is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_TEMP_UPLIFT_CUSTODIAN_DOC);
		return operate(custTrxValue, param).getStagingCustodianDoc();
	}

	/**
	 * Maker temp uplift a custodian doc
	 * 
	 * @param anITrxContext -\of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be temp
	 *         uplifted
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue tempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		checkTeamAndUser(anITrxContext);
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The TRXDoc to be tempUplift is null!!!");
		}
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_TEMP_UPLIFT_CUSTODIAN_DOC);
		return operate(custTrxValue, param);
	}

	/**
	 * Checker approve temp uplift of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approveTempUplift(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_TEMP_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Checker Approve the updating of a custodian doc. This will cater to the
	 * maintain custodian doc use case
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return OBCustodianDoc - the trx object containing the info of the
	 *         custodian doc updated
	 * @throws CustodianException
	 */
	public ICustodianTrxValue updateDocChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		// TODO: implement
		return null;
	}

	/**
	 * Get the custodian doc by transaction object based on the transaction ID
	 * The object contains the before/after image depending on the indicator
	 * 
	 * @param aTransactionID - String
	 * @return ICustodianTrxValue - the transaction interface for the custodia
	 *         document
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getDocByTrxID(String aTransactionID) throws CustodianException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CUSTODIAN_DOC);
		OBCustodianTrxValue trxValue = new OBCustodianTrxValue();
		trxValue.setTransactionID(aTransactionID);
		return operate(trxValue, param);
	}

	/**
	 * Checker approve lodgement of a custodian doc
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue approveTransaction(ICustodianTrxValue anICustodianTrxValue, String anActionValue)
			throws CustodianException {
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The ICustodianDoc to be created is null!!!");
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(anActionValue);
		return operate(anICustodianTrxValue, param);
	}

	/**
	 * Formulate the Custodian Trx Object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianTrxValue - the custodian trx interface formulated
	 */
	private ICustodianTrxValue formulateTrxValue(ITrxContext anITrxContext, ICustodianDoc anICustodianDoc)
			throws CustodianException {
		return formulateTrxValue(anITrxContext, null, anICustodianDoc);
	}

	/**
	 * Formulate the Custodian Trx Object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anITrxValue - ITrxValue
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianTrxValue - the custodian trx interface formulated
	 */
	private ICustodianTrxValue formulateTrxValue(ITrxContext anITrxContext, ICustodianTrxValue anITrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		OBCustodianTrxValue custTrxValue = null;
		if (anITrxValue != null) {
			custTrxValue = new OBCustodianTrxValue(anITrxValue);
		}
		else {
			custTrxValue = new OBCustodianTrxValue();
		}
		custTrxValue.setTrxContext(anITrxContext);
		custTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
		// CMS - 3043
		custTrxValue
				.setTransactionSubType(getTransactionSubType(anICustodianDoc, custTrxValue.getTransactionSubType()));
		custTrxValue.setStagingCustodianDoc(anICustodianDoc);
		return custTrxValue;
	}

	/**
	 * Formulate the Custodian Trx Object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustTrxVal - ITrxValue
	 * @param anICustTrxVal - ICustodianDoc
	 * @return ICustodianTrxValue - the custodian trx interface formulated
	 */
	private ICustodianTrxValue formulateTrxValue(ITrxContext anITrxContext, ICustodianTrxValue anICustTrxVal)
			throws CustodianException {

		OBCustodianTrxValue custTrxValue = new OBCustodianTrxValue();
		AccessorUtil.copyValue(anICustTrxVal, custTrxValue);
		custTrxValue.setTrxContext(anITrxContext);
		custTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
		// CMS - 3043
		custTrxValue.setTransactionSubType(getTransactionSubType(anICustTrxVal.getStagingCustodianDoc(), custTrxValue
				.getTransactionSubType()));
		return custTrxValue;
	}

	private String getTransactionSubType(ICustodianDoc anICustodianDoc, String transactionSubType) {
		String checklistStatus = null;

		if (anICustodianDoc == null) {
			return null;
		}
		try {
			// Start -CMS-3043 : Fix to update the exact Transaction Type in the
			// Custodian maker and Custodian Checker transaction
			// Trx subtype with NULL - should be updated for deleted and active
			// checklist
			// Trx subtype without NULL - should be updated if the checklist is
			// in DELETED Status
			CustodianDAO custDAO = new CustodianDAO();
			checklistStatus = custDAO.getChecklistStatus(anICustodianDoc.getCheckListID());
			boolean isDocTypeCC = ICMSConstant.DOC_TYPE_CC.equals(anICustodianDoc.getDocType());
			boolean isDeletedChecklist = ((checklistStatus != null) && checklistStatus
					.equals(ICMSConstant.STATE_CHECKLIST_DELETED));

			String newTrxSubType = null;
			if (isDeletedChecklist) {
				newTrxSubType = isDocTypeCC ? ICMSConstant.TRX_TYPE_DELETED_CC_CUSTODIAN
						: ICMSConstant.TRX_TYPE_DELETED_COL_CUSTODIAN;
			}
			else {
				if ((transactionSubType == null) || (transactionSubType.length() == 0)) {
					newTrxSubType = isDocTypeCC ? ICMSConstant.TRX_TYPE_CC_CUSTODIAN
							: ICMSConstant.TRX_TYPE_COL_CUSTODIAN;
				}
				else {
					newTrxSubType = transactionSubType;
				}
			}
			return newTrxSubType;

		}
		catch (Exception e) {
			DefaultLogger.debug(" Exception in getTransactionSubtype of Abstract Custodian Proxy manager ", e);

		}
		// CMS-3043 - End

		return null;
	}

	/**
	 * Creating a custodian transaction.
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian specific transaction object
	 *         created
	 * @throws CustodianException for any errors encountered
	 */
	private ICustodianTrxValue createCustodianTransaction(ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RECEIVE_CUSTODIAN_DOC);
		return operate(anICustodianTrxValue, param);
	}

	/**
	 * Create a custodian doc as a draft
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the draft
	 *         created
	 * @throws CustodianException if errors
	 */
	private ICustodianTrxValue createDraftDoc(ICustodianTrxValue anICustodianTrxValue) throws CustodianException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_DRAFT_CUSTODIAN_DOC);
		return operate(anICustodianTrxValue, param);
	}

	/**
	 * Create a borrower custodian doc that is not in a checkList manually
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the trx interface containing the info of the
	 *         custodian doc created
	 * @throws CustodianException
	 */
	private ICustodianTrxValue createDocMaker(ICustodianTrxValue anICustodianTrxValue) throws CustodianException {
		if (anICustodianTrxValue == null) {
			throw new CustodianException("The ICustodianTrxValue to be created is null!!!");
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		// param.setAction(ICMSConstant.ACTION_CREATE_CUSTODIAN_DOC);
		return operate(anICustodianTrxValue, param);
	}

	/**
	 * Helper method to perform the custodian transactions.
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICustodianTrxValue - the trx interface
	 */
	protected ICustodianTrxValue operate(ICustodianTrxValue anICustodianTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CustodianException {
		ICMSTrxResult result = operateForResult(anICustodianTrxValue, anOBCMSTrxParameter);
		return (OBCustodianTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the custodian transactions.
	 * 
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICustodianTrxValue - the trx interface
	 */
	private ICMSTrxResult operateForResult(ICustodianTrxValue anICustodianTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CustodianException {
		try {
			ITrxController controller = (new CustodianTrxControllerFactory()).getController(anICustodianTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CustodianException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICustodianTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			e.printStackTrace();
			throw new CustodianException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CustodianException(ex.toString());
		}
	}

	/***************************************************************************
	 * Helper method to make sure that the team and user ID are valid
	 * 
	 * @param anITrxContext
	 * @throws CustodianException if errors
	 */
	private void checkTeamAndUser(ITrxContext anITrxContext) throws CustodianException {
		if (anITrxContext == null) {
			throw new CustodianException("The ITrxContext is null!!!");
		}
		return;
	}

	/**
	 * Method to rollback a transaction.
	 * 
	 * @throws CustodianException for any errors encountered
	 */
	protected abstract void rollback() throws CustodianException;

	/**
	 * Method to create the custodian doc to be implemented by the concrete
	 * class
	 * 
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the custodian doc created
	 * @throws CustodianException if errors
	 */
	protected abstract ICustodianDoc createCustodianDoc(ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Method to get a trx value given a custodian ID
	 * 
	 * @param custodianID is the long value of the custodian ID
	 * @return ICustodianTrxValue
	 * @throws CustodianException on error
	 */
	public ICustodianTrxValue getTrxCustodianDoc(long custodianID) throws CustodianException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CUSTODIAN_ID_DOC);

			OBCustodianTrxValue tempValue = new OBCustodianTrxValue();
			tempValue.setReferenceID(String.valueOf(custodianID));

			return operate(tempValue, param);
		}
		catch (CustodianException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CustodianException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * authz tmep uplift of custodian doc by maker
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_AUTHZ_TEMP_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Maker authz temp uplift of custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException {
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		return approveTransaction(custTrxValue, ICMSConstant.ACTION_AUTHZ_TEMP_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Checker approve authz of tmep uplift of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzTempUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_AUTHZ_TEMP_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * authz perm uplift of custodian doc by maker
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_AUTHZ_PERM_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Maker authz perm uplift of custodian doc
	 * 
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is to be perm
	 *         uplift authz
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftDocMaker(ITrxContext anITrxContext,
			ICustodianTrxValue anICustodianTrxValue, ICustodianDoc anICustodianDoc) throws CustodianException {
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		return approveTransaction(custTrxValue, ICMSConstant.ACTION_AUTHZ_PERM_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * Checker approve authz of perm uplift of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzPermUpliftChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_AUTHZ_PERM_UPLIFT_CUSTODIAN_DOC);
	}

	/**
	 * authz relodge of custodian doc by maker
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_AUTHZ_RELODGE_CUSTODIAN_DOC);
	}

	/**
	 * Maker authz relodge of custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianDoc - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeDocMaker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		return approveTransaction(custTrxValue, ICMSConstant.ACTION_AUTHZ_RELODGE_CUSTODIAN_DOC);
	}

	/**
	 * Checker approve authz relodge of a custodian doc
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue authzRelodgeChecker(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_APPROVE_AUTHZ_RELODGE_CUSTODIAN_DOC);
	}

	/**
	 * Checker reject custodian doc action..
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue rejectCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_REJECT_CUSTODIAN_DOC);
	}

	/**
	 * maker cancel the reject of custodian doc..
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue cnclCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_CNCL_REJECT_CUSTODIAN_DOC);
	}

	/**
	 * maker edit the reject of custodian doc..
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue)
			throws CustodianException {
		anICustodianTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue);
		return approveTransaction(anICustodianTrxValue, ICMSConstant.ACTION_EDIT_REJECT_CUSTODIAN_DOC);
	}

	/**
	 * maker edit the rejected custodian doc
	 * 
	 * @param anITrxContext of ITrxContext type
	 * @param anICustodianTrxValue of ICustodianTrxValue type
	 * @param anICustodianDoc of ICustodianDoc type
	 * @return ICustodianTrxValue - the custodian doc trx that is edited
	 * @throws CustodianException if errors
	 */
	public ICustodianTrxValue editCustodian(ITrxContext anITrxContext, ICustodianTrxValue anICustodianTrxValue,
			ICustodianDoc anICustodianDoc) throws CustodianException {
		ICustodianTrxValue custTrxValue = formulateTrxValue(anITrxContext, anICustodianTrxValue, anICustodianDoc);
		return approveTransaction(custTrxValue, ICMSConstant.ACTION_EDIT_REJECT_CUSTODIAN_DOC);
	}

	/**
	 * Get the list of custodian document that satisfy the criteria
	 * 
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the search result containing the list of custodian
	 *         doc
	 * @throws CustodianException
	 */
	public HashMap getDocListWithOwnerInfo(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			HashMap map = new HashMap();
			SearchResult result = getDocList(aCustodianSearchCriteria);
			map.put(ICMSConstant.SEARCH_RESULT, result);
			ICustodianDoc dummyDoc = new OBCustodianDoc();
			dummyDoc.setDocType(aCustodianSearchCriteria.getDocType());
			dummyDoc.setCollateralID(aCustodianSearchCriteria.getCollateralID());
			if (ICMSConstant.DOC_TYPE_CC.equals(dummyDoc.getDocType())) {
				ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
				ICheckList checklist = mgr.getCheckListByID(aCustodianSearchCriteria.getCheckListID());
				ICCCheckListOwner owner = (ICCCheckListOwner) checklist.getCheckListOwner();
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(owner.getSubOwnerType())) {
					dummyDoc.setPledgorID(owner.getSubOwnerID());
				}
				else {
					dummyDoc.setSubProfileID(owner.getSubOwnerID());
				}
				dummyDoc.setDocSubType(owner.getSubOwnerType());
			}
			dummyDoc = setOwnerInfo(dummyDoc);
			if (ICMSConstant.DOC_TYPE_CC.equals(dummyDoc.getDocType())) {
				map.put(ICMSConstant.CC_OWNER, dummyDoc.getCCCustodianInfo());
			}
			else if (ICMSConstant.DOC_TYPE_SECURITY.equals(dummyDoc.getDocType())) {
				map.put(ICMSConstant.SEC_OWNER, dummyDoc.getCollateralCustodianInfo());
			}
			map.put(ICMSConstant.SUB_CATEGORY, dummyDoc.getDocSubType());
			return map;
		}
		catch (CheckListException ex) {
			throw new CustodianException(ex);
		}
	}

	/**
	 * maker edit the reject of custodian doc..
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	/*
	 * public void persistPrintAuthzDetails(String[] custodianDocIds) throws
	 * CustodianException { }
	 */

	/**
	 * maker edit the reject of custodian doc..
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICustodianTrxValue - ICustodianTrxValue
	 * @return ICustodianTrxValue - the custodian doc that is to be lodged
	 * @throws CustodianException if errors
	 */
	/*
	 * public IMemo getPrintMemo(OBCustAuthorize[] custAuthz) throws
	 * CustodianException { return null; }
	 */

	public abstract SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException;

	public abstract ICustodianDoc setOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException;

	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws CustodianException {
		if (trxVal == null) {
			throw new CustodianException("ITrxValue is null!");
		}
		ITrxController controller = null;
		try {
			controller = (new CustodianTrxControllerFactory()).getController(trxVal, param);
			if (controller == null) {
				throw new CustodianException("ITrxController is null!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (CustodianException e) {
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			rollback();
			throw new CustodianException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new CustodianException("Exception caught! " + e.toString(), e);
		}
	}

	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		((ICMSTrxValue) trxValue).setTrxContext(ctx);
		return trxValue;
	}

	private ICustodianTrxValue operate(ITrxContext trxContext, ICustodianTrxValue trxValue, String action)
			throws CustodianException {
		trxValue = formulateTrxValue(trxContext, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		ICMSTrxResult result = operateForResult(trxValue, param);
		return (OBCustodianTrxValue) result.getTrxValue();
	}

	// new custodian methods
	/*
	 * public ICustodianTrxValue makerCreateCustodianDoc(ITrxContext trxContext,
	 * ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC); }
	 */

	/*
	 * public ICustodianTrxValue checkerApproveCreateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTODIAN_DOC); }
	 */

	/*
	 * public ICustodianTrxValue checkerRejectCreateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTODIAN_DOC); }
	 */

	/*
	 * public ICustodianTrxValue makerCloseCreateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC); }
	 */

	/**
	 * Maker saves the custodian doc as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return the draft custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerSaveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {

		return operate(ctx, trxVal, ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC);
	}

	/**
	 * Maker updates custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return newly updated custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerUpdateCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {
		String action = ((trxVal.getTransactionID() == null) || (trxVal.getCustodianDoc() == null)) ? ICMSConstant.ACTION_MAKER_CREATE_CUSTODIAN_DOC
				: ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC;
		return operate(ctx, trxVal, action);
	}

	/**
	 * Maker cancel rejected or draft custodian doc.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return cancelled custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue makerCloseCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {
		String action = (trxVal.getCustodianDoc() == null) ? ICMSConstant.ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC
				: ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC;
		return operate(ctx, trxVal, action);
	}

	/**
	 * Checker approves custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerApproveCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {
		String action = (trxVal.getCustodianDoc() == null) ? ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTODIAN_DOC
				: ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTODIAN_DOC;
		return operate(ctx, trxVal, action);
	}

	/**
	 * Checker rejects custodian doc updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal custodian transaction value
	 * @return approved custodian transaction value
	 * @throws CustodianException on errors encountered
	 */
	public ICustodianTrxValue checkerRejectCustodianDoc(ITrxContext ctx, ICustodianTrxValue trxVal)
			throws CustodianException {
		String action = (trxVal.getCustodianDoc() == null) ? ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTODIAN_DOC
				: ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTODIAN_DOC;
		return operate(ctx, trxVal, action);
	}

	/*
	 * public ICustodianTrxValue checkerApproveUpdateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTODIAN_DOC); }
	 */

	/*
	 * public ICustodianTrxValue checkerRejectUpdateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTODIAN_DOC); }
	 */

	/*
	 * public ICustodianTrxValue makerCloseUpdateCustodianDoc( ITrxContext
	 * trxContext, ICustodianTrxValue value) throws CustodianException { return
	 * operate(trxContext, value,
	 * ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC); }
	 */

	public ICustodianTrxValue readCustodianDoc(ITrxContext trxContext, ICustodianTrxValue value)
			throws CustodianException {
		return operate(trxContext, value, ICMSConstant.ACTION_READ_CUSTODIAN_DOC);
	}

	/**
	 * To replace getDocListWithOwnerInfo Get the custodian trx value and
	 * related owner information.
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return HashMap - map containing the following key, value pair : <br>
	 *         1. "trxValue", ICustodianTrxValue <br>
	 *         2. ICMSConstant.CC_OWNER, CCCustodianInfo <br>
	 *         3. ICMSConstant.SEC_OWNER, CollateralCustodianInfo <br>
	 *         4. ICMSConstant.SUB_CATEGORY, String representing subcategory <br>
	 * @throws CustodianException
	 */
	public HashMap getDocWithOwnerInfo(CustodianSearchCriteria searchCriteria) throws CustodianException {
		HashMap resultMap = new HashMap();
		try {
			long trxID = getTrxID(searchCriteria);
			DefaultLogger.debug(this, "getDocWithOwnerInfo - trxID : " + trxID);
			ICustodianTrxValue trxValue = (trxID != ICMSConstant.LONG_INVALID_VALUE) ? getDocByTrxID(Long
					.toString(trxID)) : getNewDocTrxValue(searchCriteria.getCheckListID());

			// TODO : move string constant to ICMSConstant
			resultMap.put("trxValue", trxValue);

			// get owner info
			ICustodianDoc dummyDoc = new OBCustodianDoc();
			dummyDoc.setDocType(searchCriteria.getDocType());
			dummyDoc.setCollateralID(searchCriteria.getCollateralID());
			if (ICMSConstant.DOC_TYPE_CC.equals(dummyDoc.getDocType())) {
				ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
				ICheckList checklist = mgr.getCheckListByID(searchCriteria.getCheckListID());
				ICCCheckListOwner owner = (ICCCheckListOwner) checklist.getCheckListOwner();
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(owner.getSubOwnerType())) {
					dummyDoc.setPledgorID(owner.getSubOwnerID());
				}
				else {
					dummyDoc.setSubProfileID(owner.getSubOwnerID());
				}
				dummyDoc.setDocSubType(owner.getSubOwnerType());
			}
			dummyDoc = setOwnerInfo(dummyDoc);

			// put required owner's info in map
			if (ICMSConstant.DOC_TYPE_CC.equals(dummyDoc.getDocType())) {
				resultMap.put(ICMSConstant.CC_OWNER, dummyDoc.getCCCustodianInfo());
			}
			else if (ICMSConstant.DOC_TYPE_SECURITY.equals(dummyDoc.getDocType())) {
				resultMap.put(ICMSConstant.SEC_OWNER, dummyDoc.getCollateralCustodianInfo());
			}
			resultMap.put(ICMSConstant.SUB_CATEGORY, dummyDoc.getDocSubType());

		}
		catch (CheckListException cle) {
			throw new CustodianException(cle);
		}

		return resultMap;
	}

}