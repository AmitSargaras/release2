/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.partygroup.proxy;

// java

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupBusManager;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.partygroup.trx.OBPartyGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Proxy manager interface declares the methods used by commands
 */
public class PartyGroupProxyManagerImpl implements IPartyGroupProxyManager {

	private IPartyGroupBusManager partyGroupBusManager;

	private IPartyGroupBusManager stagingPartyGroupBusManager;

	private ITrxControllerFactory trxControllerFactory;

	//	
	// public SearchResult getPartyGroup1()
	// throws SearchDAOException, SearchDAOException {
	// return getPartyGroupBusManager().getPartyGroup1();
	// }

	public IPartyGroupBusManager getPartyGroupBusManager() {
		return partyGroupBusManager;
	}

	public void setPartyGroupBusManager(
			IPartyGroupBusManager partyGroupBusManager) {
		this.partyGroupBusManager = partyGroupBusManager;
	}

	public IPartyGroupBusManager getStagingPartyGroupBusManager() {
		return stagingPartyGroupBusManager;
	}

	public void setStagingPartyGroupBusManager(
			IPartyGroupBusManager stagingPartyGroupBusManager) {
		this.stagingPartyGroupBusManager = stagingPartyGroupBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	/**
	 * @return List of Party Group
	 * 
	 *         This method access the Database through jdbc and fetch data.
	 */

	public List getAllActual() {
		return getPartyGroupBusManager().getAllPartyGroup();
	}

	public List getPartyByFacilityList(String partyName) {
		return getPartyGroupBusManager().getPartyByFacilityList(partyName);
	}
	/**
	 * @return Particular Party Group according to the id passed as parameter.
	 * @param Party
	 *            Code
	 */

	public IPartyGroup getPartyGroupById(long id) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (id != 0) {
			return getPartyGroupBusManager().getPartyGroupById(id);
		} else {
			throw new PartyGroupException(
					"ERROR-- Key for Party Group is null.");
		}

	}

	/**
	 * @return updated Party Group Object
	 * @param Party
	 *            Group object to be updated
	 */

	public IPartyGroup updatePartyGroup(IPartyGroup partyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		if (!(partyGroup == null)) {
			return getPartyGroupBusManager().updatePartyGroup(
					(IPartyGroup) partyGroup);
		} else {
			throw new PartyGroupException("Party Group Object is null.");
		}
	}

	public IPartyGroup deletePartyGroup(IPartyGroup partyGroup)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (!(partyGroup == null)) {
			IPartyGroup item = (IPartyGroup) partyGroup;
			try {
				return getPartyGroupBusManager().deletePartyGroup(item);
			} catch (ConcurrentUpdateException e) {

				e.printStackTrace();
				throw new HolidayException(
						"ERROR-- Transaction for the PartyGroupobject is null.");
			}
		} else {
			throw new HolidayException(
					"ERROR-- Cannot delete due to null PartyGroupobject.");
		}
	}

	/**
	 * @return updated Party Group Trx value Object
	 * @param Trx
	 *            object, Party Group Trx object,Party Group object to be
	 *            updated
	 * 
	 *            The updated Party Group object in stored in Staging Table of
	 *            Party Group
	 */

	public IPartyGroupTrxValue makerUpdatePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroup to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	public IPartyGroupTrxValue makerCreatePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroup to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @return updated Party Group Trx value Object
	 * @param Trx
	 *            object, Party Group Trx object,Party Group object to be
	 *            updated After once rejected by Checker, if maker attempts to
	 *            update the same record its done by this method. The updated
	 *            Party Group object in stored in Staging Table of Party Group
	 */
	public IPartyGroupTrxValue makerEditRejectedPartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anIPartyGroupTrxValue, IPartyGroup anIPartyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anIPartyGroupTrxValue == null) {
			throw new PartyGroupException(
					"The IPartyGroupTrxValue to be updated is null!!!");
		}
		if (anIPartyGroup == null) {
			throw new PartyGroupException(
					"The IPartyGroup to be updated is null !!!");
		}
		anIPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				anIPartyGroupTrxValue, anIPartyGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PARTY_GROUP);
		return operate(anIPartyGroupTrxValue, param);
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anIPartyGroup
	 * @return IPartyGroupTrxValue
	 * 
	 * 
	 */

	private IPartyGroupTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, IPartyGroup anIPartyGroup) {
		IPartyGroupTrxValue ccPartyGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			ccPartyGroupTrxValue = new OBPartyGroupTrxValue(anICMSTrxValue);
		} else {
			ccPartyGroupTrxValue = new OBPartyGroupTrxValue();
		}
		ccPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				(IPartyGroupTrxValue) ccPartyGroupTrxValue);
		ccPartyGroupTrxValue.setStagingPartyGroup(anIPartyGroup);
		return ccPartyGroupTrxValue;
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anIPartyGroupTrxValue
	 * @return IPartyGroupTrxValue
	 */
	private IPartyGroupTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IPartyGroupTrxValue anIPartyGroupTrxValue) {
		anIPartyGroupTrxValue.setTrxContext(anITrxContext);
		anIPartyGroupTrxValue
				.setTransactionType(ICMSConstant.INSTANCE_PARTY_GROUP);
		return anIPartyGroupTrxValue;
	}

	/**
	 * 
	 * @param anIPartyGroupTrxValue
	 * @param anOBCMSTrxParameter
	 * @return IPartyGroupTrxValue
	 * @throws PartyGroupException
	 * @throws TrxParameterException
	 * @throws TransactionException
	 */

	private IPartyGroupTrxValue operate(
			IPartyGroupTrxValue anIPartyGroupTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws PartyGroupException,
			TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIPartyGroupTrxValue,
				anOBCMSTrxParameter);
		return (IPartyGroupTrxValue) result.getTrxValue();
	}

	/**
	 * 
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter
	 * @return ICMSTrxResult
	 * @throws PartyGroupException
	 * @throws TrxParameterException
	 * @throws TransactionException
	 * 
	 * 
	 *             This method selects the Controller for the Operation to be
	 *             performed.
	 */

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws PartyGroupException,
			TrxParameterException, TransactionException {
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

		catch (PartyGroupException ex) {
			throw new PartyGroupException(ex.toString());
		}catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new PartyGroupException("ERROR--Cannot update already deleted record");
		 }
	}

	/**
	 * @return PartyGroupTrx Value
	 * @param PartyGroup
	 *            Object This method fetches the Proper trx value according to
	 *            the Object passed as parameter.
	 * 
	 */
	public IPartyGroupTrxValue getPartyGroupTrxValue(long aPartyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		if (aPartyGroup == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new PartyGroupException("Invalid PartyGroupId");
		}
		IPartyGroupTrxValue trxValue = new OBPartyGroupTrxValue();
		trxValue.setReferenceID(String.valueOf(aPartyGroup));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PARTY_GROUP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @return PartyGroupTrx Value
	 * @param PartyGroup
	 *            Object This method fetches the Proper trx value according to
	 *            the Transaction Id passed as parameter.
	 * 
	 */
	public IPartyGroupTrxValue getPartyGroupByTrxID(String trxID)
			throws PartyGroupException, TransactionException {
		IPartyGroupTrxValue trxValue = new OBPartyGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PARTY_GROUP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PARTY_GROUP_ID);
		return operate(trxValue, param);
	}

	/**
	 * @return Party Group Trx Value
	 * @param Trx
	 *            object, Party Group Trx object,Party Group object
	 * 
	 *            This method Approves the Object passed by Maker
	 */

	public IPartyGroupTrxValue checkerApprovePartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anIPartyGroupTrxValue == null) {
			throw new PartyGroupException(
					"The IPartyGroupTrxValue to be updated is null!!!");
		}
		anIPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				anIPartyGroupTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PARTY_GROUP);
		return operate(anIPartyGroupTrxValue, param);
	}

	/**
	 * @return Party Group Trx Value
	 * @param Trx
	 *            object, Party Group Trx object,Party Group object
	 * 
	 *            This method Rejects the Object passed by Maker
	 */
	public IPartyGroupTrxValue checkerRejectPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anIPartyGroupTrxValue == null) {
			throw new PartyGroupException(
					"The IPartyGroupTrxValue to be updated is null!!!");
		}
		anIPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				anIPartyGroupTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PARTY_GROUP);
		return operate(anIPartyGroupTrxValue, param);
	}

	/**
	 * @return Party Group Trx Value
	 * @param Trx
	 *            object, Party Group Trx object,Party Group object
	 * 
	 *            This method Close the Object rejected by Checker
	 */
	public IPartyGroupTrxValue makerCloseRejectedPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anIPartyGroupTrxValue == null) {
			throw new PartyGroupException(
					"The IPartyGroupTrxValue to be updated is null!!!");
		}
		anIPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				anIPartyGroupTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PARTY_GROUP);
		return operate(anIPartyGroupTrxValue, param);
	}

	/**
	 * @return Maker Update Party Group
	 */

	public IPartyGroupTrxValue makerUpdateSaveUpdatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroup to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		// trxValue.setFromState("DRAFT");
		// trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	public IPartyGroupTrxValue makerEditSaveUpdatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroup to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Close draft Party group
	 */

	public IPartyGroupTrxValue makerCloseDraftPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anIPartyGroupTrxValue == null) {
			throw new PartyGroupException(
					"The IPartyGroupTrxValue to be updated is null!!!");
		}
		anIPartyGroupTrxValue = formulateTrxValue(anITrxContext,
				anIPartyGroupTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PARTY_GROUP);
		return operate(anIPartyGroupTrxValue, param);
	}

	/**
	 * @return Maker Create Party Group
	 */
	public IPartyGroupTrxValue makerCreatePartyGroup(ITrxContext anITrxContext,
			IPartyGroup anICCPartyGroup) throws HolidayException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroupto be updated is null !!!");
		}

		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPartyGroup);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Save Party Group
	 */
	/**
	 * @return Maker Create partyGroup
	 */
	public IPartyGroupTrxValue makerCreateSavePartyGroup(
			ITrxContext anITrxContext, IPartyGroup anICCPartyGroup)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroupto be updated is null !!!");
		}

		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPartyGroup);
		trxValue.setStatus("PENDING_PERFECTION");
		trxValue.setFromState("DRAFT");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Save Party Group
	 */
	public IPartyGroupTrxValue makerSavePartyGroup(ITrxContext anITrxContext,
			IPartyGroup anICCPartyGroup) throws HolidayException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPartyGroupto be updated is null !!!");
		}

		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPartyGroup);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	public IPartyGroupTrxValue makerDeletePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	public IPartyGroupTrxValue makerActivatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PartyGroupException("The ITrxContext is null!!!");
		}
		if (anICCPartyGroup == null) {
			throw new PartyGroupException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IPartyGroupTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPartyGroupTrxValue, anICCPartyGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_PARTY_GROUP);
		return operate(trxValue, param);
	}

	/*public List searchPartyGroupMaster(String login) throws PartyGroupException,TrxParameterException,TransactionException {
	 	return getPartyGroupBusManager().searchPartyGroupMaster(login);

    }*/

	
	public boolean isPartyCodeUnique(String partyCode) {
		
		return getPartyGroupBusManager().isPartyCodeUnique(partyCode);
	}
	
	public boolean isPartyNameUnique(String partyName) {
		
		return getPartyGroupBusManager().isPartyNameUnique(partyName);
	}

	public SearchResult getPartyList(String type, String text) throws PartyGroupException {
		return getPartyGroupBusManager().getPartyList(type, text);
	}
}
