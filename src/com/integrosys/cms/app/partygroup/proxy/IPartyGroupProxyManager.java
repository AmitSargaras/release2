package com.integrosys.cms.app.partygroup.proxy;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Bharat Waghela $
 */
public interface IPartyGroupProxyManager {

	public IPartyGroupTrxValue makerCloseRejectedPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	/*public List searchPartyGroupMaster(String login) throws PartyGroupException,TrxParameterException,TransactionException;*/
	
	public boolean isPartyCodeUnique(String partyCode);
	public boolean isPartyNameUnique(String partyName);
	
	public List getAllActual() throws PartyGroupException,
			TrxParameterException, TransactionException;
	
	public List getPartyByFacilityList(String partyName) throws PartyGroupException,
	TrxParameterException, TransactionException;

	public IPartyGroup getPartyGroupById(long id) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerCreatePartyGroup(ITrxContext anITrxContext,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerCreateSavePartyGroup(
			ITrxContext anITrxContext, IPartyGroup anICCPartyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue makerSavePartyGroup(ITrxContext anITrxContext,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroup updatePartyGroup(IPartyGroup partyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException;

	public IPartyGroup deletePartyGroup(IPartyGroup partyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue makerUpdatePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerCreatePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerEditRejectedPartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anIPartyGroupTrxValue, IPartyGroup anPartyGroup)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue getPartyGroupTrxValue(long aPartyGroupId)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue makerDeletePartyGroup(ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue getPartyGroupByTrxID(String aTrxID)
			throws PartyGroupException, TransactionException,
			CommandProcessingException;

	public IPartyGroupTrxValue checkerApprovePartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue checkerRejectPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue makerUpdateSaveUpdatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerEditSaveUpdatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public IPartyGroupTrxValue makerCloseDraftPartyGroup(
			ITrxContext anITrxContext, IPartyGroupTrxValue anIPartyGroupTrxValue)
			throws PartyGroupException, TrxParameterException,
			TransactionException;

	public IPartyGroupTrxValue makerActivatePartyGroup(
			ITrxContext anITrxContext,
			IPartyGroupTrxValue anICCPartyGroupTrxValue,
			IPartyGroup anICCPartyGroup) throws PartyGroupException,
			TrxParameterException, TransactionException;

	public SearchResult getPartyList(String type, String text) throws PartyGroupException;
}
